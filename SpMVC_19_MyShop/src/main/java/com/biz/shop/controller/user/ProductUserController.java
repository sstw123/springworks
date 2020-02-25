package com.biz.shop.controller.user;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.shop.domain.CartListVO;
import com.biz.shop.domain.CartVO;
import com.biz.shop.domain.ProductVO;
import com.biz.shop.service.CartService;
import com.biz.shop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/user/product")
@Controller
public class ProductUserController {
	
	protected final ProductService productSvc;
	protected final CartService cartSvc;
	
	@RequestMapping(value= {"/",""}, method=RequestMethod.GET)
	public String main() {
		return "redirect:/user/product/list";
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model, String search) {
		
		List<ProductVO> proList = null;
		
		if(search == null || search.isEmpty()) {
			proList = productSvc.selectAll();
		} else {
			proList = productSvc.selectByName(search);
		}
		
		log.debug("-------------proList : " + proList.toString());
		
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
	
	@RequestMapping(value="/cart", method=RequestMethod.GET)
	public String cart(Authentication authen, Model model) {
		
		if(authen == null) {
			return "redirect:/user/product/list";
		}
		
		List<CartVO> cartList = null;
		
		try {
			String username = authen.getPrincipal().toString();
			cartList = cartSvc.selectCart(username);
		} catch (Exception e) {
			
		}
		
		log.debug("---장바구니--- : " + cartList.toString());
		
		model.addAttribute("BODY", "CART");
		model.addAttribute("CART_LIST", cartList);
		
		return "users/user_main";
	}
	
	/*
	 * Authentication 인터페이스
	 * Spring Security에 로그인 된 사용자 정보 추출을 위한 인터페이스
	 */
	@ResponseBody
	@RequestMapping(value="/cart", method=RequestMethod.POST)
	public boolean cart(Authentication authen, CartVO cartVO, HttpSession httpSession) {
		
		try {
			// 스프링 시큐리티를 이용해 로그인 한 사용자 username 추출, toString으로 변환, 미리 받아온 cartVO에 세팅
			cartVO.setUsername(authen.getPrincipal().toString());
			
			cartSvc.save(cartVO);
			
			return true;//authen이 null이 아닌 경우
		} catch (Exception e) {
			return false;//authen == null인 경우
		}
		
		
		
//		String principal = "";
//		if(authen == null) {
//			cartVO.setUsername("guest");
//		} else {
//			principal = authen.getPrincipal() + "";
//			cartVO.setUsername(principal);
//		}
		
		
//		ProductVO cartVO = productSvc.findById(id);
		
//		List<ProductVO> cartList = new ArrayList<ProductVO>();
//		if(cartVO != null) {
//			cartList.add(cartVO);
//			httpSession.setAttribute("CART_LIST", cartList);
//		}
		
//		return "SUCCESS";
	}
	
	@ResponseBody
	@RequestMapping(value = "qty_update", method=RequestMethod.GET)
	public String qty_update(@RequestParam("seq") Long seq, @RequestParam("p_qty") Integer p_qty) {
		
		int ret = cartSvc.qty_update(seq, p_qty);
		
		return ret + "";
		
	}
	
	@RequestMapping(value = "cart_change_list_qty", method=RequestMethod.POST)
	public String cart_change_list_qty(
			CartListVO cartListVO) {
			//@RequestBody CartVO cartVO) {
		
		//log.debug(cartVO.toString());
		cartSvc.qty_list_update(cartListVO);
		
		return "redirect:/user/product/cart";
	}
	
	@ResponseBody
	@RequestMapping(value="/cart_order", method=RequestMethod.POST)
	public Integer cart_order(@RequestParam("orderList[]") String[] orderList) {
		
		Integer ret = cartSvc.cart_order(orderList);
		return ret;
	}
	
	@RequestMapping(value = "cart_delete/{seq}", method=RequestMethod.GET)
	public String cart_delete(@PathVariable("seq") Long seq) {
		cartSvc.cart_delete(seq);
		
		return "redirect:/user/product/cart";
	}
	
	@ResponseBody
	@RequestMapping(value = "cart_list_delete", method=RequestMethod.POST)
	public Integer cart_list_delete(@RequestParam("deleteArray[]") String[] deleteArray) {
		
		log.debug("SEQ LIST : " + deleteArray.toString());
		
		Integer ret = cartSvc.cart_list_delete(deleteArray);
		
		return ret;
		//return "redirect:/user/product/cart";
	}
	
	protected void makeModel() {
		
	}

}
