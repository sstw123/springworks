package com.biz.gdata.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.biz.gdata.config.DataGoConfig;

@Service
public class TourGetService {
	
	/*
	 * query 문자열을 공공DB에 request하고
	 * response된 문자열을 return하는 메소드
	 * 
	 * 1. URL 객체 생성 및 초기화 생성자에 URL+queryString 입력
	 * 2. HttpURLConnection 객체 생성하고 URL 객체의 openConnection() 메소드를 HttpURLConnection으로 형변환하여 주소값 연결
	 * 3. HttpURLConnection 객체를 setRequestMethod("GET"); 메소드를 이용해 GET방식으로 변경
	 */
	public String getDataString(String queryString) throws IOException {
		URL url = new URL(queryString);
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
	
	/*
	public List<CityVO> getData() throws JsonSyntaxException, IOException {
		
		// JSONParser jParser = new JSONParser();

		// response로 받은 문자열
		// JSONObject jObject = (JSONObject) jParser.parse(this.getDataString());
		
		JsonElement jElement = JsonParser.parseString(this.getDataString()); 
		
		// response tag
		// JSONObject jORes = (JSONObject) jObject.get("response");
		JsonObject jObRes = (JsonObject) jElement.getAsJsonObject().get("response");
		
		// response body
		// JSONObject jOBody = (JSONObject) jORes.get("body");
		JsonObject jObBody = (JsonObject) jObRes.getAsJsonObject().get("body");
		
		// response body items
		// JSONObject jOItems = (JSONObject) jOBody.get("items");
		JsonObject jObItems = (JsonObject) jObBody.getAsJsonObject().get("items");
		
		// response body items item
		// JSONArray jArrItem = (JSONArray) jOItems.get("item");
		JsonArray jArrItem = (JsonArray) jObItems.getAsJsonObject().get("item");
		
		 
		// TypeToken : Gson에 있는 클래스
		// JsonArray 데이터를 List형 데이터로 변환하는 도구
		 
		// 1. JsonArray를 List형으로 변환하기 위한 바구니(?) 생성
		// 2. Gson을 이용하여 List형으로 변환
		TypeToken<List<CityVO>> cityToken = new TypeToken<List<CityVO>>() { };
		
		// TypeToken 도구를 사용하여 List<Class> 형으로 변환하는 method 호출 
		Gson gson = new Gson();
		List<CityVO> cityList = gson.fromJson(jArrItem, cityToken.getType());
		
		return cityList;
	}
	*/

	
	public String getQueryStr() throws UnsupportedEncodingException {
		
		String queryStr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaCode"
				+ "?ServiceKey="+ DataGoConfig.dataKey
				+ "&MobileApp=" + URLEncoder.encode(DataGoConfig.MY_APP_NAME, "UTF-8")
				+ "&_type=json"
				+ "&MobileOS=ETC"
				+ String.format("&numOfRows=%d", 20)
				+ String.format("&pageNo=%d", 1);
		
		return queryStr;
	}

}
