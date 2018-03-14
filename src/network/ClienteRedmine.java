package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.TicketRedmine;

public class ClienteRedmine {

	public static void main(String[] args) {
		try {
//ArrayList<TicketRedmine> listaTickets = new ArrayList<TicketRedmine>();
			
			//Promagis: 12; versão 1056
			//Certo: 128; versão: 1318
			//consulta com mais de um tracker_id não está funcionando
			//String endereco = "http://redmine.tjpb.jus.br/issues.json?project_id=12&version_id=1056&tracker_id=2,5";
			//String endereco = "http://redmine.tjpb.jus.br/issues.json?project_id=128&fixed_version_id=1318&tracker_id=2&tracker_id=5";
			String endereco = "http://redmine.tjpb.jus.br/issues.json?project_id=12&fixed_version_id=1056";
			URL url  = new URL(endereco);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("X-Redmine-API-Key","f686f32946cff6059a75f8387696f34a34fddf6b");
			//'Content-type: application/json;charset=utf-8'
			
			if(conn.getResponseCode() != 200){
				throw new RuntimeException("Falha! HTTP Error code: "+conn.getResponseCode());
			}
			
			BufferedReader buffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String saida;
			
			StringBuffer output = new StringBuffer();
			
			while((saida = buffer.readLine()) != null){
				output.append(saida);
			}
			
			JSONObject obj = new JSONObject(output.toString());
			JSONArray tickets = obj.getJSONArray("issues");
			
			JSONObject it;
			JSONArray customFields;
			TicketRedmine ticket;
			
			double esforcoTotal=0, esforcoRealizado=0;
			double percEntregue = 0;
			
			for (int i = 0; i < tickets.length(); i++) {
				it = tickets.getJSONObject(i);
				ticket = new TicketRedmine();
				
				ticket.setTicket(it.getInt("id"));
				
				ticket.setIdProjeto(it.getJSONObject("project").getInt("id"));
				ticket.setNomeProjeto(it.getJSONObject("project").getString("name"));
				
				ticket.setIdTipoTicket(it.getJSONObject("tracker").getInt("id"));
				ticket.setTipoTicket(it.getJSONObject("tracker").getString("name"));
				
				ticket.setIdVersao(it.getJSONObject("fixed_version").getInt("id"));
				ticket.setVersao(it.getJSONObject("fixed_version").getString("name"));
				
				ticket.setPercTerminado(it.getInt("done_ratio"));
				
				customFields = it.getJSONArray("custom_fields");
				
				for(int j=0;j<customFields.length();j++){
					//se o custom field for "Esforço Análise"
					if(customFields.getJSONObject(j).getInt("id") == 28){
						ticket.setEsfocoAnalise(customFields.getJSONObject(j).getString("value").equals("")?0:
							Integer.parseInt(customFields.getJSONObject(j).getString("value"))
						);
					}
				}
				//listaTickets.add(ticket);
				
				if(ticket.getIdTipoTicket()==2 || ticket.getIdTipoTicket()==5) {
					esforcoTotal += ticket.getEsfocoAnalise();
					if(ticket.getPercTerminado() > 30) {
						esforcoRealizado += ticket.getEsfocoAnalise();
					}
					System.out.println(ticket.toString());
				}
				if(esforcoTotal>0) {
					percEntregue = esforcoRealizado/esforcoTotal;
				}
				
			}
			
			System.out.println("Esforço Análise: "+percEntregue+" ("+ esforcoRealizado+"/"+esforcoTotal+")");
			
			conn.disconnect();			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
