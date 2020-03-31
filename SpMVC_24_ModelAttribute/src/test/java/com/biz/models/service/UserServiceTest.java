package com.biz.models.service;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.biz.models.domain.UserVO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/appServlet/*-context.xml"} )
public class UserServiceTest {
	
	@Autowired
	UserService userService;
	
	
	@Test
	public void a_deleteTest() {
		int ret = userService.delete("korea");
		ret += userService.delete("admin");
		ret += userService.delete("guest");
		ret += userService.delete("dba");
		
		assertEquals(ret, 4, 0);
	}
	


	@Test
	public void b_insertTest() {
		UserVO userVO = UserVO.builder()
				.userId("korea")
				.password("12345")
				.userName("대한민국")
				.userRole("gov")
				.build();
		int return1 = userService.insert(userVO);
		assertEquals(return1, 1, 0);
		
		userVO = UserVO.builder()
				.userId("admin")
				.password("12345")
				.userName("홍길동")
				.userRole("admin")
				.build();
		return1 = userService.insert(userVO);
		assertEquals(return1, 1, 0);
		
		userVO = UserVO.builder()
				.userId("guest")
				.password("12345")
				.userName("성춘향")
				.userRole("guest")
				.build();
		return1 = userService.insert(userVO);
		assertEquals(return1, 1, 0);
		
		userVO = UserVO.builder()
				.userId("dba")
				.password("12345")
				.userName("이몽룡")
				.userRole("dba")
				.build();
		return1 = userService.insert(userVO);
		assertEquals(return1, 1, 0);
		
	}
	
	@Test
	public void c_test() {
		UserVO userVO = userService.getUser("admin");
		assertEquals(userVO.getUserId(), "admin");
		assertEquals(userVO.getUserName(), "홍길동");
		
		userVO = userService.getUser("guest");
		assertEquals(userVO.getUserId(), "guest");
		assertEquals(userVO.getUserName(), "성춘향");
		
		userVO = userService.getUser("dba");
		assertEquals(userVO.getUserId(), "dba");
		assertEquals(userVO.getUserName(), "이몽룡");
		
	}
	
}
