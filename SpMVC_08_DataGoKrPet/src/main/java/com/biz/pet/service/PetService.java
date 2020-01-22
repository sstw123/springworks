package com.biz.pet.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.pet.config.DataGoConfig;
import com.biz.pet.domain.GoPetVO;
import com.biz.pet.domain.pet_rest.RestVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PetService {
	
	private final String go_pet_url = "http://openapi.jeonju.go.kr/rest/dongmulhospitalservice";
	
	public String getQueryHeader() {
		
		String strQuery = go_pet_url + "/getDongMulHospital"
				+ "?ServiceKey=" + DataGoConfig.DATA_GO_AUTH
				+ "&pageNo=1"
				+ "&numOfRows=100"
				
				;
		
		return strQuery;
	}
	
	public String getDataGo() throws URISyntaxException {
		String strQuery = this.getQueryHeader();
		
		// Spring 3.2에서 도입된 새로운 HttpURLConnection의 추상화된 객체
		// 따라서 pom.xml의 스프링 버전을 올려주어야 한다
		RestTemplate restTemplate = new RestTemplate();
		
		URI restURI = new URI(strQuery);
		
		ResponseEntity<String> responseList = restTemplate.exchange(restURI, HttpMethod.GET, null, String.class);
		// exchange 메소드에게 restURI를 보내고, GET방식으로 받고, String형으로 받기
		
		// getBody() 메소드
		// http 프로토콜에서 헤더와 바디가 있다
		// 헤더에는 정보가, 바디에는 데이터가 있다
		String strResponse = responseList.getBody().toString();
		return strResponse;
	}
	
	public List<GoPetVO> getRestVoList(String s_cate, String s_text) {
		String strQuery = this.getQueryHeader();
		try {
			if(s_cate.equalsIgnoreCase("ADDR")) {
				strQuery += "&address=" + URLEncoder.encode(s_text, "UTF-8");
			} else {
				strQuery += "&dongName=" + URLEncoder.encode(s_text, "UTF-8");
			}
			 
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// HttpRequest Header 설정하기
		HttpHeaders header = new HttpHeaders();
		
		// XML 수신하기
		header.setAccept(Collections.singletonList(MediaType.APPLICATION_XML));
		
		// JSON 수신하기
//		header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters", header);
		
		// Spring 3.2에서 도입된 새로운 HttpURLConnection의 추상화된 객체
		// 따라서 pom.xml의 스프링 버전을 올려주어야 한다
		RestTemplate restTemplate = new RestTemplate();
		
		URI restURI = null;
//		ResponseEntity<String> responseList = null;
		ResponseEntity<RestVO> result = null;
		try {
			restURI = new URI(strQuery);
			
			// exchange 메소드에게 restURI를 보내고, GET방식으로 받고, String형으로 받기
			// responseList = restTemplate.exchange(restURI, HttpMethod.GET, entity, GoPetVO.class);
			result = restTemplate.exchange(restURI, HttpMethod.GET, entity, RestVO.class);
			RestVO restVO = (RestVO) result.getBody();
			
			List<GoPetVO> goPetList = restVO.body.data.list;
			
			return goPetList;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/*
	public List<GoPetVO> getData(String strSearch) {
		JsonElement jElement = JsonParser.parseString(this.getRestString(strSearch));
		
		
		log.debug(jElement.toString());
		JsonObject jObjectBody = (JsonObject) jElement.getAsJsonObject().get("body");
//		JsonObject jObjectData = (JsonObject) jObjectBody.getAsJsonObject().get("data");
		String str = jObjectBody.getAsJsonObject().get("totalCount").getAsString();
//		JsonArray jArrayList = (JsonArray) jObjectData.getAsJsonObject().get("list");
		log.debug("strstr:"+str);
		
		JsonArray jArrayList = null;
		Gson gson = new Gson();
		List<GoPetVO> hosList = null;
		
		// list를 추출하여 JsonArray로 변환하는 과정에서 exception이 발생하면
		try {
			// 
//			jArrayList = (JsonArray) jElement.getAsJsonObject().get("body")
//					.getAsJsonObject().get("data")
//					.getAsJsonObject().get("list");
			
			// 정상적으로 list를 모두 추출했을 경우 전체 데이터의 List를 return
			
		} catch (ClassCastException e) {
			// TODO: exception이 발생했을 때는 1개짜리 list를 만들어서 return
			log.debug("데이터가 1개 뿐");
			// JsonObject petObj = (JsonObject) jObjectData.getAsJsonObject().get("list");
			// TypeToken<GoPetVO> typeToken = new TypeToken<GoPetVO>() {};
			
			// hosList = new ArrayList<GoPetVO>();
			// hosList.add(gson.fromJson(petObj, typeToken.getType());
			// return hosList;
		}
		
		
		// JsonArray를 List로 변환
		
		TypeToken<List<GoPetVO>> typeTokenList = new TypeToken<List<GoPetVO>>() {};
		hosList = gson.fromJson(jArrayList, typeTokenList.getType());
		
		return hosList;
	}
	*/
}
