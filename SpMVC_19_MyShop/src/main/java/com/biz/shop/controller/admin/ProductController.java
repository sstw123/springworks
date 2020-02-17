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

import com.biz.shop.domain.ProductVO;
import com.biz.shop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes(value = "productVO")
@RequiredArgsConstructor
@RequestMapping("/admin/product")
@Controller
public class ProductController {
	
	private final ProductService productSvc;
	
	
	@ModelAttribute(value = "productVO")
	public ProductVO newProductVO() {
		return new ProductVO();
	}
	
	@RequestMapping(value = {"/",""}, method = RequestMethod.GET)
	public String product(@RequestParam(value="search", required = false, defaultValue = "0") String search,
						@RequestParam(value = "text", required = false, defaultValue = "") String text,
						Model model) {
		/*
		if(search.equals("0")) {
			List<ProductVO> proList = productSvc.selectAll();
		} else {
			// 검색 list
		}
		*/
		
		ProductVO productVO = new ProductVO();
		
		List<ProductVO> proList = productSvc.selectAll();
		model.addAttribute("PRO_LIST", proList);
		
		model.addAttribute("search",search);
		model.addAttribute("text",text);
		
		model.addAttribute("productVO", productVO);
		model.addAttribute("BODY", "PRODUCT");
		return "admin/main";
	}
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String input(@Valid @ModelAttribute("productVO") ProductVO productVO, BindingResult result, Model model, SessionStatus ssStatus) {
		// Validation 사용법
		// 1. form에서 받을 변수에 @Valid 어노테이션 붙이기
		// 2. 만약 유효성 검사를 통과하지 못하면 BindingResult로 에러를 전달하고 hasErrors() 메소드로 에러 검사 가능
		
		if(result.hasErrors()) {
			log.debug("========== validation 오류 ==========");
			List<ProductVO> proList = productSvc.selectAll();
			model.addAttribute("PRO_LIST", proList);
			model.addAttribute("BODY", "PRODUCT");
			return "admin/main";
		}
		
		productSvc.save(productVO);
		ssStatus.setComplete();
		
		return "redirect:/admin/product";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@ModelAttribute(value = "productVO") ProductVO productVO, @PathVariable("id") long id, Model model) {
		
		List<ProductVO> proList = productSvc.selectAll();
		model.addAttribute("PRO_LIST", proList);
		
		productVO = productSvc.findById(id);// 세션에 productVO 저장
		
		model.addAttribute("productVO", productVO);
		model.addAttribute("BODY", "PRODUCT");
		return "admin/main";
	}

}
