package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.AcaoCard;
import entity.CardTrello;

public class ClienteTrello {

	public static void main(String[] args) {
		try {
			String api_key = "a2adfb6f5aeb7055d60384593bf0a276";
			String token_trello = "7600ff7f3f0592dedc6b2bf362defa24f44e57d46f2ba965919bf9a2b91ba7a0";
			
			String autorizacao="?key="+api_key+"&token="+token_trello;
			String criterio = "&cards=open&lists=open";
			
			String lista_documentacao = "56cf6e8ef40ca6ca3bcbe26f";
			//String lista_elicitacao = "56cf6e735694b3d802e8d8a0";
			//String lista_cont_apf = "56cf6e9ae769951207321a17";
			//String lista_impedimento = "573105b3fdf760380b56c518";
			
			//URL lists of board
			//https://api.trello.com/1/boards/56cf6e5095d740ac09d83af1/lists?key=a2adfb6f5aeb7055d60384593bf0a276&token=7600ff7f3f0592dedc6b2bf362defa24f44e57d46f2ba965919bf9a2b91ba7a0
			
			//https://api.trello.com/1/lists/56cf6e8ef40ca6ca3bcbe26f/cards?key=a2adfb6f5aeb7055d60384593bf0a276&token=7600ff7f3f0592dedc6b2bf362defa24f44e57d46f2ba965919bf9a2b91ba7a0&cards=open&lists=open
			String endereco = "https://api.trello.com/1/lists/"+lista_documentacao+"/cards"+autorizacao+criterio;
			
			URL url = new URL(endereco);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.tjpb.jus.br",3128));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection(proxy);
			conn.setRequestMethod("GET");

			conn.setRequestProperty("Accept", "application/json");
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed! HTTP error code: "+conn.getResponseCode());
			}
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			String line;
			StringBuffer output = new StringBuffer();
			
			while ((line=buffer.readLine()) != null) {
				output.append(line);
				
			}
			
			JSONArray arr = new JSONArray(output.toString());
			JSONObject obj, acaojson;
			CardTrello ct;
			
			String endAcoes;
			URL urlAcoes;
			HttpURLConnection connAcoes;
			JSONArray arrAcoes;
			AcaoCard acao;
			
			for(int i=0;i<arr.length();i++){
				obj = arr.getJSONObject(i);
				ct = new CardTrello();
				
				//DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.ENGLISH);
				
				DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
				
				ct.setId(obj.getString("id"));
				ct.setName(obj.getString("name"));
				ct.setShortLink(obj.getString("shortUrl"));

				if (!obj.isNull("due")) {
					ct.setDataEntrega(formatter.parseDateTime(obj.getString("due")));
				}
				
				ct.setCardDataFinalizada(obj.getBoolean("dueComplete"));
				ct.setDataUltimaAtividade(formatter.parseDateTime(obj.getString("dateLastActivity")));
				ct.setDescricao(obj.getString("desc"));
				
				String[] descricoes = ct.getDescricao().split("@");
				
				int indice;
				for (int j = 0; j < descricoes.length; j++) {
					indice = descricoes[j].indexOf(" ");
					if(indice > 0) {
						ct.getDescricoes().put(descricoes[j].substring(0, indice).trim(), descricoes[j].substring(indice, descricoes[j].length()).trim());
					}
					
				}
				
				
				ct.setIdList(obj.getString("idList"));
				
				endAcoes = "https://api.trello.com/1/cards/"+ct.getId()+"/actions"+autorizacao+criterio;				
				urlAcoes = new URL(endAcoes);
				connAcoes = (HttpURLConnection) urlAcoes.openConnection(proxy);
				connAcoes.setRequestMethod("GET");
				connAcoes.setRequestProperty("Accept", "application/json");
				
				if (connAcoes.getResponseCode() != 200) {
					throw new RuntimeException("Failed! HTTP error code: "+connAcoes.getResponseCode());
				}
				
				buffer = new BufferedReader(new InputStreamReader(connAcoes.getInputStream()));
				output.delete(0, output.length());
				
				while ((line=buffer.readLine()) != null) {
					output.append(line);
				}
				
				arrAcoes = new JSONArray(output.toString());
				
				for(int j=0;j<arrAcoes.length();j++){
					acaojson = arrAcoes.getJSONObject(j);
					acao = new AcaoCard();
					acao.setIdAcao(acaojson.getString("id"));
					acao.setIdCartao(ct.getId());
					acao.setIdCriador(acaojson.getString("idMemberCreator"));
					if(acaojson.getJSONObject("data").has("listBefore")) {
						acao.setIdListaAntes(acaojson.getJSONObject("data").getJSONObject("listBefore").getString("name"));
						acao.setIdListaDepois(acaojson.getJSONObject("data").getJSONObject("listAfter").getString("name"));
					}
					System.out.println(acao.toString());
				}
				
				System.out.println(ct.toString());
			}

			conn.disconnect();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

}
