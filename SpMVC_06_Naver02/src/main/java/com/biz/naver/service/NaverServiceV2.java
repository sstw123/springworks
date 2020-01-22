package com.biz.naver.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.naver.config.NaverConfig;
import com.biz.naver.domain.PageDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverServiceV2 {
	
	private final String naver_news_url = "https://openapi.naver.com/v1/search/news.json";
	private final String naver_book_url = "https://openapi.naver.com/v1/search/book.json";
	private final String naver_movie_url = "https://openapi.naver.com/v1/search/movie.json";
	
	@Autowired
	PageService pService;
	
	public PageDTO getPageInfo(String cate, String search, long currentPageNo) throws IOException, ParseException {
		// 전체 데이터 개수 계산
		String totalQuery = this.queryNaver(cate, search);
		String totalString = this.getNaverString(totalQuery);
		JSONObject totalJObject = this.strToJson(totalString);
		
		// JSONObject에서 key가 total인 항목을 찾아서 값을 문자열로 추출
		String strTotal = totalJObject.get("total").toString(); 
		long totalCount = Long.valueOf(strTotal);
		
		// 네이버는 최대 1000개의 데이터까지 검색을 제공한다 그 이상이면 오류보냄
		if(totalCount > 1000) totalCount = 1000;
		
		PageDTO pageDTO = pService.makePagination(totalCount, currentPageNo);
		
		return pageDTO; 
	}
	
	// 0
	public JSONArray getNaver(String cate, String search, long currentPageNo) throws IOException, ParseException {
		
		PageDTO pageDTO = this.getPageInfo(cate, search, currentPageNo);
		
		long display = pageDTO.getListPerPage();
		long startDataNum = (pageDTO.getCurrentPageNo() - 1) * display + 1;
		
		// 1
		String query = this.queryNaver(cate, search, startDataNum, pageDTO.getListPerPage());
		// 2
		String resString = this.getNaverString(query);
		// 3
		JSONObject resObject = this.strToJson(resString);
		// 4
		JSONArray resArray = this.getArray(resObject, "items");
		
		return resArray;
	}
	
	// 1. cate, search, start, display 값을 매개변수로 받아서
	// start값 번째부터 display값 개수만큼 데이터를 가져오기 위한 query 생성
	public String queryNaver(String cate, String search, long startDataNum, long display) {
		// 검색값이 한글일 때 깨질 경우를 대비한 URLEncoder.encode()를 이용한 "UTF-8" 변환 및 UTF-8 지원하지 않을 경우를 위한 try-catch
		String queryString = "";
		try {
			queryString = URLEncoder.encode(search, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// https://.../?query=aaa
		String query = this.queryURL(cate) + "?query=" + queryString;
		
		// start값 페이지부터 display값 페이지 개수만큼 데이터 받기
		query += "&start=" + startDataNum;
		query += "&display=" + display;
		
		return query;
	}
	
	public String queryNaver(String cate, String search) {
		// 검색값이 한글일 때 깨질 경우를 대비한 URLEncoder.encode()를 이용한 "UTF-8" 변환 및 UTF-8 지원하지 않을 경우를 위한 try-catch
		String queryString = "";
		try {
			queryString = URLEncoder.encode(search, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// https://.../?query=aaa
		String query = this.queryURL(cate) + "?query=" + queryString;
		
		return query;
	}
	
	
	// 2. queryNaver에서 생성한 query 문자열을
	// 네이버에게 전송하고 response된 문자열을 리턴
	public String getNaverString(String query) throws IOException {
		URL url = new URL(query);
		HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
		
		httpUrlConnection.setRequestMethod("GET");
		httpUrlConnection.setRequestProperty("X-Naver-Client-Id", NaverConfig.NaverClientId);
		httpUrlConnection.setRequestProperty("X-Naver-Client-Secret", NaverConfig.NaverClientSecret);
		
		int resCode = httpUrlConnection.getResponseCode();
		
		BufferedReader br = null;
		// 네이버가 정상적으로 요청받아서 200 코드를 보내주면
		if(resCode == 200) {
			// InputStreamReader의 생성자에 HttpURLConnection 객체의 getInputStream을 넣어서 읽어오기 
			InputStreamReader isr = new InputStreamReader(httpUrlConnection.getInputStream());
			br = new BufferedReader(isr);
		} else {
			// 오류가 발생했다면 오류내용을 getErrorStream() 으로 받아오기
			InputStreamReader isr = new InputStreamReader(httpUrlConnection.getErrorStream());
			br = new BufferedReader(isr);
		}
		
		StringBuffer resString = new StringBuffer(); // 자바 1.7 이상에선 String으로 써도 알아서 변환된다
		String reader = "";
		while(true) {
			reader = br.readLine();
			if(reader == null) break;
			resString.append(reader);
		}
		
		if(resCode == 200) {
			return resString.toString();
		} else {
			// 오류발생시
			log.debug("네이버 오류 : " + resString.toString());
			return null;
		}
		
	}
	
	
	// 3. 네이버에서 response한 문자열을 통째로 JSON Object로 변환하기
	// 문자열을 JSONObject로 변환
	public JSONObject strToJson(String jsonString) throws ParseException {
		JSONParser jParser = new JSONParser();
		JSONObject jObject = (JSONObject) jParser.parse(jsonString);
		
		return jObject;
	}
	
	
	// 4. JSONObject로부터 Naver의 items만 추출하여 JSONArray로 변환하기
	// naver로부터 받은 JSON 데이터에서 items 항목을 추출하여 실제 데이터들을 Array로 만들어주는 method
	public JSONArray getArray(JSONObject jObject, String keyString) {
		return (JSONArray) jObject.get(keyString);
	}
	

	public String queryURL(String cate) {
		
		String queryURL = "";
		if(cate.equalsIgnoreCase("NEWS")) {
			queryURL = naver_news_url;
		} else if(cate.equalsIgnoreCase("BOOK")) {
			queryURL = naver_book_url;
		} else if(cate.equalsIgnoreCase("MOVIE")) {
			queryURL = naver_movie_url;
		}
		
		return queryURL;
	}

}
