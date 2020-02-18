package com.biz.shop.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.shop.domain.DeptVO;
import com.biz.shop.service.DeptService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes(value = "deptVO")
@RequiredArgsConstructor
@RequestMapping("/admin/dept")
@Controller
public class DeptController {
	
	private final DeptService deptSvc;
	
	@ModelAttribute(value = "deptVO")
	public DeptVO newDeptVO() {
		return new DeptVO();
	}
	
	protected void modelMapping(Model model) {
		List<DeptVO> deptList = deptSvc.selectAll();
		model.addAttribute("DEPT_LIST", deptList);
		model.addAttribute("BODY", "DEPT");
	}
	
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String product(@RequestParam(value="search", required = false, defaultValue = "0") String search,
						@RequestParam(value = "text", required = false, defaultValue = "") String text,
						Model model) {
		
		DeptVO deptVO = new DeptVO();//다른 페이지에 들어가서 저장된 deptVO 세션의 초기화를 위한 코드
		model.addAttribute("deptVO", deptVO);//다른 페이지에 들어가서 저장된 deptVO 세션의 초기화를 위한 코드
		
		this.modelMapping(model);
		
		model.addAttribute("search",search);
		model.addAttribute("text",text);
		
		return "admin/main";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		this.modelMapping(model);
		return "admin/dept_list";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public String dept_detail(@Valid @ModelAttribute("deptVO") DeptVO deptVO, BindingResult result, Model model) {
		// Validation 사용법
		// 1. form에서 받을 변수에 @Valid 어노테이션 붙이기
		// 2. 만약 유효성 검사를 통과하지 못하면 BindingResult로 에러를 전달하고 hasErrors() 메소드로 에러 검사 가능
		if(result.hasErrors()) {
			log.debug("========== validation 오류 ==========");
			this.modelMapping(model);
			return "admin/main";
		}
		
		this.modelMapping(model);
		model.addAttribute("DEPT_BODY", "DETAIL");
		return "admin/main";
		
	}
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String input(@ModelAttribute("deptVO") DeptVO deptVO, Model model, SessionStatus ssStatus) {
		
		deptSvc.save(deptVO);
		ssStatus.setComplete();
		
		return "redirect:/admin/dept";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@ModelAttribute(value = "deptVO") DeptVO deptVO, @PathVariable("id") long id, Model model) {
		
		this.modelMapping(model);
		
		deptVO = deptSvc.findById(id);// 세션에 deptVO 저장
		model.addAttribute("deptVO", deptVO);// 세션에 deptVO 저장
		
		return "admin/main";
	}

}
