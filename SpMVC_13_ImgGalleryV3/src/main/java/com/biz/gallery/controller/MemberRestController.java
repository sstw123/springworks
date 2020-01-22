package com.biz.gallery.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.gallery.domain.MemberVO;
import com.biz.gallery.service.MemberService;

@RequestMapping("rest/member")
@RestController
public class MemberRestController {
	
	private final MemberService memberSvc;
	
	@Autowired
	public MemberRestController(MemberService memberSvc) {
		this.memberSvc = memberSvc;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(MemberVO memberVO, Model model, HttpSession httpSession) {
		
		MemberVO dbMemberVO = memberSvc.login(memberVO);
		if(dbMemberVO != null) {
			httpSession.setAttribute("MEMBER", memberVO);
			return "LOGIN_OK";
		} else {
			httpSession.removeAttribute("MEMBER");
			return "LOGIN_FAIL";
		}
	}

}
