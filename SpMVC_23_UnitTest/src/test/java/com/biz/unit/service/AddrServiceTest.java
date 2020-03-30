package com.biz.unit.service;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
	spring framework 환경에서 test를 수행하기 위한 초기화 구분
	@ContextConfiguration
	Spring 프로젝트의 bean 등 설정 파일을 불러와서
	Spring 프로젝트 실행 환경과 동일하게 테스트를 수행할 수 있도록 설정
	
	locations : context.xml 파일들을 문자열 배열 형태로 설정 가능
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/*-context.xml"})
public class AddrServiceTest {
	
	// Spring의 bean으로 설정된 AddrService를 가져와서 사용할 수 있도록 설정
	// 일반적인 Spring 프로젝트 실행 방법과 동일하게 설정
	@Autowired
	AddrService as;
	
	@Test
	public void addrTest() {
		// 비교할 대상의 데이터를 작성하고, 리턴된 데이터와 비교해서 테스트 수행
		Map<String, String> addr = as.getAddr();
		Map<String, String> addr1 = as.getAddr();
		
		assertEquals(addr,  addr1);
	}
	
	@Test
	public void nameTest() {
		String name= "홍길동";
		String getName = as.getName(name);
		assertEquals("저는 홍길동 입니다", getName);
	}

}
