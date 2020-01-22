package com.biz.naver.controller;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.naver.domain.PageDTO;
import com.biz.naver.service.NaverServiceV2;

@Controller
public class NaverController {
	
	@Autowired
	NaverServiceV2 nServiceV2;
	
	@RequestMapping(value="/search", method=RequestMethod.GET, produces="text/json; charset=UTF-8")
	public String news(String cate, String search, @RequestParam(value="currentPageNo", required=false, defaultValue="1") long currentPageNo, Model model) {
		
		JSONArray resArray = null;
		try {
			PageDTO pageDTO = nServiceV2.getPageInfo(cate, search, currentPageNo);
			model.addAttribute("PAGE", pageDTO);
			
			resArray = nServiceV2.getNaver(cate, search, currentPageNo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		model.addAttribute("NAVER_ITEMS", resArray);
		model.addAttribute("cate", cate);
		model.addAttribute("search", search);
		model.addAttribute("currentPageNo", currentPageNo);
		
		
		return "home";
	}

}
