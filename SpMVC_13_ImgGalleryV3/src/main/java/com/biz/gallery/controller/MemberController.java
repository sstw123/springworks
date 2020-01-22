package com.biz.gallery.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.gallery.domain.MemberVO;
import com.biz.gallery.service.MemberService;

@RequestMapping("member")
@Controller
public class MemberController {
	
	private final MemberService memberSvc;
	
	@Autowired
	public MemberController(MemberService memberSvc) {
		this.memberSvc = memberSvc;
	}

	@RequestMapping(value="join", method=RequestMethod.GET)
	public String join(Model model) {
		
		model.addAttribute("MODAL", "JOIN");
		return "home";
	}
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public String join(MemberVO memberVO, Model model) {
		
		memberSvc.insert(memberVO);
		
		return "redirect:/image/list";
	}
	
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login() {
		return "body/member-login";
	}
	
	
	//@ResponseBody
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(MemberVO memberVO, Model model, HttpSession httpSession) {
		
		MemberVO dbMemberVO = memberSvc.login(memberVO);
		if(dbMemberVO != null) {
			httpSession.setAttribute("MEMBER", memberVO);
			//return "LOGIN_OK";
		} else {
			httpSession.removeAttribute("MEMBER");
			//return "LOGIN_FAIL";
		}
		
		return "redirect:/image/list";
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("MEMBER");
		return "redirect:/image/list";
	}
	
}
