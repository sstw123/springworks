package com.biz.student.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.student.domain.StudentDTO;
import com.biz.student.service.AnnService;
import com.biz.student.service.HomeService;
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
	 */
	
	// @Autowired
	@Inject // java EJB 방식
	StudentService sService;
	
	@Autowired // spring 고유방식
	HomeService hSv;
	
	@Autowired
	AnnService aService;
	
	public StudentController() {
		// TODO Auto-generated constructor stub
		sService = new StudentService();
	}
	
	// @RequestMapping으로 설정된 메소드의 return값이 null이면
	// 기본적으로 value에 설정된 문자열과 같은 값이 리턴된다
	@RequestMapping (value="input", method=RequestMethod.GET)
	public String input() {
		return "student/input";
	}
	
	/*
	 * input form에 데이터를 입력한 후 전송하면 데이터를 수신할 method
	 * 매개변수로 설정된 StudentDTO stDTO는 form의 input box에 설정된 name과 같은 변수명을 필드로 갖는 DTO
	 * 별다른 setter 조치 없이 Spring은 자동으로 form의 input box에 설정된 name값과 일치하는 정보를 검사하여
	 * 자동으로 DTO에 전달받은 값을 stDTO에 담아준다
	 * 
	 * form에서 전달받은 데이터는 기본적으로 String 형이다
	 * 이 때, st_grade는 stDTO에서 int 형으로 필드변수에 선언이 되어 있는데
	 * 문자열로 전달받은 데이터를 int형으로 Integer.valueOf() method를 이용해서 숫자형으로 변환을 시도할 것이다(spring framework에서)
	 * 
	 * 만약 st_grade input box에 값을 입력하지 않고 전송을 누르면 해당 변수에 담긴 값은 ""이거나 null일 것이다
	 * Integer.valueOf("") 또는 Integer.valueOf(null)을 실행할텐데 NumberFormatException이 발생한다
	 * 그러면 톰캣은 Web Browser에 400오류를 보내서 값이 잘못 전달되었다는 것을 알려준다
	 */
	@RequestMapping(value="input",method=RequestMethod.POST)
	public String search(StudentDTO stDTO, Model model) {
		System.out.println(stDTO.toString());
		
		model.addAttribute("stDTO",stDTO);
//		model 객체는 stDTO 변수에 stDTO 객체를 담아서 student/view로 보낸다
		
		return "student/view";
	}
	
	@RequestMapping(value="search",method=RequestMethod.GET)
	public String search() {
		sService.viewStudent();
		hSv.viewHome();
		aService.viewAnn();
		return "student/search";
	}
	
	@RequestMapping(value="view",method=RequestMethod.GET)
	public String view() {
		return "student/view";
	}
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String view(Model model) {
		List<StudentDTO> stdList = sService.stdList();
		model.addAttribute("stdList", stdList);
		
		
		return "student/viewList";
	}

}
