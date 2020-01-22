package com.biz.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.student.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class StudentController {
	
	/*
	 * Container에 등록된 bean 중에서
	 * StudentService 클래스의 객체를 가져온다
	 */
	@Autowired
	StudentService ss;
	
	/*
	 * RequestMapping의 value는 URI 명
	 * return 값이 null이라면 views 폴더 밑의 JSP파일명은 URI를 그대로 따라가고
	 * retrun 값이 존재한다면
	 * 
	 * Controller method의 매개변수
	 * - Controller가 어떤 일을 수행하는데 필요한 정보를
	 * Spring, Tomcat, Dispatcher 등에게 요청하는 것 
	 */
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		/*
		 * Model model :
		 * dispatcher에게 *.jsp 파일에 데이터를 보내서 Rendering을 할텐데
		 * Rendering 할 데이터를 담을 클래스(DTO같은)
		 */
		log.debug("학생리스트를 보여주는 페이지(jsp)와 연결해주는 method");
		
		List<String> strList = ss.getList();
		/*
		 * StudentService의 getList() method를 호출해서 가져온 strList를
		 * jsp로 전달
		 */
		model.addAttribute("STD_LIST",strList);
		// "STD_LIST"라는 변수에 strList를 담는다
		
		return "student/list";
	}
	
	@RequestMapping(value="search", method=RequestMethod.GET)
	public String search() {
		log.debug("학생정보를 검색하는 코드");
		return "student/search";
	}

}
