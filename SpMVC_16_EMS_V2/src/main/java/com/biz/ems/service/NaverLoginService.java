package com.biz.ems.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.SecureRandom;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.biz.ems.config.NAVER;
import com.biz.ems.domain.NaverMember;
import com.biz.ems.domain.NaverMemberResponse;
import com.biz.ems.domain.NaverReturnAuth;
import com.biz.ems.domain.NaverTokenVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverLoginService {
	
	private final String client_Id = "py_PCSPuu6Zh2MKNsfxO";
	private final String client_secret = "Dgu9Xb2Gby";
	private final String loginAPI_URL = "https://nid.naver.com/oauth2.0/authorize";
	private final String tokenAPI_URL = "https://nid.naver.com/oauth2.0/token";
	private final String callbackURL = "https://callor.com:12600/ems_sianblone/member/naver/ok";
	private final String naverMemberAPI_URL = "https://openapi.naver.com/v1/nid/me";
	
	public String oAuthLoginGet() {
		String redirect_uri = null;
		
		try {
			redirect_uri = URLEncoder.encode(callbackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SecureRandom random = new SecureRandom();
		
		// 최대 130bit 크기 임의의 정수를 생성하여 문자열로 만들기 
		String stateKey = new BigInteger(130, random).toString();
		
		log.debug("stateKey : " + stateKey);
		
		String getLoginURL = loginAPI_URL
				+ "?client_id=" + client_Id
				+ "&response_type=code"
				+ "&redirect_uri=" + redirect_uri
				+ "&state=" + stateKey
				
				;
		
		log.debug("URL : " + getLoginURL);
		
		return getLoginURL;
	}
	
	/**
	 * 네이버에 정보요구를 할 때 사용할 토큰을 요청
	 * token을 요청하기 위해서 GET/POST method를 사용할 수 있는데
	 * 여기서는 POST를 사용해서 요청을 하겠다
	 * @param naverOk
	 */
	public NaverTokenVO oAuthAccessGetToken(NaverReturnAuth naverReturn) {
		
		// 아래 세줄의 코드는 필요없다
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-Naver-Client-Id", this.client_Id);
		headers.set("X-Naver-Client-Secret", this.client_secret);
		
		/*
		 * MultiValueMap <K, V>
		 * http 프로토콜에서 사용할 수 있도록 Map 인터페이스를 상속받은 spring 전용 Map 인터페이스
		 * http 프로토콜과 관련된 내장 method가 포함되어 있다
		 */
		MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<String, String>();
		
		paramsMap.add(NAVER.TOKEN.grant_type, NAVER.GRANT_TYPE.authorization_code);
		paramsMap.add(NAVER.TOKEN.client_id, this.client_Id);
		paramsMap.add(NAVER.TOKEN.client_secret, this.client_secret);
		paramsMap.add(NAVER.TOKEN.code, naverReturn.getCode());
		paramsMap.add(NAVER.TOKEN.state, naverReturn.getState());
		
		// headers에 담긴 정보와 params에 담긴 정보를
		// HttpEntity 형식으로 변환
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(paramsMap, headers);
		
		/*
		// 주소가 전부 영어일 경우 URLEncoder() 필요 없음
		URI restURI = null;
		try {
			restURI = new URI(tokenAPI_URL);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		/*
		 * RestTemplate를 사용하여 네이버에 token을 요청
		 */
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NaverTokenVO> result = null;
		
		result = restTemplate.exchange(tokenAPI_URL, HttpMethod.POST, request, NaverTokenVO.class);
		
		log.debug("NAVER TOKEN : " + result.getBody().toString());
		
		return result.getBody();
		
	}
	
	public NaverMemberResponse getNaverMemberInfo(NaverTokenVO tokenVO) {
		
		String token = tokenVO.getAccess_token();
		String header = tokenVO.getToken_type() + " " + token;//bearer token
		// header 문자열을 HttpHeaders에 실어서 GET방식으로 요청
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Authorization", header);
		
		HttpEntity<String> request = new HttpEntity<String>("parameter", httpHeaders);//"parameter"가 안들어가도 값이 잘 넘어오지만(보낸 값이 하나라서 그럴 수도 있음) 원래는 쓰는걸 권장한다고 한다
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NaverMember> result = null;
		
		result = restTemplate.exchange(naverMemberAPI_URL, HttpMethod.GET, request, NaverMember.class);
		
		NaverMember nMember = result.getBody();
		NaverMemberResponse nResponse = nMember.getResponse();
		
		return nResponse;
		
	}

}
