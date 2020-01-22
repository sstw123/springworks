package com.biz.rbooks.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biz.rbooks.domain.BookVO;
import com.biz.rbooks.service.BookService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(value="book")
@RestController
public class BookController {
	
	private final BookService bookSvc;
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public List<BookVO> list() {
		return bookSvc.selectAll();
	}

}
