package com.biz.ajax.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.ajax.domain.AddrDTO;

@RequestMapping(value = "/ajax")
@Controller
public class AjaxController {
	
	@ResponseBody
	@RequestMapping(value= {"","/"}, method=RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public String ajax_method() {
		return "english한글123";
	}
	
	/*
	.do : 전자정부 프레임워크의 web 서비스에서 사용하던 URL 패턴
	
	일반적으로 서버에 접속하면 index.* 파일을 보여주고
	메뉴를 클릭하거나 어떤 기능을 선택할 때는 접미사로 .do를 붙여야 컨트롤러로 가도록 하는 패턴
	*.do 접미사가 붙어있는 URL은 컨트롤러를 통해서 요청을 처리하고 그렇지 않은 요청은 모두 무시한다
	
	이 패턴을 사용하여 web 서비스를 만들려면 프로젝트의 web.xml의 appServlet url-pattern을 설정하고
	대신 index.html, index.jsp 파일을 webapp 폴더에 작성해두어야 한다
	그래야 프로젝트가 톰캣에 의해 run 되었을 때 404오류가 나타나지 않는다
	*/
	@ResponseBody
	@RequestMapping(value="/msg", method=RequestMethod.POST)
	public String ajax_msg(String msg) {
		if(msg.equalsIgnoreCase("KOREA")) {
			return "KOREA";
		} else {
			return "NOT KOREA";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/addr" ,method=RequestMethod.POST)
	public AddrDTO addr() {
		AddrDTO dto = AddrDTO.builder()
				.a_name("홍길동")
				.a_addr("서울")
				.a_tel("010")
				.a_age(20)
				.build();
		
		return dto;
	}
	
	@RequestMapping(value="/addr_view" ,method=RequestMethod.GET)
	public String addr_view(Model model) {
		AddrDTO dto = AddrDTO.builder()
				.a_name("홍길동")
				.a_addr("서울")
				.a_tel("010")
				.a_age(20)
				.build();
		
		model.addAttribute("dto", dto);
		
		return "addr";
	}
	
	@ResponseBody
	@RequestMapping(value="put", method=RequestMethod.PUT, produces="text/plain;charset=UTF-8")
	public String ajax_put(String msg) {
		
		return msg;
	}
	
}