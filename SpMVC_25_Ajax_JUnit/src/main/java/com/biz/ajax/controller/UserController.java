package com.biz.ajax.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.ajax.domain.UserVO;

@Controller
public class UserController {
	
	/*
		@ModelAttribute는 form에서 전송되는 데이터를 VO에 한번에 받기 위해 사용하는 Annotation
		과거 Spring 3버전대에서는 설정해주어야 VO에 받을 수 있었지만 지금은 설정해주지 않아도 자동으로 VO에 값을 받을 수 있게 되었다
		
		@ModelAttirubte를 사용하는 또 하나의 이유는 form에서 받은 데이터를 다시 view로 rendering하여 재전송할때
		model.addAttribute("객체이름", 객체)를 쓰지 않아도 Model에 자동으로 담겨서 rendering된다
		
		@ModelAttribute("객체이름")을 설정하면
	 */
	@RequestMapping(value="saveuser", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute("USER_VO") UserVO userVO) {
		
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value="senduserid", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Map<String, Object> saveUserId(UserVO userVO) {
		
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("RES_CODE", "RECEIVED");
		msg.put("userVO", userVO);
		
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value="senduser", method=RequestMethod.POST)
	public UserVO senduser(UserVO userVO) {
		
		return userVO;
	}
	
	@RequestMapping(value="html", method=RequestMethod.GET)
	public String senduserhtml(UserVO userVO) {
		
		return "user_info";
	}

}
