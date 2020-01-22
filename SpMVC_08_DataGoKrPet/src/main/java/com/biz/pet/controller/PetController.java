package com.biz.pet.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.pet.domain.GoPetVO;
import com.biz.pet.domain.SearchVO;
import com.biz.pet.service.PetService;

@Controller
public class PetController {
	
	@Autowired
	PetService pService;
	
	@RequestMapping(value="/pet", method=RequestMethod.GET)
	public String pet(@RequestParam(value="s_cate", required=false, defaultValue = "") String s_cate, @RequestParam(value="s_text", required=false, defaultValue = "") String s_text, Model model) {
		
		List<GoPetVO> hosList = pService.getRestVoList(s_cate, s_text);
		model.addAttribute("H_LIST", hosList);
		
		SearchVO searchVO = SearchVO.builder()
				.s_cate(s_cate)
				.s_text(s_text)
				.build();
		
		model.addAttribute("searchVO", searchVO);
		
		return "home";
	}

}
