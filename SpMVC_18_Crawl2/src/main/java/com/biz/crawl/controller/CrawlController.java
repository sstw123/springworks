package com.biz.crawl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.service.CrawlService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("crawl")
@Controller
public class CrawlController {
	
	private final CrawlService crawlSvc;
	
	@RequestMapping(value="free", method=RequestMethod.GET)
	public String crawl(Model model) {
		
		CrawlDTO crawlDTO = crawlSvc.getFreeBoard();
		model.addAttribute("CRAWL_DTO", crawlDTO);
		
		return "home";
	}
	
	

}
