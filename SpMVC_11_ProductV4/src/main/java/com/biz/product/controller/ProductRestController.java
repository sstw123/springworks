package com.biz.product.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biz.product.domain.ProductDTO;
import com.biz.product.domain.ProductFileDTO;
import com.biz.product.service.FileService;
import com.biz.product.service.ProductService;

/*
 * 지금부터 이 Controller는 RESTful 서비스를 response 한다는 선언
 * 이 컨트롤러에서 모든 method는 절대 view를 리턴할 수 없다
 * 실질적으로 Model, ModelAndView 클래스를 사용하지 않아도 된다
 */

@RequestMapping(value="/rest")
@RestController
public class ProductRestController {
	
	@Autowired
	ProductService pService;
	
	@Autowired
	FileService fService;
	
	@RequestMapping(value="subImgDelete", method=RequestMethod.GET, produces="text/html; charset=UTF-8")
	public String selectByFileSeq(String file_seq) {
		
		String p_code = pService.subImgDelete(file_seq);
		
		return p_code;
	}
	
	@RequestMapping(value="getProduct", method=RequestMethod.GET, produces="application/json;charset=UTF-8")
	public ProductDTO getProduct(String p_code) {
		
		ProductDTO proDTO = pService.selectByPCode(p_code);
		
		return proDTO;
	}
	
	@RequestMapping(value="getString", method=RequestMethod.GET, produces="text/json; charset=UTF-8")
	// @ResponseBody를 사용할 때는 produces를 설정하는 것이 좋다
	// 특히 한글 데이터를 response 할 때는 반드시 charset=UTF-8로 설정해주자
	public String getString(@RequestParam(value="str", // query 문자열
										required=false, // 혹시 값을 보내지 않더라도 오류가 나지 않도록 함
										defaultValue="없음") // 값을 보내지 않으면 없음 이라는 문자열로 세팅
															// required=false와 defaultValue가 없으면 서버는 client에게 400오류를 보내고 처리를 거부한다
															// 또한 VO나 DTO 등 객체에는 적용하면 안된다
											String myStr) {
		
		if(myStr.equals("없음")) {
			return "http://localhost:8080/product/getString?str=문자열 형식으로 보내세요";
		} else {
			return "보낸 문자열 : " + myStr;
		}
	}
	
	@RequestMapping(value="getUUID", method=RequestMethod.GET, produces="text/json; charset=UTF-8")
	public String getUUID() {
		String strUUID = UUID.randomUUID().toString();
		
		return strUUID + ":" + strUUID.length() ;
	}
	
}
