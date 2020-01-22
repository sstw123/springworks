package com.biz.iolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/product")
@Controller
public class ProductController {
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model) {
		
		return null;
	}
	

}
