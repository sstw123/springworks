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
		
		ProductVO productVO = new ProductVO();//다른 페이지에 들어가서 저장된 productVO 세션의 초기화를 위한 코드
		model.addAttribute("productVO", productVO);//다른 페이지에 들어가서 저장된 productVO 세션의 초기화를 위한 코드
		
		this.modelMapping(model);
		
		model.addAttribute("search",search);
		model.addAttribute("text",text);
		
		return "admin/main";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.POST)
	public String product_detail(@Valid @ModelAttribute("productVO") ProductVO productVO, BindingResult result, Model model) {
		// Validation 사용법
		// 1. form에서 받을 변수에 @Valid 어노테이션 붙이기
		// 2. 만약 유효성 검사를 통과하지 못하면 BindingResult로 에러를 전달하고 hasErrors() 메소드로 에러 검사 가능
		if(result.hasErrors()) {
			log.debug("========== validation 오류 ==========");
			this.modelMapping(model);
			return "admin/main";
		}
		
		this.modelMapping(model);
		model.addAttribute("PRO_BODY", "DETAIL");
		return "admin/main";
		
	}
	
	@RequestMapping(value = "/input", method = RequestMethod.POST)
	public String input(@ModelAttribute("productVO") ProductVO productVO, Model model, SessionStatus ssStatus) {
		
		productSvc.save(productVO);
		ssStatus.setComplete();
		
		return "redirect:/admin/product";
	}
	
	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public String update(@ModelAttribute(value = "productVO") ProductVO productVO, @PathVariable("id") long id, Model model) {
		
		this.modelMapping(model);
		
		productVO = productSvc.findById(id);// 세션에 productVO 저장
		model.addAttribute("productVO", productVO);// 세션에 productVO 저장
		return "admin/main";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") long id, Model model) {
		
		productSvc.delete(id);
		
		return "redirect:/admin/product";
	}
	
	

	protected void modelMapping(Model model) {
		List<ProductVO> proList = productSvc.selectAll();
		model.addAttribute("PRO_LIST", proList);
		model.addAttribute("BODY", "PRODUCT");
	}
	
}
