package com.app.money.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.money.config.DataGoConfig;
import com.app.money.domain.MoneySearchDTO;
import com.app.money.domain.ResultServListVO;
import com.app.money.domain.ResultWantedListVO;

@Service
public class MoneyListService {
	
	@Autowired
	PaginationService pagiSvc;
	
	private final String API_URL = "http://www.bokjiro.go.kr/openapi/rest/gvmtWelSvc";
	
	// 검색설정이 지정되지 않은 상태에서, 기본으로 세팅할 밸류
	public MoneySearchDTO defaultSearchSetting() {
		return MoneySearchDTO.builder()
				.crtiKey(DataGoConfig.DATA_GO_AUTH)
				.pageNo("1") // 시작페이지 1페이지
				.numOfRows(pagiSvc.getListPerPage()) // 페이지당 보여줄 검색결과
				.callTp("L")
				.srchKeyCode("001")
				.searchWrd("")
				.lifeArray("")
				.charTrgterArray("")
				.obstKiArray("")
				.obstAbtArray("")
				.trgterIndvdlArray("")
				.desireArray("")
				.build();
	}
	
	// API에 보낼 기본적인 URL+쿼리 문자열 만들기
	public String queryHeader(MoneySearchDTO msDTO) {
		
		String searchWrd = "";
		try {
			searchWrd = URLEncoder.encode(msDTO.getSearchWrd(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String queryHeader = API_URL
				+ "?crtiKey=" + msDTO.getCrtiKey()
				+ "&callTp=L" // 페이지타입 : 리스트
				+ "&pageNo=" + msDTO.getPageNo()
				+ "&numOfRows=" + msDTO.getNumOfRows()
				+ "&srchKeyCode=" + msDTO.getSrchKeyCode()
				
				+ "&searchWrd=" + searchWrd
				+ "&lifeArray=" + msDTO.getLifeArray()
				+ "&charTrgterArray=" + msDTO.getCharTrgterArray()
				+ "&obstKiArray=" + msDTO.getObstAbtArray()
				+ "&obstAbtArray=" + msDTO.getObstAbtArray()
				+ "&trgterIndvdlArray=" + msDTO.getTrgterIndvdlArray()
				+ "&desireArray=" + msDTO.getDesireArray()
				
				;
		
		return queryHeader;
	}
	
	
	public ResultWantedListVO getWantedList (MoneySearchDTO msDTO) {
		
		RestTemplate restTemplate = new RestTemplate();
		URI apiURI = null;
		ResponseEntity<ResultWantedListVO> responseEntity = null;
		
		try {
			apiURI = new URI(this.queryHeader(msDTO));// URI는 try-catch 필요
			responseEntity = restTemplate.exchange(apiURI, HttpMethod.GET, null, ResultWantedListVO.class);// 만약 위에서 에러나면 실행하지 않음
			
			ResultWantedListVO resultWantedListVO = responseEntity.getBody();
			// 만약 위에서 에러나면 실행하지 않음
			// 여기엔 API 응답메세지가 resultWantedListVO형 데이터로 담겨있다
			
			List<ResultServListVO> servList = resultWantedListVO.servList;
			// resultWantedListVO에서 servList를 그대로 가져온다(Getter, Setter 사용하지 않았음)
			return resultWantedListVO;
			
		} catch (URISyntaxException e) {
			// TODO check1
			// 에러 발생시 컨트롤러로 null 리턴
			e.printStackTrace();
			return null;
		}
		
	}

	public String calcLifeArray(String birth) {
		
		int bYear = Integer.valueOf(birth.substring(0,4));
		int bMonth = Integer.valueOf(birth.substring(4,6));
		int bDay = Integer.valueOf(birth.substring(6,8));
		
		LocalDate birthDate = LocalDate.of(bYear, bMonth, bDay);
		LocalDate today = LocalDate.now( ZoneId.of("Asia/Seoul") );
		
		int age = Period.between(birthDate, today).getYears();
		String lifeArray = "";
		
		if(age < 6) {
			// 영유아
			lifeArray = "001";
		} else if(age < 14) {
			// 아동
			lifeArray = "002";
		} else if(age < 19) {
			// 청소년
			lifeArray = "003";
		} else if(age < 30) {
			// 청년
			lifeArray = "004";
		} else if(age < 64) {
			// 중장년
			lifeArray = "005";
		} else {
			// 노년
			lifeArray = "006";
		}
		
		return lifeArray;
	}
	
	

}
