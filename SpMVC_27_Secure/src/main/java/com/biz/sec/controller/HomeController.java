package com.biz.sec.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		return "home";
	}
	
	
	
	/**
	 * Controller의 메소드에서 HttpServletRequest 클래스로부터 인증(로그인)정보를 추출하여 세부 항목을 보는 방법
	 * @param id
	 * @param req
	 * @return
	 */
	// security:intercept-url을 Annotation을 이용해서 설정하기
	// @Secured(value={"ROLE1", "ROLE2"}) 문자열 배열 이용
	//@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public String auth(Model model) {
		return "auth/auth_view";
	}
	
	@Secured(value = {"ROLE_ADMIN", "ROLE_USER"})
	@ResponseBody
	@RequestMapping(value = "/auth/{id}", method = RequestMethod.GET)
	public Object auth(@PathVariable("id") String id, HttpServletRequest req) {
		// HttpServletRequest는 클라이언트가 서버에 요청할 때 모든 정보가 포함되어있는 객체
		int intId = 0;
		try {
			intId = Integer.valueOf(id);
		} catch (Exception e) {
			return e.getMessage();
		}
		
		Authentication auth = (Authentication) req.getUserPrincipal();
		
		switch (intId) {
		case 1:
			return auth.getPrincipal();
		case 2:
			return auth.getDetails();
		case 3:
			return auth.getAuthorities();
		case 4:
			return auth.getCredentials();
		case 5:
			return auth.getName();
		default:
			return "Not Found";
		}
		
		//return auth;
	}
	
}
