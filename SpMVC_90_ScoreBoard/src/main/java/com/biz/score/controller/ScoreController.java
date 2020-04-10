package com.biz.score.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.score.model.ScoreVO;
import com.biz.score.service.ScoreService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ScoreController {
	
	private final ScoreService scoreSvc;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(Model model) {
		List<ScoreVO> scoreList = scoreSvc.selectAll();
		
		model.addAttribute("BODY", "SCORE");
		model.addAttribute("SC_LIST", scoreList);
		
		return "home";
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public String list(@PathVariable("id") long id, Model model) {
		ScoreVO scoreVO = scoreSvc.findById(id);
		
		model.addAttribute("BODY", "SCORE_INFO");
		model.addAttribute("SC_VO", scoreVO);
		
		return "home";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.GET)
	public String insert(@RequestParam(value="id", required=false, defaultValue = "0") long id, Model model) {
		ScoreVO scoreVO = scoreSvc.findById(id); 
		
		model.addAttribute("BODY", "SAVE");
		model.addAttribute("SC_VO", scoreVO);
		return "home";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String insert(@ModelAttribute ScoreVO scoreVO) {
		
		int ret = scoreSvc.save(scoreVO);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String delete(@PathVariable("id") long id) {
		
		int ret = scoreSvc.delete(id);
		
		return "redirect:/";
	}

}
