package com.biz.ajax.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.ajax.domain.UserVO;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		// userVO 데이터를 생성하고(service로부터 가져오고)
		// model에 userVO를 담고
		// return "home" (home.jsp 호출)
		// userVO와 home.jsp를 렌더링하여
		// web client로 전송하기(*.html 코드로 변환되어서 전송)
		
		UserVO userVO = new UserVO();
		
		model.addAttribute("USER_VO", userVO.makeSampleVO());
		
		return "home";
	}
	
}
