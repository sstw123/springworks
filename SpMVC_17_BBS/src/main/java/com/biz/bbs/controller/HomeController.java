package com.biz.bbs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.bbs.domain.MenuVO;
import com.biz.bbs.mapper.MenuDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class HomeController {
	
	private final MenuDao menuDao;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		
		List<MenuVO> mList = menuDao.getAllMenu();
		
		model.addAttribute("MENUS", mList);
		log.debug("11111111111111 : " + mList.toString());
		
		return "redirect:/bbs/list";
	}
	
}
