package com.gitapi.github.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;


public class Http {

	public String chamaUrl(String busca, String lingua, boolean ordem) throws IOException {
		/*
		URL facebook = new URL("https://api.github.com/users/lfbrf/repos");
		BufferedReader bufferedReader = new BufferedReader(https://api.github.com/users/lfbrf/repos
		        new InputStreamReader(facebook.openStream())); 
		*/
		System.out.println("https://api.github.com/search/repositories?q=" +busca + "&language:java&sort=stars&order=desc");
		String url = "https://api.github.com/search/repositories?q=" +busca + "&language:java&sort=stars&order=desc";
		
		
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
		
		
		
		
/*		
HttpURLConnection httpcon = (HttpURLConnection) new URL("https://api.github.com/users/lfbrf/repos").openConnection();
		
		
		
		
		httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
		BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

		//Read line by line
		StringBuilder responseSB = new StringBuilder();
		String line;
		while ( ( line = in.readLine() ) != null) {
			responseSB.append("\n " + line);
			//System.out.println(line);
		}
		in.close();
	



		
		//Get Git Hub Downloads of XR3Player
		
		 String x = responseSB.toString();
	      x = x.substring(1);
	      x = x.substring(0, x.length() - 1);
	      return x;
	} */
}
