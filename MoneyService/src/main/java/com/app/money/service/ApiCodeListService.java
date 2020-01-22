package com.app.money.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ApiCodeListService {
	
	// key는 api 변수명, value는 가져올 하위 Map 변수명
	public Map<String, Map<String, String>> selectApiCodeMap() {
		Map<String, Map<String, String>> selectApiCodeMap = new HashMap<String, Map<String, String>>();
		
		selectApiCodeMap.put("srchKeyCode", this.srchKeyCode());
		selectApiCodeMap.put("lifeArray", this.lifeArray());
		selectApiCodeMap.put("charTrgterArray", this.charTrgterArray());
		selectApiCodeMap.put("obstKiArray", this.obstKiArray());
		selectApiCodeMap.put("obstAbtArray", this.obstAbtArray());
		selectApiCodeMap.put("trgterIndvdlArray", this.trgterIndvdlArray());
		selectApiCodeMap.put("desireArray", this.desireArray());
		
		return selectApiCodeMap;
	}
	
	
	// 검색분류 : key는 api 코드값, value는 웹화면에 표시할 값 
	public Map<String, String> srchKeyCode() {
		Map<String, String> srchKeyCode = new HashMap<String, String>();
		
		srchKeyCode.put("001", "제목");
		srchKeyCode.put("002", "내용");
		srchKeyCode.put("003", "제목+내용");
		
		return srchKeyCode;
	}
	
	// 생애주기 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> lifeArray() {
		Map<String, String> lifeArray = new HashMap<String, String>();
		
		lifeArray.put("","해당없음");
		lifeArray.put("001","영유아");
		lifeArray.put("002","아동");
		lifeArray.put("003","청소년");
		lifeArray.put("004","청년");
		lifeArray.put("005","중장년");
		lifeArray.put("006","노년");
		
		return lifeArray;
		
	}
	
	// 대상특정 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> charTrgterArray() {
		Map<String, String> charTrgterArray = new HashMap<String, String>();
		
		charTrgterArray.put("001", "해당없음");
		charTrgterArray.put("002", "여성");
		charTrgterArray.put("003", "임산부");
		charTrgterArray.put("004", "장애");
		charTrgterArray.put("005", "국가유공자등 보훈대상자");
		charTrgterArray.put("006", "실업자");
		
		return charTrgterArray;
	}
	
	// 장애유형 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> obstKiArray() {
		Map<String, String> obstKiArray = new HashMap<String, String>();
		
		obstKiArray.put("", "해당없음");
		obstKiArray.put("10", "지체");
		obstKiArray.put("20", "시각");
		obstKiArray.put("30", "청각");
		obstKiArray.put("40", "언어");
		obstKiArray.put("50", "지적");
		obstKiArray.put("60", "뇌병변");
		obstKiArray.put("70", "자폐성");
		obstKiArray.put("80", "정신");
		obstKiArray.put("90", "신장");
		obstKiArray.put("A0", "심장");
		obstKiArray.put("B0", "호흡기");
		obstKiArray.put("C0", "간");
		obstKiArray.put("D0", "안면");
		obstKiArray.put("E0", "장루");
		obstKiArray.put("F0", "간질");

		return obstKiArray;
	}
	
	// 장애정도 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> obstAbtArray() {
		Map<String, String> obstAbtArray = new HashMap<String, String>();
		obstAbtArray.put("", "해당없음");
		obstAbtArray.put("10", "심한 장애");
		obstAbtArray.put("20", "심하지 않은 장애");
		
		return obstAbtArray;
	}
	
	// 가구유형 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> trgterIndvdlArray() {
		Map<String, String> trgterIndvdlArray = new HashMap<String, String>();
		
		trgterIndvdlArray.put("001", "해당없음");
		trgterIndvdlArray.put("002", "한부모");
		trgterIndvdlArray.put("003", "다문화");
		trgterIndvdlArray.put("004", "조손");
		trgterIndvdlArray.put("005", "새터민");
		trgterIndvdlArray.put("006", "소년소녀가장");
		trgterIndvdlArray.put("007", "독거노인");
		
		return trgterIndvdlArray;
	}
	
	// 니즈 : key는 api 코드값, value는 웹화면에 표시할 값
	public Map<String, String> desireArray() {
		Map<String, String> desireArray = new HashMap<String, String>();
		
		desireArray.put("", "해당없음");
		desireArray.put("0000000", "안전");
		desireArray.put("1000000", "건강");
		desireArray.put("2000000", "일상생활유지");
		desireArray.put("3000000", "가족관계");
		desireArray.put("4000000", "사회적 관계");
		desireArray.put("5000000", "경제");
		desireArray.put("6000000", "교육");
		desireArray.put("7000000", "고용");
		desireArray.put("8000000", "생활환경");
		desireArray.put("9000000", "법률 및 권익보장");
		desireArray.put("A000000", "기타");

		return desireArray;
	}

}
