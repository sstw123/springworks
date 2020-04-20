package com.biz.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes(value = "userVO")
@RequiredArgsConstructor
@RequestMapping(value="/join")
@Controller
public class JoinController {
	
	private final UserService userSvc;
	
	@ModelAttribute(value = "userVO")
	public UserDetailsVO makeUserDetailsVO() {
		return new UserDetailsVO();
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String join(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model) {
		
		return "join/join";
	}
	
	@RequestMapping(value="/email", method=RequestMethod.POST)
	public String email(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model, SessionStatus session) {
		//userVO.setEmail("");
		return "join/join_email";
	}
	
	@ResponseBody
	@RequestMapping(value="/emailauth", method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String email_auth(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model, SessionStatus session) {
		int ret = userSvc.insert(userVO);
		//session.setComplete();
		
		return "join/join_email";
	}
	
	@RequestMapping(value="/emailvalid", method=RequestMethod.GET)
	public String email_valid(@ModelAttribute("userVO") UserDetailsVO userVO, SessionStatus session) {
		boolean ret = userSvc.email_valid(userVO.getUsername(), userVO.getEmail());
		session.setComplete();
		if (ret) {
			return "redirect:/user/login";
		} else {
			
			return "join/join_email_fail";
		}
	}
	
}
