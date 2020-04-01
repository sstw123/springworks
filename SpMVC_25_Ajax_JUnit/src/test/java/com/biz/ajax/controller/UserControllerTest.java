package com.biz.ajax.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.biz.ajax.domain.UserVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
public class UserControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	private UserController userController;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders
				.standaloneSetup(userController)
				.build();
	}

	@Test
	public void testSaveUser() throws Exception {
		UserVO userVO = new UserVO();
		
		/*
			saveuser를 POST 방식으로 호출하면서
			4개의 requestParam 데이터를 주입하고
			결과가 200인지 검사하고
			최종적으로 model에 담겨서 리턴되는 값 테스트
		 */
		mockMvc
			.perform( post("/saveuser")
					.param("userId", userVO.makeSampleVO().getUserId())
					.param("password", userVO.makeSampleVO().getPassword())
					.param("userName", userVO.makeSampleVO().getUserName())
					.param("role", userVO.makeSampleVO().getRole())
			)
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("USER_VO"))
			.andReturn();
	}
	
	@Test
	public void sendUserIdTest() throws Exception {
		mockMvc.perform(post("/senduserid")
				.param("userId", "admin")
				.param("password", "12345")
				.param("userName", "hong")
				.param("role", "admin")
				)
		.andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect(jsonPath("$.userVO.userId").exists())
		
		;
	}

}
