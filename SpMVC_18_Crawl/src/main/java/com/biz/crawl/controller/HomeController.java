package com.biz.crawl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.crawl.domain.NaverMovieVO;
import com.biz.crawl.service.NaverMovieCrawlService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final NaverMovieCrawlService nmcSvc;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<NaverMovieVO> naverMovieList = nmcSvc.selectAll();
		model.addAttribute("NAVER_LIST", naverMovieList);
		
		return "home";
	}
	
}
