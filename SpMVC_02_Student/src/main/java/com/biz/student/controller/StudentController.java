package com.biz.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.student.domain.StudentDTO;
import com.biz.student.service.StudentService;

/*
 * @Controller
 * tomcat, spring container에 이 클래스의 List를 추가하고 request 요청에 대비
 * 
 * 스프링에선 index.jsp를 제외하곤 모든 jsp파일을 직접 여는게 아니라
 * Controller를 통해서 열게 된다
 * 
 * Controller의 메소드는 jsp파일을 열어주는 역할이다
 * 
 * @RequestMapping의 value에는 주소창에 입력할 값이 들어가며
 * 
 * return에는 jsp파일의 경로(path)와 파일명(.jsp 확장자 제외)이 들어가게 된다
 * 기본 path는 /WEB-INF/views/ 폴더이며 그 밑부터 지정해주면 된다 
 */
@Controller
public class StudentController {
	
	/*
	 * 톰캣 컨테이너(싱글톤 클래스 하나에 객체하나)
	 * servlet-context.xml에서 경로를 지정하여 @Service도 스캔하여
	 * 싱글톤 객체로 만들어서 컨테이너에 담아놓았다
	 * 
	 * @Autowired
	 * 제어의 역전(IOC)
	 * 의존성 주입(DI)
	 * 
	 */
	@Autowired
	StudentService ss;
	
//	public StudentController() {
//		// TODO Auto-generated constructor stub
//		ss = new StudentService();
//	}
	
	// @RequestMapping으로 설정된 메소드의 return값이 null이면
	// 기본적으로 value에 설정된 문자열과 같은 값이 리턴된다
	@RequestMapping (value="input", method=RequestMethod.GET)
	public String input() {
		return "student/input";
	}
	
	@RequestMapping(value="input",method=RequestMethod.POST)
	public String search(StudentDTO stDTO, Model model) {
		System.out.println(stDTO.toString());
		
		model.addAttribute("stDTO",stDTO);
//		model 객체는 stDTO 변수에 stDTO 객체를 담아서 student/view로 보낸다
		
		return "student/view";
	}
	
	@RequestMapping(value="search",method=RequestMethod.GET)
	public String search() {
		return "student/search";
	}
	
	@RequestMapping(value="view",method=RequestMethod.GET)
	public String view() {
		return "student/view";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String view(Model model) {
		List<StudentDTO> stdList = ss.stdList();
		model.addAttribute("stdList", stdList);
		
		
		return "student/viewList";
	}

}
