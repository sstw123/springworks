package com.biz.bokji.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.biz.bokji.config.DataGoConfig;
import com.biz.bokji.domain.BokListVO;
import com.biz.bokji.domain.BokSearchDTO;
import com.biz.bokji.domain.WantedList;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BokListService {
	
	private final String BOK_URL = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc";
	
	public String getQueryHeader(BokSearchDTO bokDTO) {
		
		String query = BOK_URL;
		
		query += "?crtiKey=" + DataGoConfig.DATA_GO_AUTH
		+ "&callTp=L"
		+ "&pageNo=" + bokDTO.getPageNo()
		+ "&numOfRows=" + bokDTO.getNumOfRows()
		+ "&srchKeyCode=" + bokDTO.getSrchKeyCode();
		
		String searchWrd = "";
		try {
			searchWrd = URLEncoder.encode(bokDTO.getSearchWrd(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		query += "&searchWrd=" + searchWrd
		
		+ "&lifeArray=" + bokDTO.getLifeArray()
		+ "&charTrgterArray=" + bokDTO.getCharTrgterArray()
		+ "&obstKiArray=" + bokDTO.getObstKiArray()
		+ "&obstAbtArray=" + bokDTO.getObstAbtArray()
		+ "&trgterIndvdlArray=" + bokDTO.getTrgterIndvdlArray()
		+ "&desireArray=" + bokDTO.getDesireArray()
		
		;
		
		return query;
	}
	
	public List<BokListVO> getRestResult(BokSearchDTO bokDTO) {
		
		// RestTemplate를 사용하여 api에 보낼 URI를 생성
		URI restURI = null;
		
		// 생성된 URI를 api 서버에 보내서 데이터를 수신할 객체(도구)
		RestTemplate restTemp = new RestTemplate();
		
		// xml을 data로 변환할 때 사용할 객체(도구)
		ResponseEntity<WantedList> result = null;

		try {
			restURI = new URI(this.getQueryHeader(bokDTO));
			result = restTemp.exchange(restURI, HttpMethod.GET, null, WantedList.class);
			//RestTemplate.exchange(주소(URI), 메소드방식(HttpMethod.GET / POST), 헤더, restURI를 서버에 보내서 수신된 xml형 문자열을 WantedList 클래스의 형식에 맞추어서 객체로 만들기)
			
			log.debug("헤더 : " + result.getHeaders().toString());
			log.debug("바디 : " + result.getBody().toString());
			
			// 실제 데이터가 들어있는 body만 추출
			// body에는 WantedList 클래스형 데이터가 들어있다
			WantedList wantedList = (WantedList)result.getBody();
			
			// servList값을 추출하여 필요한 데이터 리스트만 뽑기
			List<BokListVO> bdList = wantedList.servList;
			
			if(bdList.size() < 1) {
				bdList = new ArrayList<BokListVO>();
			} else {
				for(BokListVO vo : bdList) {
					log.debug("요소 : " + vo.toString());
				}
			}
			
			// Controller로 return
			return bdList;
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
