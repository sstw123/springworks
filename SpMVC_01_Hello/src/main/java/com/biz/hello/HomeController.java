package com.biz.hello;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	/*
	 * @RequestMapping(value = "URI", method = RequestMethod.GET/POST)
	 * RequestMapping 어노테이션은 URI와 GET/POST 방식을 설정한다(안붙어있으면 둘 다 사용 가능)
	 * 스프링 내의 서블릿이 my를 보고 해당 메소드(여기선 my3)를 호출해준다
	 * 그리고 mypage라는 문자열을 return 해주고 같은 파일명을 views 폴더에서 찾아준다
	 * Model - VIEW - CONTROLLER 패턴
	 */
	@RequestMapping(value = "my", method = RequestMethod.POST)
	public String my3(Model model, String strName, String strDept) {
		System.out.println(strName);
		System.out.println(strDept);
		return "mypage";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String home(Model model) {
		return "home";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		model.addAttribute("st_name","이몽룡");
		model.addAttribute("st_dept","컴퓨터공학과");
		model.addAttribute("st_grade",3);
		
		return "home";
	}
	
}
