package com.biz.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.biz.shop.domain.ProductSizeVO;
import com.biz.shop.domain.ProductVO;
import com.biz.shop.service.ProductOptionsService;
import com.biz.shop.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping(value = "/product")
public class ProductController {
	
	@Qualifier("productServiceImpl")
	@Autowired
	private ProductService productSvc;
	
	@Autowired
	private ProductOptionsService proOptSvc;
	
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
	
	@RequestMapping(value = "/detail/{p_code}")
	public String detail(@PathVariable("p_code") String p_code, Model model) {
		ProductVO productVO = productSvc.findByPCode(p_code);
		model.addAttribute("PRODUCT_VO", productVO);
		
		// option 정보 테이블에서 데이터 리스트 가져와서 사용하기
		model.addAttribute("m_color_list", proOptSvc.getColorList());
		model.addAttribute("m_size_list", proOptSvc.getSizeList());
		return "product/product_detail";
	}
	
	@ResponseBody
	@RequestMapping(value = "/insert_size", method = RequestMethod.POST)
	public Object insert_size(ProductSizeVO proSizeVO) {
		log.debug("SIZE : {}", proSizeVO.getS_size());
		log.debug("P_CODE : {}", proSizeVO.getP_code());
		
		int ret = proOptSvc.countProductSize(proSizeVO);
		if(ret > 0) {
			return "false";
		} else {
			int insertResult = proOptSvc.insert_size(proSizeVO);
			if(insertResult <= 0) return "false";
		}
		
		return proSizeVO;
	}
	
	@ResponseBody
	@RequestMapping(value = "/delete_size", method = RequestMethod.POST)
	public String delete_size(ProductSizeVO proSizeVO) {
		int ret = proOptSvc.delete_size(proSizeVO);
		return "true";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET)
	public String save(ProductVO productVO, Model model) {
		return "product/product_write";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(ProductVO productVO
			, MultipartFile file
			) {
		productSvc.save(productVO, file);
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
