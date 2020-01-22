package com.biz.iolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value="/param")
@Controller
public class ParamController {
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view() {
		
		return "param/param-view";
	}
	
	@RequestMapping(value="/업데이트", method=RequestMethod.GET)
	public String update(String code, String id) {
		/*
		 * 만약 매개변수를 int 형으로 받을 경우, 받은 문자열을 무조건 Integer.valueOf() 등으로 숫자형으로 변환하려 하기 때문에
		 * 아무 것도 전달하지 않아서 null 값을 보내게 될 경우 오류가 나게 된다
		 * 따라서 무조건 String으로 받은 뒤 현재 메소드 내에서 try로 묶어 Integer.valueOf() 등으로 변환해주도록 하자
		 */
		System.out.println(id);
		return "param/param-update";
	}
	
	@RequestMapping(value="/업데이트", method=RequestMethod.POST)
	public String update(String code, Model model) {
		System.out.println(code);
		return "param/param-update";
	}

}
