package com.biz.bokji.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.bokji.config.DataGoConfig;
import com.biz.bokji.domain.BokDetailVO;
import com.biz.bokji.domain.BokListVO;
import com.biz.bokji.domain.BokSearchDTO;
import com.biz.bokji.domain.WantedList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BokDetailService {
	
	private final String BOK_URL = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc";
	
	public String getQueryHeader(String servId) {
		
		String query = BOK_URL;
		
		query += "?crtiKey=" + DataGoConfig.DATA_GO_AUTH
		+ "&callTp=D"
		+ "&servId=" + servId
		;
		
		return query;
	}
	
	public BokDetailVO getRestResult(String servId) {
		
		// RestTemplate를 사용하여 api에 보낼 URI를 생성w
		URI restURI = null;
		
		// 생성된 URI를 api 서버에 보내서 데이터를 수신할 객체(도구)
		RestTemplate restTemp = new RestTemplate();
		
		// xml을 data로 변환할 때 사용할 객체(도구)
		ResponseEntity<BokDetailVO> result = null;

		try {
			restURI = new URI(this.getQueryHeader(servId));
			result = restTemp.exchange(restURI, HttpMethod.GET, null, BokDetailVO.class);
			//RestTemplate.exchange(주소(URI), 메소드방식(HttpMethod.GET / POST), 헤더, restURI를 서버에 보내서 수신된 xml형 문자열을 WantedList 클래스의 형식에 맞추어서 객체로 만들기)
			
			log.debug("헤더 : " + result.getHeaders().toString());
			log.debug("바디 : " + result.getBody().toString());
			
			// 실제 데이터가 들어있는 body만 추출
			// body에는 WantedList 클래스형 데이터가 들어있다
			BokDetailVO bdVO = (BokDetailVO)result.getBody();
			
			// Controller로 return
			return bdVO;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	// 검색에 필요한 기본 변수들 세팅
	public BokSearchDTO getDefaultSearch() {
		return BokSearchDTO.builder()
				.pageNo("1")
				.numOfRows("10").build();
	}

}
