package com.biz.ems;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class Main {
	
	public static void main(String[] args) {
		
		String url = "https://stackoverflow.com/questions/14321873/java-url-encoding-urlencoder-vs-uri";
		
		try {
			String urlENC = URLEncoder.encode(url, "UTF-8");
			System.out.println("urlENC : " + urlENC);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			URI uri = new URI(url);
			System.out.println("uri : " + uri);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
