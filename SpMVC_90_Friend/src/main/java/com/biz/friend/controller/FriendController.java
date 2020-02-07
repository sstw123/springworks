package com.biz.friend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.friend.domain.FriendDTO;
import com.biz.friend.domain.SearchDTO;
import com.biz.friend.service.FriendService;

import lombok.RequiredArgsConstructor;

@SessionAttributes("frdDTO")
@RequiredArgsConstructor
@RequestMapping("frd")
@Controller
public class FriendController {
	
	protected final FriendService frdSvc;
	
	@ModelAttribute("frdDTO")
	public FriendDTO makeSession() {
		return new FriendDTO();
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public String list(Model model) {
		
		List<FriendDTO> frdList = frdSvc.selectAll();
		
		model.addAttribute("BODY", "LIST");
		model.addAttribute("FRD_LIST", frdList);
		
		return "home";
	}
	
	
	@RequestMapping(value="view", method=RequestMethod.GET)
	public String view(int frd_id, Model model) {
		
		FriendDTO frdDTO = frdSvc.selectById(frd_id);
		
		model.addAttribute("BODY", "VIEW");
		model.addAttribute("FRD_INFO", frdDTO);
		
		return "home";
	}
	
	@RequestMapping(value = "input", method = RequestMethod.GET)
	public String input(Model model) {
		
		model.addAttribute("BODY", "INPUT");
		
		return "home";
	}
	
	@RequestMapping(value = "input", method = RequestMethod.POST)
	public String input(@ModelAttribute FriendDTO frdDTO, Model model) {
		frdSvc.insert(frdDTO);
		
		return "redirect:/frd/list";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.GET)
	public String edit(@ModelAttribute("frdDTO") FriendDTO frdDTO, Model model) {
		frdDTO = frdSvc.selectById(frdDTO.getFrd_id());
		
		model.addAttribute("BODY", "EDIT");
		model.addAttribute("frdDTO", frdDTO);
		
		return "home";
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute FriendDTO frdDTO) {
		
		frdSvc.update(frdDTO);
		
		return "redirect:/frd/list";
	}
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public String delete(int frd_id) {
		
		frdSvc.delete(frd_id);
		
		return "redirect:/frd/list";
	}
	
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(SearchDTO searchDTO, Model model) {
		
		List<FriendDTO> searchResult = frdSvc.search(searchDTO);
		model.addAttribute("BODY", "LIST");
		model.addAttribute("FRD_LIST", searchResult);
		
		return "home";
	}

}
