package com.biz.naver.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.naver.config.NaverConfig;
import com.biz.naver.domain.NaverItemVO;
import com.biz.naver.domain.NaverSearchCover;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NaverService {
	
	private final String naver_news = "https://openapi.naver.com/v1/search/news.json";
	private final String naver_book = "https://openapi.naver.com/v1/search/book.json";
	private final String naver_movie = "https://openapi.naver.com/v1/search/movie.json";
	
	public List<NaverItemVO> naverSearch(String cate, String search) throws UnsupportedEncodingException, URISyntaxException {
		
		search = URLEncoder.encode(search, "UTF-8");
		String query = "";
		
		if (cate.equalsIgnoreCase("NEWS")) {
			query = naver_news + "?query=" + search;
		} else if (cate.equalsIgnoreCase("BOOK")) {
			query = naver_book + "?query=" + search;
		} else if (cate.equalsIgnoreCase("MOVIE")) {
			query = naver_movie + "?query=" + search;
		}
		
		
		// RestTemplate로 조회하기 위해
		HttpHeaders header = new HttpHeaders();
		
		header.set("X-NAVER-Client-Id", NaverConfig.NAVER_CLIENT_ID);
		header.set("X-NAVER-Client-Secret", NaverConfig.NAVER_CLIENT_SEC);
		HttpEntity<String> entity = new HttpEntity<String>(header);
		
		// 주소 변환
		URI restURI = new URI(query);
		RestTemplate restTemp = new RestTemplate();
		
		// 데이터를 받아서 사용할 객체 타입 지정
		ResponseEntity<String> strResult = null;
		
		ResponseEntity<NaverSearchCover> restResult = null;
		
		// 문자열 형태로 받기
		strResult = restTemp.exchange(restURI, HttpMethod.GET, entity, String.class);
		
		restResult = restTemp.exchange(restURI, HttpMethod.GET, entity, NaverSearchCover.class);
		
		// 리턴값
		// return strResult.getBody();
		NaverSearchCover sc = restResult.getBody();
		log.debug("가져온 데이터 개수 {} ", sc.total);
		if (Integer.valueOf(sc.total) < 1) return null;
		else return sc.items;
	}
	
}
