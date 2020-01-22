package com.biz.rbooks.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.rbooks.domain.BookVO;
import com.biz.rbooks.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value="/book")
public class BookController {
	
	private final BookService bookSvc;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<BookVO> bookList = bookSvc.selectAll();
		
		model.addAttribute("BLIST", bookList);
		
		return "books/list";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String search(@RequestParam(value="strText", required=false, defaultValue="") String strText, Model model) {
		List<BookVO> bookList = bookSvc.selectByBnameList(strText);
		
		model.addAttribute("BLIST", bookList);
		
		return "books/list_body";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insert(Model model) {
		
		return null;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(BookVO bookVO) {
		bookSvc.insert(bookVO);
		
		return "redirect:/book/list";
	}

}
