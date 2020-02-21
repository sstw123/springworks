package com.biz.shop.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.shop.domain.ProductVO;
import com.biz.shop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="/user/product")
@Controller
public class ProductUserController {
	
	protected final ProductService productSvc;
	
	@RequestMapping(value= {"/",""}, method=RequestMethod.GET)
	public String main() {
		return "redirect:/user/product/list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		
		List<ProductVO> proList = productSvc.selectAll();
		model.addAttribute("PRODUCT_LIST", proList);
		model.addAttribute("BODY", "LIST");
		
		return "users/user_main";
	}
	
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public String detail(@RequestParam("id") long id, Model model) {
		
		ProductVO productVO = productSvc.findById(id);
		model.addAttribute("PRODUCT_VO", productVO);
		model.addAttribute("BODY", "DETAIL");
		
		return "users/user_main";
	}
	
	@ResponseBody
	@RequestMapping(value="/basket", method=RequestMethod.POST)
	public String basket(long id, HttpSession httpSession) {
		
		ProductVO basketVO = productSvc.findById(id);
		
//		List<ProductVO> basketList = new ArrayList<ProductVO>();
//		if(basketVO != null) {
//			basketList.add(basketVO);
//			httpSession.setAttribute("BASKET_LIST", basketList);
//		}
		
		return "SUCCESS";
	}
	
	protected void makeModel() {
		
	}

}
