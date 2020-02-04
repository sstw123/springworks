package com.biz.bbs.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.service.BBsService;

@SessionAttributes("bbsVO")
@RequestMapping(value="bbs")
@Controller
public class BBsController {
	
	private final BBsService bbsSvc;
	
	@Autowired
	public BBsController(@Qualifier("bbsSvcV1") BBsService bbsSvc) {
		this.bbsSvc = bbsSvc;
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
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(@ModelAttribute("bbsVO") BBsVO bbsVO, Model model) {
		
		bbsVO = bbsSvc.findById(bbsVO.getBbs_id());
		
		model.addAttribute("BBS_VO",bbsVO);
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
		
		model.addAttribute("bbsVO", bbsVO);
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
		
		bbsVO = bbsSvc.reply(bbsVO);
		
		return "redirect:/bbs/list";
		
	}

}
