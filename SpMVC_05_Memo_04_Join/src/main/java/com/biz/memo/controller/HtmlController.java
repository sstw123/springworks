package com.biz.memo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/html")
@Controller
public class HtmlController {
	
	@RequestMapping(value="/hello.html", method=RequestMethod.GET)
	public String hello() {
		return "home";
	}

}
