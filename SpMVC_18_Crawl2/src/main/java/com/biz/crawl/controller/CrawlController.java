package com.biz.crawl.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.PaginationDTO;
import com.biz.crawl.service.CrawlService;
import com.biz.crawl.service.PaginationService;
import com.biz.rbooks.domain.BookInfoDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("crawl")
@Controller
public class CrawlController {
	
	private final CrawlService crawlSvc;
	private final PaginationService pageSvc;
	
	private CrawlDTO makeCrawlDTO() {
		return CrawlDTO.builder()
			.srchStartDate("11-01")
			.srchLastDate("11-31")
			.build();
	}
	
	@RequestMapping(value="hs/freeboard", method=RequestMethod.GET)
	public String hsFreeBoard(Model model,
							String srchStartDate,
							String srchLastDate,
							@RequestParam(value="currPage", required = false, defaultValue = "1") int currPageNo) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = this.makeCrawlDTO();
		crawlDTO.setC_site("하스인벤");
		crawlDTO.setC_board("자유게시판");
		crawlDTO.setSrchStartDate(srchStartDate);
		crawlDTO.setSrchLastDate(srchLastDate);
		
		//crawlDTO = crawlSvc.selectByOptions(crawlDTO);
		long totalCount = crawlSvc.countByOptions(crawlDTO);
		PaginationDTO paginationDTO = pageSvc.makePageInfo(totalCount, currPageNo);
		model.addAttribute("PAGE_DTO", paginationDTO);
		
		crawlDTO = crawlSvc.selectByPage(paginationDTO);
		
		model.addAttribute("BODY", "FREEBOARD");
		model.addAttribute("CRAWL_DTO", crawlDTO);//총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="hs/freeboard/save", method=RequestMethod.POST)
	public String hsFreeBoardSave() {
		crawlSvc.insertHSInvenFreeBoard();
		
		return "redirect:/crawl/hs/freeboard";
	}
	
	@RequestMapping(value="lol/freeboard", method=RequestMethod.GET)
	public String lolFreeBoard(Model model, String srchStartDate, String srchLastDate) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = this.makeCrawlDTO();
		crawlDTO.setC_site("롤인벤");
		crawlDTO.setC_board("자유게시판");
		crawlDTO.setSrchStartDate(srchStartDate);
		crawlDTO.setSrchLastDate(srchLastDate);
		
		crawlDTO = crawlSvc.selectByOptions(crawlDTO);
		
		model.addAttribute("BODY", "FREEBOARD");
		model.addAttribute("CRAWL_DTO", crawlDTO);//총 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/freeboard/save", method=RequestMethod.POST)
	public String lolFreeBoardSave() {
		crawlSvc.insertLOLInvenFreeBoard();
		
		return "redirect:/crawl/lol/freeboard";
	}
	
	@RequestMapping(value="lol/tip", method=RequestMethod.GET)
	public String lolTip(Model model, String srchStartDate, String srchLastDate) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = this.makeCrawlDTO();
		crawlDTO.setC_site("롤인벤");
		crawlDTO.setC_board("팁과노하우");
		crawlDTO.setSrchStartDate(srchStartDate);
		crawlDTO.setSrchLastDate(srchLastDate);
		
		crawlDTO = crawlSvc.selectByOptions(crawlDTO);
		
		model.addAttribute("BODY", "TIP");
		model.addAttribute("CRAWL_DTO", crawlDTO);//총 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/tip/save", method=RequestMethod.POST)
	public String lolTipSave() {
		crawlSvc.insertLOLInvenTip();
		
		return "redirect:/crawl/lol/tip";
	}
	
	@RequestMapping(value="lol/userinfo", method=RequestMethod.GET)
	public String lolUserInfo(Model model, String srchStartDate, String srchLastDate) {
		
		// DB에서 검색할 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = this.makeCrawlDTO();
		crawlDTO.setC_site("롤인벤");
		crawlDTO.setC_board("실시간 유저 정보");
		crawlDTO.setSrchStartDate(srchStartDate);
		crawlDTO.setSrchLastDate(srchLastDate);
		
		crawlDTO = crawlSvc.selectByOptions(crawlDTO);
		
		model.addAttribute("BODY", "USERINFO");
		model.addAttribute("CRAWL_DTO", crawlDTO);//총 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/userinfo/save", method=RequestMethod.POST)
	public String lolUserInfoSave() {
		crawlSvc.insertLOLInvenUserInfo();
		
		return "redirect:/crawl/lol/userinfo";
	}
	
	

}
