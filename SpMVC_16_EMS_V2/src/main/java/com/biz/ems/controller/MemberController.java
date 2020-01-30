package com.biz.ems.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.ems.domain.NaverMemberResponse;
import com.biz.ems.domain.NaverReturnAuth;
import com.biz.ems.domain.NaverTokenVO;
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
	
	// value는 네이버에 로그인 요청을 보낼 때 네이버가 받을 URL 부분
	// 외부에서 접속할 수 있는 URL이어야 한다
	// 네이버에 로그인이 성공하면 실제로 네이버에서 로그인 인증정보를 보내준다 -> 그 값을 jackson을 이용해 JSON으로 받기
	@ResponseBody
	@RequestMapping(value="naver/ok", method=RequestMethod.GET)
	public NaverMemberResponse naver_ok(@ModelAttribute NaverReturnAuth naverReturn) {
		
		NaverTokenVO nToken = nLoginSvc.oAuthAccessGetToken(naverReturn);
		
		NaverMemberResponse nResponse = nLoginSvc.getNaverMemberInfo(nToken);
		
		return nResponse;
	}
	
}
