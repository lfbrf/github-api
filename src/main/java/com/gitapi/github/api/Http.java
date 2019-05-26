package com.gitapi.github.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;


public class Http {

	public String chamaUrl(String busca, String language, boolean ordem, boolean listaBanco ) throws IOException {
		String url = "";
		if (listaBanco) {
			url = "https://api.github.com/repositories/" + busca;
			System.out.println("Minha Url");
			System.out.println(url);
		}
		else {
			url = "https://api.github.com/search/repositories?q=" + busca;
			if (!(language == null) && !(language.equals("")) &&  !language.equals("Todas") && language != "Todas") {
				url = url + "language:"+language;
			}

			url = url + "&sort=stars&order=desc";
		}

		System.out.println(url);
		HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();

		httpcon.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); 
		httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
		httpcon.setRequestProperty("charset", "utf-8");


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
}
