package com.biz.gdata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.biz.gdata.config.DataGoConfig;

@Service
public class TourService {
	
	public String getQueryStr() throws UnsupportedEncodingException {
		
		String queryStr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode"
				+ "?ServiceKey="+ DataGoConfig.dataKey
				+ "&MobileApp=" + URLEncoder.encode(DataGoConfig.MY_APP, "UTF-8")
				+ "&_type=json"
				+ "&MobileOS=ETC"
				+ String.format("&numOfRows=%d", 10)
				+ String.format("&pageNo=%d", 1);
		
		return queryStr;
	}
	
	public JSONArray getData() throws ParseException, IOException {
		
		JSONParser jParser = new JSONParser();
		JSONObject jObject = (JSONObject) jParser.parse(this.getDataString());
		
		// item 가져오기
		JSONObject jOBody = (JSONObject) jObject.get("body");
		JSONObject jOItems = (JSONObject) jOBody.get("items");
		JSONArray jAItem = (JSONArray) jOItems.get("item");
		
		return jAItem;
	}
	
	public String getDataString() throws IOException {
		URL url = new URL(this.getQueryStr());
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		
		httpConn.setRequestMethod("GET");
		int resCode = httpConn.getResponseCode();
		
		BufferedReader br = null;
		if(resCode == 200) {
			InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
			br = new BufferedReader(isr);
		} else {
			InputStreamReader isr = new InputStreamReader(httpConn.getErrorStream());
			br = new BufferedReader(isr);
		}
		
		String resString = "";
		String reader = "";
		while(true) {
			reader = br.readLine();
			if(reader == null) break;
			resString += reader;
		}
		br.close();
		return resString;
	}

}
