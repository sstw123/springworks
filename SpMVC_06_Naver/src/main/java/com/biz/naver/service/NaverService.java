package com.biz.naver.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import com.biz.naver.config.NaverConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverService {
	
	private final String naver_news_url = "https://openapi.naver.com/v1/search/news.json";
	private final String naver_book_url = "https://openapi.naver.com/v1/search/book.json";
	private final String naver_movie_url = "https://openapi.naver.com/v1/search/movie.json";
	
	public JSONArray strToJson(String strText) throws ParseException {
		
		JSONParser jParser = new JSONParser();
		JSONObject jObject = null;
		
		// json형 문자열을 JSONObject로 변환하는 method
		jObject = (JSONObject) jParser.parse(strText);
		
		// items tag가 품고있는 데이터들을 JSON 배열 객체로 만들기
		JSONArray jArray = (JSONArray) jObject.get("items");
		
		return jArray;
	}
	
	public JSONArray getNews(String cate, String search) {
		
		log.debug("서비스");
		
		URL url;
		HttpURLConnection httpConn;
		
		try {
			// naver에 보낼 query URL을 UTF-8 방식으로 인코딩 수행
			
			String queryText = "";
			
			queryText = URLEncoder.encode(search, "UTF-8");
			// URLEncoder.encode()를 사용하는 이유 : 웹에서 검색했는데 한글(영어 이외의 문자)이 깨져서 이상하게 날아갈 수 있다
			// 따라서 UTF-8로 변환을 해주어야 하는데 API마다 지원하는 인코딩이 다르다
			// 네이버에선 UTF-8을 지원하므로 UTF-8로 설정해준다
			
			String queryURL = naver_news_url;
			
			if(cate.equalsIgnoreCase("news")) {
				queryURL = naver_news_url;
			} else if(cate.equalsIgnoreCase("book")) {
				queryURL = naver_book_url;
			} else if(cate.equalsIgnoreCase("movie")) {
				queryURL = naver_movie_url;
			}
			queryText = queryURL + "?query=" + queryText;
			
			// queryText 문자열을 URL의 생성자에 넣어 http 프로토콜의 header를 작성 
			url = new URL(queryText);
			
			// url 정보를 가지고 접속통로 생성하기
			httpConn = (HttpURLConnection) url.openConnection();
			
			// naver에 query를 수행하기 위해 정보를 설정(보내기)
			httpConn.setRequestMethod("GET");
			
			// id는 노출되면 안되기 때문에 http body의 감춰진 영역에 값을 저장하여 보낸다
			httpConn.setRequestProperty("X-Naver-Client-Id", NaverConfig.NaverClientId);
			httpConn.setRequestProperty("X-Naver-Client-Secret", NaverConfig.NaverClientSecret);
			
			// 네이버에 쿼리를 보낸 것에 대해 응답 코드를 받는다
			int resCode = httpConn.getResponseCode();
			log.debug("상태코드 : " + resCode);
			
			if(resCode == 200) {
				InputStreamReader isr = new InputStreamReader( httpConn.getInputStream() );
				BufferedReader br = new BufferedReader(isr);
				
				String retString = "";
				String reader = new String();
				while(true) {
					
					reader = br.readLine();
					if(reader == null) break;
					retString += reader;
				}
				br.close();
				return this.strToJson(retString);
			}
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
