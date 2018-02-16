package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;

public class ClienteTrelloAcoes {

	public static void main(String[] args) {
		try {
			String api_key = "a2adfb6f5aeb7055d60384593bf0a276";
			String token_trello = "7600ff7f3f0592dedc6b2bf362defa24f44e57d46f2ba965919bf9a2b91ba7a0";
			
			String autorizacao="?key="+api_key+"&token="+token_trello;
			String criterio = "&cards=open&lists=open";
			
			String cardExemplo = "5a1334b735ebacce9f0b3da4";
			
			//https://api.trello.com/1/cards/5a1334b735ebacce9f0b3da4/actions?key=a2adfb6f5aeb7055d60384593bf0a276&token=7600ff7f3f0592dedc6b2bf362defa24f44e57d46f2ba965919bf9a2b91ba7a0
			String endereco = "https://api.trello.com/1/cards/"+cardExemplo+"/actions"+autorizacao+criterio;
			
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
			
			System.out.println(arr.length()+" ações");
			
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
