package com.biz.gdata.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.gdata.config.DataGoConfig;
import com.biz.gdata.domain.AreaBaseDTO;
import com.biz.gdata.domain.CityVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Service
public class TourAppService {
	
	@Autowired
	TourGetService tgService;
	
	protected String queryHeader(String servName) throws UnsupportedEncodingException {
		String queryHeader = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + servName
				+ "?ServiceKey="+ DataGoConfig.dataKey
				+ "&MobileApp=" + URLEncoder.encode(DataGoConfig.MY_APP_NAME, "UTF-8")
				+ "&_type=json"
				+ "&MobileOS=ETC"
				+ String.format("&numOfRows=%d", 20)
				+ String.format("&pageNo=%d", 1)
		;
		
		return queryHeader;
	}
	
	public String getAreaQuery() throws UnsupportedEncodingException {
		
		String queryStr = this.queryHeader("areaCode");
		
		return queryStr;
	}
	
	public String getAreaQuery(String cityCode) throws UnsupportedEncodingException {
		
		String queryStr = this.queryHeader("areaCode")
		+ "&areaCode=" + cityCode;
		
		return queryStr;
	}
	
	// Controller에서 호출
	// city 코드가 없으면 실행되는 코드, 시도 리스트 추출
	public List<CityVO> getAreaData() throws JsonSyntaxException, IOException {
		return this.getAreaData(null);
	}
	
	// Controller에서 호출
	// city 코드가 있으면 실행되는 코드, 시군구 리스트를 추출
	public List<CityVO> getAreaData(String cityCode) throws JsonSyntaxException, IOException {
		
		// 이 클래스에서 만든 query 문자열을 tgService의 getDataString()에 보내서
		// 데이터를 조회하는 메소드
		
		String resString = "";
		
		if(cityCode == null) {
			resString = tgService.getDataString(this.getAreaQuery());
		} else {
			resString = tgService.getDataString(this.getAreaQuery(cityCode));
		}
		JsonElement jElement = JsonParser.parseString(resString);
		
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
		
		/* 
		 * TypeToken : Gson에 있는 클래스
		 * JsonArray 데이터를 List형 데이터로 변환하는 도구
		 * 
		 * 1. JsonArray를 List형으로 변환하기 위한 바구니(?) 생성
		 * 2. Gson을 이용하여 List형으로 변환
		 */
		TypeToken<List<CityVO>> cityToken = new TypeToken<List<CityVO>>() { };
		
		// TypeToken 도구를 사용하여 List<Class> 형으로 변환하는 method 호출 
		Gson gson = new Gson();
		List<CityVO> cityList = gson.fromJson(jArrItem, cityToken.getType());
		
		return cityList;
	}
	
	public String getAreaBaseQuery(String cityCode) throws UnsupportedEncodingException {
		return this.getAreaBaseQuery(cityCode, null);
	}
	
	// 지역의 관광정보를 가져오기 위한 쿼리
	public String getAreaBaseQuery(String cityCode, String sigun) throws UnsupportedEncodingException {
		
		String queryStr = this.queryHeader("areaBasedList")
				
				+ "&arrange=A"
				+ "&contentTypeId=15"
				+ String.format("&areaCode=%s", cityCode)
				+ "&listYN=Y"
		;
		if(sigun != null) {
			queryStr += String.format("&sigunguCode=%s", sigun);
		}
		
		return queryStr;
	}
	
	// 지역의 관광정보를 가져오기 위한 메소드
	public List<AreaBaseDTO> getAreaBaseListData(String cityCode, String sigun) throws JsonSyntaxException, IOException {
		
		String resString = tgService.getDataString(this.getAreaBaseQuery(cityCode, sigun));
		
		JsonElement jElement = JsonParser.parseString(resString); 
		JsonObject jObRes = (JsonObject) jElement.getAsJsonObject().get("response");
		JsonObject jObBody = (JsonObject) jObRes.getAsJsonObject().get("body");
		JsonObject jObItems = (JsonObject) jObBody.getAsJsonObject().get("items");
		JsonArray jArrItem = (JsonArray) jObItems.getAsJsonObject().get("item");
		TypeToken<List<AreaBaseDTO>> cityToken = new TypeToken<List<AreaBaseDTO>>() { };
		Gson gson = new Gson();
		List<AreaBaseDTO> baseList = gson.fromJson(jArrItem, cityToken.getType());
		
		return baseList;
	}
	

}
