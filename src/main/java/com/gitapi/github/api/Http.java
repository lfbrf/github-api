package com.gitapi.github.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;

import org.apache.tomcat.util.codec.binary.Base64;


public class Http {

	public String chamaUrl(String busca, String language, boolean ordem, boolean listaBanco ) throws IOException {
		String url = "";
		if (listaBanco) {
			url = "https://api.github.com/repositories/" + busca;
		}
		else {
			url = "https://api.github.com/search/repositories?q=" + busca;
			if (!(language == null) && !(language.equals("")) &&  !language.equals("Todas") && language != "Todas") {
				url = url + "language:"+language;
			}

			url = url + "&sort=stars&order=desc";
		}

		

		String token = "b88b5b70096a0111461f277e972cc1909989fd2f";
 
		//String urvl = "raw.githubusercontent.com/Crunchify/All-in-One-Webmaster/master/readme.txt";
 
		// HttpClient Method to get Private Github content with Basic OAuth token
		System.out.println("ANTES DA CHAMADA POR UTL");
		
		
		
		HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();

		httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
		httpcon.setRequestProperty("charset", "utf-8");
		String authString = "Basic " + Base64.encodeBase64String(token.getBytes());
		httpcon.setRequestProperty("Authorization", authString);


		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

		String retornoJson;

		StringBuilder builder = new StringBuilder();


		while ((retornoJson = bufferedReader.readLine()) != null) {

			builder.append(retornoJson);
		}

		bufferedReader.close();
		String x = builder.toString();

		
	
		
		
		return x;

	}  
	
	
	private static String getGithubContentUsingURLConnection(String token, String url) {
		String newUrl =  url;
		System.out.println(newUrl);
		try {
			URL myURL = new URL(newUrl);
			URLConnection connection = myURL.openConnection();
			token = token + ":x-oauth-basic";
			String authString = "Basic " + Base64.encodeBase64String(token.getBytes());
			connection.setRequestProperty("Authorization", authString);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
			connection.addRequestProperty("User-Agent", "Mozilla/5.0");
			connection.setRequestProperty("charset", "utf-8");
			System.out.println("Ok veio aqui");
			InputStream crunchifyInStream = connection.getInputStream();
			System.out.println(crunchifyGetStringFromStream(crunchifyInStream));
			return crunchifyGetStringFromStream(crunchifyInStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
		if (crunchifyStream != null) {
			Writer crunchifyWriter = new StringWriter();
 
			char[] crunchifyBuffer = new char[2048];
			try {
				Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
				int counter;
				while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
					crunchifyWriter.write(crunchifyBuffer, 0, counter);
				}
			} finally {
				crunchifyStream.close();
			}
			return crunchifyWriter.toString();
		} else {
			return "No Contents";
		}
	}
	
	
}
