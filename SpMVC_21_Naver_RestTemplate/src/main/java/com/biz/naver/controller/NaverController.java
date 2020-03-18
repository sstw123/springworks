package com.biz.naver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.naver.domain.NaverItemVO;
import com.biz.naver.service.NaverService;

@Controller
public class NaverController {
	@Autowired
	NaverService naverSvc;
	
	@ResponseBody
	@RequestMapping(value="/search", method=RequestMethod.GET, produces = "application/json; charset=UTF-8")
	public List<NaverItemVO> naverSearch(String cate, String search) {
		
		List<NaverItemVO> ret = null;
		
		try {
			ret = naverSvc.naverSearch(cate, search);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ret;
	}

}
