package com.biz.crawl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.PaginationDTO;
import com.biz.crawl.service.CrawlService;
import com.biz.crawl.service.PaginationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("crawl")
@Controller
public class CrawlController {
	
	private final CrawlService crawlSvc;
	private final PaginationService pageSvc;
	
	@RequestMapping(value="lol/userinfo", method=RequestMethod.GET)
	public String lolUserInfo(Model model, String srchStartDate, String srchLastDate,
							@RequestParam(value="currPage", required = false, defaultValue = "1") int currPageNo) {
		
		// DB에서 검색할 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
					.c_site("롤인벤")
					.c_board("실시간 유저 정보")
					.srchStartDate(srchStartDate)
					.srchLastDate(srchLastDate)
					.build();
		
		this.pagination(model, crawlDTO, currPageNo);
		
		model.addAttribute("BODY", "USERINFO");
		model.addAttribute("CRAWL_DTO", crawlDTO);// 총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/userinfo/save", method=RequestMethod.POST)
	public String lolUserInfoSave() {
		crawlSvc.insertLOLInvenUserInfo();
		
		return "redirect:/crawl/lol/userinfo";
	}
	
	@RequestMapping(value="lol/userinfo/delete", method=RequestMethod.POST)
	public String lolUserInfoDelete() {
		
		// DB에서 삭제할 사이트,게시판명 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("롤인벤")
				.c_board("실시간 유저 정보")
				.build();
		
		crawlSvc.deleteBySiteBoard(crawlDTO);
		
		return "redirect:/crawl/lol/userinfo";
	}
	
	@RequestMapping(value="lol/tip", method=RequestMethod.GET)
	public String lolTip(Model model, String srchStartDate, String srchLastDate,
						@RequestParam(value="currPage", required = false, defaultValue = "1") int currPageNo) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("롤인벤")
				.c_board("팁과노하우")
				.srchStartDate(srchStartDate)
				.srchLastDate(srchLastDate)
				.build();
		
		this.pagination(model, crawlDTO, currPageNo);
		
		model.addAttribute("BODY", "TIP");
		model.addAttribute("CRAWL_DTO", crawlDTO);// 총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/tip/save", method=RequestMethod.POST)
	public String lolTipSave() {
		crawlSvc.insertLOLInvenTip();
		
		return "redirect:/crawl/lol/tip";
	}
	
	@RequestMapping(value="lol/tip/delete", method=RequestMethod.POST)
	public String lolTipDelete() {
		
		// DB에서 삭제할 사이트,게시판명 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("롤인벤")
				.c_board("팁과노하우")
				.build();
		
		crawlSvc.deleteBySiteBoard(crawlDTO);
		
		return "redirect:/crawl/lol/tip";
	}
	
	@RequestMapping(value="lol/freeboard", method=RequestMethod.GET)
	public String lolFreeBoard(Model model, String srchStartDate, String srchLastDate,
							@RequestParam(value="currPage", required = false, defaultValue = "1") int currPageNo) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("롤인벤")
				.c_board("자유게시판")
				.srchStartDate(srchStartDate)
				.srchLastDate(srchLastDate)
				.build();
		
		this.pagination(model, crawlDTO, currPageNo);
		
		model.addAttribute("BODY", "FREEBOARD");
		model.addAttribute("CRAWL_DTO", crawlDTO);// 총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="lol/freeboard/save", method=RequestMethod.POST)
	public String lolFreeBoardSave() {
		crawlSvc.insertLOLInvenFreeBoard();
		
		return "redirect:/crawl/lol/freeboard";
	}
	
	@RequestMapping(value="lol/freeboard/delete", method=RequestMethod.POST)
	public String lolFreeBoardDelete() {
		
		// DB에서 삭제할 사이트,게시판명 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("롤인벤")
				.c_board("자유게시판")
				.build();
		
		crawlSvc.deleteBySiteBoard(crawlDTO);
		
		return "redirect:/crawl/lol/freeboard";
	}
	
	@RequestMapping(value="hs/freeboard", method=RequestMethod.GET)
	public String hsFreeBoard(Model model,
							String srchStartDate,
							String srchLastDate,
							@RequestParam(value="currPage", required = false, defaultValue = "1") int currPageNo) {
		
		// DB 검색 쿼리값 c_site와 c_board 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("하스인벤")
				.c_board("자유게시판")
				.srchStartDate(srchStartDate)
				.srchLastDate(srchLastDate)
				.build();
		
		this.pagination(model, crawlDTO, currPageNo);
		
		model.addAttribute("BODY", "FREEBOARD");
		model.addAttribute("CRAWL_DTO", crawlDTO);// 총 조회수, 평균 조회수, 게시물 수, List<CrawlSubDTO> 들어있음
		
		return "home";
	}
	
	@RequestMapping(value="hs/freeboard/save", method=RequestMethod.POST)
	public String hsFreeBoardSave() {
		crawlSvc.insertHSInvenFreeBoard();
		
		return "redirect:/crawl/hs/freeboard";
	}
	
	@RequestMapping(value="hs/freeboard/delete", method=RequestMethod.POST)
	public String hsFreeBoardDelete() {
		
		// DB에서 삭제할 사이트,게시판명 설정
		CrawlDTO crawlDTO = CrawlDTO.builder()
				.c_site("하스인벤")
				.c_board("자유게시판")
				.build();
		
		crawlSvc.deleteBySiteBoard(crawlDTO);
		
		return "redirect:/crawl/hs/freeboard";
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
