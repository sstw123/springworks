package com.biz.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "nation")
@Controller
public class NationController {
	
	@ResponseBody
	@RequestMapping("/where")
	public String where() {
		String nation = "한국\n"
				+ "= Korea\n"
				+ "...";
		
		
		return nation;
	}
	
	@ResponseBody
	@RequestMapping("/korea")
	public String korea() {
		String nation = "korea";
		
		return nation;
	}
	
	@ResponseBody
	@RequestMapping("us")
	public String us() {
		String nation = "United States";
		
		return nation;
	}

}
