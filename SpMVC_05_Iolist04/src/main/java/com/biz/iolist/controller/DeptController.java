package com.biz.iolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.biz.iolist.domain.DeptDTO;
import com.biz.iolist.service.DeptService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value="/dept")
@Controller
public class DeptController {
	
	@Autowired
	DeptService dService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(String text, Model model) {
		List<DeptDTO> deptList = dService.findByDCodeOrDName(text);
		model.addAttribute("DEPT_LIST", deptList);
		
		return "dept/dept-list-body";
		
	}

//	class의 /dept와 method의 /list를 묶어서, /dept/list path로 request 했을 때 list() method가 호출된다
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		
		/*
		 * Model(ui.Model)클래스와 같은 역할을 수행하는 Spring 클래스
		 * 사용법이 조금 다른 형식
		 * 여기에는 view와 객체를 동시에 담아서 Dispatcher에게 객체를 리턴해주는 형식으로 사용
		 * 
		 * Model은 객체만 담고 view는 문자열을 return 하는 방식으로 사용
		 */
		
		ModelAndView mView = new ModelAndView();
		List<DeptDTO> deptList = dService.getAllList();
		
//		model.addAttribute("DEPTLIST", deptList)와 같은 역할
		mView.addObject("DEPTLIST", deptList);
		
//		return "/dept/list"와 같은 역할
		mView.setViewName("/dept/dept-list");
		
//		return String이 아닌 ModelAndView 사용
		return mView;
	}
	
//	입력 form을 보여주고 데이터를 입력받도록 시작하는 절차
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public String input(Model model) {
		
		return "dept/dept-input";
	}
	
//	입력 form에서 데이터를 입력하고 전송(저장) 버튼을 클릭하면 데이터를 가지고 호출될 method
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String input(DeptDTO deptDTO, Model model) {
		log.debug("거래처정보 " + deptDTO.toString());
		int ret = dService.insert(deptDTO);
		/*
		 * redirect: 문자열로 시작되는 return값
		 * view(*.jsp, /dept/list.jsp)를 호출하라는 것이 아니고
		 * 		http://localhost:8080/context/dept/list 명령을 실행하라
		 * 		메뉴에서 /dept/list 주소를 클릭하라
		 * 		브라우저 주소창에 ~~/dept/list를 입력하고 Enter
		 */
		return "redirect:/dept/list";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(String d_code, Model model) {
		
		log.debug("거래처코드 : " + d_code);
		DeptDTO dDTO = dService.findByDCode(d_code);
		model.addAttribute("DEPT_DTO", dDTO);
		
		return "dept/dept-view";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(String id, Model model) {
		log.debug("삭제할 거래처코드 : " + id);
		int ret = dService.delete(id);
		
		return "redirect:/dept/list";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(String id, Model model) {
		DeptDTO dDTO = dService.findByDCode(id);
		model.addAttribute("DI", dDTO);
		return "dept/dept-input";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(DeptDTO deptDTO, Model model) {
		int ret = dService.update(deptDTO);
		return "redirect:/dept/view?d_code=" + deptDTO.getD_code();
	}
}
