package com.biz.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.ems.domain.NaverLoginOk;
import com.biz.ems.service.NaverLoginService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="member")
@Controller
public class MemberController {
	
	private final NaverLoginService nLoginSvc;

	@RequestMapping(value="naver", method=RequestMethod.GET)
	public String naver_login() {
		String loginURL = nLoginSvc.oAuthLoginGet();
		
		return "redirect:" + loginURL;
	}
	
	@ResponseBody
	@RequestMapping(value="naver/ok", method=RequestMethod.GET)
	public NaverLoginOk naver_ok(@ModelAttribute NaverLoginOk naverOk) {
		return naverOk;
	}
	
}
