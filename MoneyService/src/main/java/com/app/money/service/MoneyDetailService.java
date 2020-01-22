package com.app.money.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.money.config.DataGoConfig;
import com.app.money.domain.ResultServListVO;
import com.app.money.domain.ResultWantedDetailVO;

@Service
public class MoneyDetailService {
	
	private final String API_URL = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc";
	
	public String queryHeader (String servId) {
		
		String queryHeader = API_URL
				+ "?crtiKey=" + DataGoConfig.DATA_GO_AUTH
				+ "&callTp=D" // 페이지타입 : 상세
				+ "&servId=" + servId
				
				; 
		
		return queryHeader;
	}
	
	public ResultWantedDetailVO getDetailVO (String servId) {
		
		RestTemplate restTemplate = new RestTemplate();
		URI apiURI = null;
		ResponseEntity<ResultWantedDetailVO> responseEntity = null;
		
		try {
			apiURI = new URI(this.queryHeader(servId));// URI는 try-catch 필요
			responseEntity = restTemplate.exchange(apiURI, HttpMethod.GET, null, ResultWantedDetailVO.class);// 만약 위에서 에러나면 실행하지 않음
			
			ResultWantedDetailVO resultWantedDetailVO = responseEntity.getBody();
			// 만약 위에서 에러나면 실행하지 않음
			// 여기엔 API 응답메세지가 resultWantedDetailVO형 데이터로 담겨있다
			
			if(resultWantedDetailVO == null) {
				resultWantedDetailVO = new ResultWantedDetailVO();
				// 만약 API 조회결과 resultWantedDetailVOt가 null이라면 NullPointerException 방지를 위해 초기화 해주기 
			}
			
			// 문제 없을시 컨트롤러로 resultWantedDetailVO 리턴
			return resultWantedDetailVO;
			
		} catch (URISyntaxException e) {
			// TODO check1
			// 에러 발생시 컨트롤러로 null 리턴
			e.printStackTrace();
			return null;
		}
		
	}

}
