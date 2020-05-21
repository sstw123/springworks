package com.biz.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.shop.domain.ProductVO;
import com.biz.shop.service.ProductService;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	@Qualifier("productServiceImpl")
	@Autowired
	private ProductService productSvc;
	
	/*
	 * Spring Form 태그와 연동하는 경우 VO 객체를 ModelAttribute로 선언하여 jsp로 보내줄 수 있다
	 * 보내는 방법
	 * A. VO 매개변수에 @ModelAttribute 붙여주기
	 * B. 새로운 메소드에 VO 객체 세팅 후 리턴받는 메소드 위에 @ModelAttribute 붙여주기
	 * 둘 중 하나의 방법으로 JSP에 바로 보내줄 수있다
	 */
	@ModelAttribute("productVO")
	public ProductVO newProductVO() {
		return new ProductVO();
	}
	
	@RequestMapping(value = {"", "/list"}, method = RequestMethod.GET)
	public String list(Model model) {
		List<ProductVO> proList = productSvc.selectAll();
		model.addAttribute("proList", proList);
		return "product/product_list";
	}
	
	public String detail(long id) {
		return "product/product_detail";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(ProductVO productVO, Model model) {
		return "product/product_write";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ProductVO productVO) {
		
		productSvc.save(productVO);
		return "redirect:/product/list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/code_check", method = RequestMethod.POST)
	public boolean code_check(String p_code) {
		boolean ret = true;
		ProductVO proList = productSvc.findByPCode(p_code);
		if(proList == null) {
			ret = false;
		}
		return ret;
	}
	
	public String delete(long id) {
		return "redirect:/product/list";
	}
	
}
