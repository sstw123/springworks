package com.biz.crawl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.PaginationDTO;
import com.biz.crawl.service.CrawlService;
import com.biz.crawl.service.PaginationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes(value = "CRAWL_DTO")
@RequiredArgsConstructor
@RequestMapping("crawl")
@Controller
public class CrawlController {
	
	private final CrawlService crawlSvc;
	private final PaginationService pageSvc;
	
	@ModelAttribute("CRAWL_DTO")
	protected CrawlDTO makeCrawlDTO() {
		return new CrawlDTO();
	}
	
	@RequestMapping(value="/site/board", method=RequestMethod.GET)
	public String siteBoard(Model model, @ModelAttribute("CRAWL_DTO") CrawlDTO crawlDTO) {
		
		this.pagination(model, crawlDTO, crawlDTO.getCurrPage());
		
		model.addAttribute("BODY", crawlDTO.getC_site()+crawlDTO.getC_board());
		model.addAttribute("CRAWL_DTO", crawlDTO);// 총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="/site/board/save", method=RequestMethod.POST)
	public String saveSiteBoard(@ModelAttribute("CRAWL_DTO") CrawlDTO crawlDTO) {
		
		crawlSvc.saveSiteBoard(crawlDTO);
		// c_site, c_board, crawlSiteURL 필요
		
		return "redirect:/crawl/site/board?c_site=" + crawlDTO.getC_site() + "?c_board=" + crawlDTO.getC_board();
	}
	
	@RequestMapping(value="/site/board/delete", method=RequestMethod.POST)
	public String deleteSiteBoard(@ModelAttribute("CRAWL_DTO") CrawlDTO crawlDTO) {
		
		crawlSvc.deleteBySiteBoard(crawlDTO);
		// c_site, c_board 필요
		
		return "redirect:/crawl/site/board?c_site=" + crawlDTO.getC_site() + "?c_board=" + crawlDTO.getC_board();
	}
	
	private void pagination (Model model, CrawlDTO crawlDTO, int currPageNo) {
		long totalCount = crawlSvc.countByOptions(crawlDTO);//총 게시물 수 가져오기
		// 총 게시물 수가 1개 이상 존재하면
		if(totalCount > 0) {
			PaginationDTO paginationDTO = pageSvc.makePageInfo(totalCount, currPageNo);//페이지마다 보여줄 정보를 담은 DTO
			model.addAttribute("PAGE_DTO", paginationDTO);
			crawlDTO.setPaginationDTO(paginationDTO);
			
			crawlDTO = crawlSvc.selectByOptionsByPage(crawlDTO);//1페이지에 보여줄 DTO 세팅
		}
	}

}
