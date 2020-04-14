package com.biz.sec.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/user")
@Controller
public class UserController {
	
	private final UserService userSvc;
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login() {
		return "auth/login";
	}
	
	@RequestMapping(value = "/join", method=RequestMethod.GET)
	public String join() {
		return "auth/join";
	}
	
	@ResponseBody
	@RequestMapping(value = "/join", method=RequestMethod.POST, produces="text/html; charset=UTF-8")
	public String join(String username, String password) {
		
		log.debug("아이디 {}, 비밀번호 {}", username, password);
		
		userSvc.insert(username, password);
		
		//return "redirect:/";
		return String.format("<b>아이디</b> : %s, <b>비밀번호</b> : %s", username, password);
	}
	
	@ResponseBody
	@RequestMapping(value="/idcheck", method=RequestMethod.GET)
	public boolean idcheck(String username) {
		UserDetailsVO userDetailsVO = userSvc.findByUserName(username);
		
		boolean ret = false;
		
		// 이미 DB에 회원정보가 저장되어있는 경우 체크하기
		// null이 아니면 && 뒤쪽의 if문 진행
		// 먼저 null 체크를 해서 NullPointerException 방지
		if(userDetailsVO != null && userDetailsVO.getUsername().equalsIgnoreCase(username)) {
			ret = true;
		}
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String user() {
		return "user home";
	}
	
	@RequestMapping(value = "/mypage", method=RequestMethod.GET)
	public String mypage(Principal principal, Model model) {
		
		model.addAttribute("BODY", "MYPAGE");
		model.addAttribute("prcp", principal);
		
		return "home";
	}
	
	@RequestMapping(value = "/mypage", method=RequestMethod.POST)
	public String mypage(UserDetailsVO userVO, Principal principal) {
		
		UserDetailsVO tempVO = (UserDetailsVO) principal;
		
		long id = tempVO.getId();
		
		userVO.setId(id);
		
		int ret= userSvc.update(userVO);
		
		return "redirect:/mypage";
	}
	
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout() {
		return "redirect:/";
	}

}