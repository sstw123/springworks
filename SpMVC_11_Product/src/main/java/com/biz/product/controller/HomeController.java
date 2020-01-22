package com.biz.product.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class HomeController {
	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		return "home";
	}
	
	@ResponseBody
	@RequestMapping(value="/nation", method=RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getNation(String str) {
		
		log.debug("Request 변수 str : " + str);
		
		long sum = 0;
		for(int i = 0; i < 1000000L; i++) {
			sum += i;
		}
		
		String strNation = "Republic of Korea";
		strNation = str + sum;
		return strNation;
	}
	
}
