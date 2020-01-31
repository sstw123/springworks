package com.biz.ems.service;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.biz.ems.config.NAVER;
import com.biz.ems.domain.NaverMember;
import com.biz.ems.domain.NaverMemberResponse;
import com.biz.ems.domain.NaverReturnAuth;
import com.biz.ems.domain.NaverTokenVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NaverLoginService {
	
	@Value("${naver.client.id}")
	private String client_Id;
	@Value("${naver.client.secret}")
	private String client_secret;
	private final String loginAPI_URL = "https://nid.naver.com/oauth2.0/authorize";
	private final String tokenAPI_URL = "https://nid.naver.com/oauth2.0/token";
	private final String callbackURL = "https://callor.com:12600/ems_sianblone/member/naver/ok";
	private final String naverMemberAPI_URL = "https://openapi.naver.com/v1/nid/me";
	
	public String oAuthLoginGet() {
		
		SecureRandom random = new SecureRandom();
		
		// 최대 130bit 크기 임의의 정수를 생성하여 문자열로 만들기 
		String stateKey = new BigInteger(130, random).toString();
		
		log.debug("stateKey : " + stateKey);
		
		
		/*
		 * 스프링 4.1에서 사용하는 UriQuery 문자열을 생성하는 클래스
		 * fromHttpUrl() : 요청을 수행할 server 주소
		 * queryParam() : 변수=값 형태의 query 문자열 생성
		 * build(true) : 생성하는 문자열 중 encoding이 필요한 부분이 있으면 encoding을 수행한다
		 * 5.1 이상에서는 별도로 encoding() 메소드가 있다
		 * queryParam("name", "korea") => ?name=korea
		 * queryParam("name", "korea", "usa") => ?name=korea&name=usa
		 */
		String getLoginURL =
				UriComponentsBuilder.fromHttpUrl(loginAPI_URL)
					.queryParam("client_id", this.client_Id)
					.queryParam("response_type", "code")
					.queryParam("redirect_uri", callbackURL)
					.queryParam("state", stateKey)
					.build(true)
					.toUriString();
		
		/*
		String redirect_uri = null;
		
		try {
			redirect_uri = URLEncoder.encode(callbackURL, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String getLoginURL = loginAPI_URL
				+ "?client_id=" + client_Id
				+ "&response_type=code"
				+ "&redirect_uri=" + redirect_uri
				+ "&state=" + stateKey
				;
		*/
		
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
		MultiValueMap<String, String> bodiesMap = new LinkedMultiValueMap<String, String>();
		
		bodiesMap.add(NAVER.TOKEN.grant_type, NAVER.GRANT_TYPE.authorization_code);
		bodiesMap.add(NAVER.TOKEN.client_id, this.client_Id);
		bodiesMap.add(NAVER.TOKEN.client_secret, this.client_secret);
		bodiesMap.add(NAVER.TOKEN.code, naverReturn.getCode());
		bodiesMap.add(NAVER.TOKEN.state, naverReturn.getState());
		
		// headers에 담긴 정보와 params에 담긴 정보를
		// HttpEntity 형식으로 변환
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String,String>>(bodiesMap, headers);
		
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
		
		/*
		 * body부분에 parameter라는 문자열을 추가하고
		 * header부분에 위의 httpHeaders에서 설정한 형식으로 생성하고 => Authorization="bearer..."
		 * 모두 문자열로 변환하여 Http Protocol 데이터 구조로 변경한다
		 * 그리고 Http Protocol을 사용하여 데이터를 전송할 수 있도록 준비한다
		 * 
		 * HttpEntity<String> 형식으로 선언 : 단일 생성 방식
		 * HttpEntity<MultiValue<String,Object>> 형식으로 선언 : body 부분에 데이터를 다음과 같이 생성 => 변수=[값], 변수=[값]
		 * header부분의 데이터는 [변수:값, 변수:값]
		 */
		HttpEntity<String> request = new HttpEntity<String>("parameter", httpHeaders);//"parameter"가 안들어가도 값이 잘 넘어오지만(보낸 값이 하나라서 그럴 수도 있음) 원래는 쓰는걸 권장한다고 한다
		
		log.debug("ENTITY : " + request.toString());
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<NaverMember> result = null;
		
		result = restTemplate.exchange(naverMemberAPI_URL, HttpMethod.GET, request, NaverMember.class);
		
		NaverMember nMember = result.getBody();
		NaverMemberResponse nResponse = nMember.getResponse();
		
		return nResponse;
		
	}

}
