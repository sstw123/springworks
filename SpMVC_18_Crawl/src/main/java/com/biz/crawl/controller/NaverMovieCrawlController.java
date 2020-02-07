package com.biz.crawl.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.crawl.domain.NaverMovieVO;
import com.biz.crawl.service.NaverMovieCrawlService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("naver")
@RestController
public class NaverMovieCrawlController {
	
	private final NaverMovieCrawlService nmcSvc;
	
	@RequestMapping(value="rank", method=RequestMethod.GET)
	public List<NaverMovieVO> rank() {
		
		List<NaverMovieVO> naverMovieList = nmcSvc.getMovieRank();
		
		return naverMovieList;
	}

}
