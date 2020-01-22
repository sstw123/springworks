package com.biz.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.todo.domain.ToDoList;
import com.biz.todo.service.ToDoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ToDoController {
	
	@Autowired
	@Qualifier("todoSvcV2")
	ToDoService todoSvc;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String List(Model model) {
		/*
		 * 팀프로젝트에서 Controller 개발자와, Service 개발자가 다를 경우
		 * Service는 interface가 정의되어 있기 때문에
		 * 아직 기능은 구현되지 않았어도 Controller 개발자는
		 * 당연히 Service의 method를 호출하면 결과가 리턴될 것이라는 것을 알고
		 * 나머지 코드를 구현할 수 있게 된다
		 */
		
		List<ToDoList> toDoList = todoSvc.selectAll();
		model.addAttribute("toDoList", toDoList);
		
		return "home";
	}
	
	@RequestMapping(value="list", method=RequestMethod.POST)
	public String insert(@ModelAttribute ToDoList toDoList, Model model) {
		
		int ret = todoSvc.insert(toDoList);
		if(ret < 1) {
			model.addAttribute("INSERT_RESULT", "NOT INSERT");
		}
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="complete", method=RequestMethod.GET)
	public String complete(@RequestParam("tdSeq") String strSeq) {
		
		long start = System.currentTimeMillis();
		todoSvc.complete(strSeq);
		long end = System.currentTimeMillis();
		long time = end - start;
		
		log.debug("==========================걸린 시간=========================== : " + time);
		
		return "redirect:/list";
	}
	
	@RequestMapping(value="alarm", method=RequestMethod.GET)
	public String alarm(@RequestParam("tdSeq") String strSeq) {
		
		todoSvc.alarm(strSeq);
		
		return "redirect:/list";
	}

}