package com.biz.ajax.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

// *.class 코드
// 매개변수로 실제 *.class 파일을 직접 주입하고 그 파일을 실행하여 결과를 스스로 알아서 가져간다

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class HomeControllerTest {
	
	// 가상의 Mock 클래스 생성, 의존성 주입을 하기 위한 도구
	MockMvc mockMvc;
	
	// @InjectMocks : HomeController를 단독으로 테스트하겠다는 선언
	@InjectMocks
	private HomeController homeController;

	@Before
	public void setUp() throws Exception {
		
		// @InjectMocks로 설정한 클래스의 의존성 주입
		// HomeController를 사용할 수 있도록 초기화
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(homeController)
				.build();
	}

	@Test
	public void testHome() throws Exception {
		
		System.out.println();
		// http://localhost:8080/ URL로 request 요청
		// controller가 상태 코드를 200(OK) response 했는지 test 수행
		mockMvc.perform(get("/"))
			.andExpect(status().isOk()) // Controller의 응답 코드가 "200"?
			.andExpect(view().name("home")); // Controller의 마지막 return view 이름이 "home"?
		
	}

}
