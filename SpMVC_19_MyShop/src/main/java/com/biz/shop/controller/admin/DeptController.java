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
	
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String product(@RequestParam(value="search", required = false, defaultValue = "0") String search,
						@RequestParam(value = "text", required = false, defaultValue = "") String text,
						Model model) {
		
		DeptVO deptVO = new DeptVO();
		
		List<DeptVO> deptList = deptSvc.selectAll();
		model.addAttribute("DEPT_LIST", deptList);
		
		model.addAttribute("search",search);
		model.addAttribute("text",text);
		
		model.addAttribute("deptVO", deptVO);
		model.addAttribute("BODY", "DEPT");
		return "admin/main";
	}
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String input(@Valid @ModelAttribute("deptVO") DeptVO deptVO, BindingResult result, Model model, SessionStatus ssStatus) {
		
		if(result.hasErrors()) {
			log.debug("========== validation 오류 ==========");
			List<DeptVO> deptList = deptSvc.selectAll();
			model.addAttribute("DEPT_LIST", deptList);
			model.addAttribute("BODY", "DEPT");
			return "admin/main";
		}
		
		deptSvc.save(deptVO);
		ssStatus.setComplete();
		
		return "redirect:/admin/dept";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@ModelAttribute(value = "deptVO") DeptVO deptVO, @PathVariable("id") long id, Model model) {
		
		List<DeptVO> deptList = deptSvc.selectAll();
		model.addAttribute("DEPT_LIST", deptList);
		
		deptVO = deptSvc.findById(id);// 세션에 productVO 저장
		
		model.addAttribute("deptVO", deptVO);
		model.addAttribute("BODY", "DEPT");
		return "admin/main";
	}

}
