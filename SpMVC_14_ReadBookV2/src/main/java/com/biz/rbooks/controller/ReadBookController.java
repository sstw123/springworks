package com.biz.rbooks.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.rbooks.domain.BookVO;
import com.biz.rbooks.domain.ReadBookVO;
import com.biz.rbooks.service.BookService;
import com.biz.rbooks.service.ReadBookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="/rbook")
@Controller
public class ReadBookController {
	
	private final ReadBookService rBookSvc;
	private final BookService bookSvc;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(Model model) {
		List<ReadBookVO> rBookList = rBookSvc.selectAll();
		
		model.addAttribute("RBOOKS", rBookList);
		
		return "rbooks/list";
	}
	
	@RequestMapping(value="view/{rb_seq}", method=RequestMethod.GET)
	public String view(@PathVariable("rb_seq") long rb_seq, Model model) {
		
		ReadBookVO rBookVO = rBookSvc.selectBySeq(rb_seq);
		model.addAttribute("RBOOK", rBookVO);
		
		String b_code = rBookVO.getRb_bcode();
		BookVO bookVO = bookSvc.selectByBcode(b_code);
		model.addAttribute("BOOK", bookVO);
		
		List<ReadBookVO> rBookList = rBookSvc.selectByBcode(b_code);
		model.addAttribute("RBOOKS", rBookList);
		
		return "rbooks/view";
	}
	
	// 입력화면을 보여주기 위한 메소드
	@RequestMapping(value="insert", method=RequestMethod.GET)
	public String insert(Model model) {
		
		ReadBookVO rBookVO = new ReadBookVO();
		
		// java 1.8 이상에서 사용하는 날짜 클래스
		LocalDate localDate = LocalDate.now();
		String curDate = localDate.toString();
		
		// 시간을 시:분:초만 보이도록 설정
		// HH : 24시간제 시간
		DateTimeFormatter dt = DateTimeFormatter.ofPattern("HH:mm:ss");
		LocalTime localTime = LocalTime.now();
		String curTime = localTime.format(dt);
		
		rBookVO.setRb_date(curDate);
		rBookVO.setRb_stime(curTime);
		
		model.addAttribute("rBookVO", rBookVO);
		
		return "rbooks/input";
	}
	
	@RequestMapping(value="insert", method=RequestMethod.POST)
	public String insert(ReadBookVO rBookVO) {
		
		int ret = rBookSvc.insert(rBookVO);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="update/{rb_seq}", method=RequestMethod.GET)
	public String update(@PathVariable("rb_seq") long rb_seq, Model model) {
		
		ReadBookVO rBookVO = rBookSvc.selectBySeq(rb_seq);
		model.addAttribute("rBookVO", rBookVO);
		
		return "rbooks/input";
	}
	
	@RequestMapping(value="update/{rb_seq}", method=RequestMethod.POST)
	public String update(@PathVariable("rb_seq") long rb_seq, @ModelAttribute ReadBookVO rBookVO) {
	// 일반적인 GET 쿼리방식은 ? 뒤의 쿼리가 무시되기 때문에 따로 변수값이 필요 없지만
	// @PathVariable 방식에서는 form으로 submit하면 "update/" 뒤의 주소까지 포함되어 현재 열린 창으로 submit 하는 것이기 때문에
	// @PathVariable을 붙여주어야 제대로 작동한다
		int ret = rBookSvc.update(rBookVO);
		
		return "redirect:/rbook/list";
	}
	
	@RequestMapping(value="delete/{rb_seq}", method=RequestMethod.GET)
	public String delete(@PathVariable("rb_seq") long rb_seq) {
		
		int ret = rBookSvc.delete(rb_seq);
		
		return "redirect:/";
	}

}
