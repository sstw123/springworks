package com.biz.friend.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.friend.domain.MemberDTO;
import com.biz.friend.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="member")
@RestController
public class MemberRestController {
	
	protected final MemberService mSvc;
	
	@RequestMapping(value="join", method=RequestMethod.POST)
	public boolean join(MemberDTO mDTO) {
		int ret = mSvc.insert(mDTO);
		
		boolean joinResult = false;
		if(ret > 0) {
			joinResult = true;
		}
		return joinResult;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public boolean login(MemberDTO mDTO, HttpSession httpSession) {
		
		boolean loginResult = mSvc.login(mDTO);
		if(loginResult) {
			mDTO.setM_pw(null);
			httpSession.setAttribute("MEMBER", mDTO);
		}
		
		return loginResult;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.POST)
	public String logout(HttpSession httpSession) {
		httpSession.removeAttribute("MEMBER");
		
		return "logout";
	}

}
