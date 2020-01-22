package com.biz.ems.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverLoginService {
	
	private final String clientId = "py_PCSPuu6Zh2MKNsfxO";
	private final String loginAPI_URL = "https://nid.naver.com/oauth2.0/authorize";
	private final String callbackLocalURL = "http://localhost:8080/ems/naver/ok";
	private final String callbackURL = "http://callor.com:12600/ems/naver/ok";
	
	public String oAuthLoginGet() {
		String redirectURI = null;
		
		try {
			redirectURI = URLEncoder.encode(callbackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SecureRandom random = new SecureRandom();
		
		// 최대 130bit 크기 임의의 정수를 생성하여 문자열로 만들기 
		String stateKey = new BigInteger(130, random).toString();
		
		log.debug("stateKey : " + stateKey);
		
		String getLoginURL = loginAPI_URL
				+ "?client_id=" + clientId
				+ "&redirectURI=" + redirectURI
				+ "&state=" + stateKey
				
				;
		
		log.debug("URL : " + getLoginURL);
		
		return getLoginURL;
	}

}
