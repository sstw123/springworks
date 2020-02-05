package com.biz.bbs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.service.BBsService;
import com.biz.bbs.service.CommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("bbsVO")
@RequestMapping(value="bbs")
@Controller
public class BBsController {
	
	private final BBsService bbsSvc;
	private final CommentService cmtSvc;
	
	@Autowired
	public BBsController(@Qualifier("bbsSvcV1") BBsService bbsSvc, CommentService cmtSvc) {
		this.bbsSvc = bbsSvc;
		this.cmtSvc = cmtSvc;
	}
	
	@ModelAttribute("bbsVO")
	public BBsVO makeBBsVO() {
		return new BBsVO();
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		
		List<BBsVO> bbsList = bbsSvc.selectAll();
		model.addAttribute("BBS_LIST",bbsList);
		model.addAttribute("BODY", "BBS_LIST");
		
		return "home";
	}
	
	/*
	 * view 메소드에서 @ModelAttribute를 사용한 이유
	 * 게시판 상세페이지에서 답글을 달 때 본문만 작성하는 textarea 박스를 두고
	 * 나머지 항목들은 별도로 저장하거나 저장하지 않아도 답글을 저장했을 때
	 * 원글의 내용이 같이 controller로 전송되도록 하기 위한 설정
	 * 
	 * @ModelAttribute로 설정된 bbsVO는 SessionAttributes()에 등록되어있기 때문에
	 * 다른 메소드에서 그 값을 참조할 수 있다
	 */
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(@ModelAttribute("bbsVO") BBsVO bbsVO, Model model) {
		
		bbsVO = bbsSvc.findById(bbsVO.getBbs_id());
//		List<CommentVO> cmtList = cmtSvc.selectAll(bbsVO.getBbs_id());
		
		model.addAttribute("bbsVO",bbsVO);
//		model.addAttribute("CMT_LIST", cmtList);
		model.addAttribute("BODY", "BBS_VIEW");
		
		return "home";
	}
	
	@RequestMapping(value="input", method=RequestMethod.GET)
	public String save(Model model) {
		
		LocalDate ld = LocalDate.now();
		LocalTime lt = LocalTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		BBsVO bbsVO = BBsVO.builder()
						.bbs_date(ld.toString())
						.bbs_time(lt.format(dtf))
						.build();
		
		model.addAttribute("bbsVO", bbsVO);// bbs_input.jsp의 form:form에 날짜,시간 세팅하기
		model.addAttribute("BODY", "BBS_INPUT");
		
		return "home";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String save(@ModelAttribute BBsVO bbsVO) {
		bbsSvc.save(bbsVO);
		
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="delete", method=RequestMethod.GET)
	public String delete(long bbs_id) {
		bbsSvc.delete(bbs_id);
		
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="reply",method=RequestMethod.POST)
	public String reply(@ModelAttribute("bbsVO") BBsVO bbsVO) {
		
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter ld = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter lt = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		bbsVO.setBbs_date(ldt.format(ld));
		bbsVO.setBbs_time(ldt.format(lt));
		
		bbsVO = bbsSvc.reply(bbsVO);
		
		return "redirect:/bbs/list";
	}
	
	@RequestMapping(value="cmt_write", method=RequestMethod.POST)
	public String comment(CommentVO cmtVO, Model model) {
		int ret = cmtSvc.insert(cmtVO);
		List<CommentVO> cmtList = cmtSvc.selectAll(cmtVO.getCmt_p_id());
		
		model.addAttribute("CMT_LIST", cmtList);
		
		return "include/bbs_comment";
	}
	
	@RequestMapping(value="cmt_list", method=RequestMethod.POST)
	public String cmt_list(long cmt_p_id, Model model) {
		
		List<CommentVO> cmtList = cmtSvc.selectAll(cmt_p_id);
		
		model.addAttribute("CMT_LIST", cmtList);
		
		return "include/bbs_comment";
	}

}
