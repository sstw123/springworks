package com.biz.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.service.CommentService;

import lombok.extern.slf4j.Slf4j;

// class에 부착하는 RequestMapping
// type 수준(또는 top level)의 RequestMapping (호출할 때 prefix로 붙는 RequestMapping)
// 메소드에 /list라고 RequestMapping을 붙이면 context/comment/list 로 path가 지정된다
@Slf4j
@RequestMapping(value="/comment")
@Controller
public class CommentController {
	
	@Qualifier("cmtSvcV1")
	protected final CommentService cmtSvc;
	
	public CommentController(CommentService cmtSvc) {
		super();
		this.cmtSvc = cmtSvc;
	}//생성자 끝
	

	// 게시판의 ID값을 매개변수로 받아서 코멘트 리스트를 보여주는 메소드
	@RequestMapping(value="list", method=RequestMethod.GET)
	public String list(@RequestParam(value = "bbs_id", required = false) Long c_b_id ,Model model) {
		
		if(c_b_id != null) {
			List<CommentVO> cmtList = cmtSvc.findByBId(c_b_id);
			model.addAttribute("COMMENT_LIST", cmtList);
		}
		
		return "comment_list";
	}
	
	// 코멘트 입력값을 매개변수로 받아서 DB에 저장하는 메소드
	@RequestMapping(value="insert", method = RequestMethod.POST)
	public String insert(@ModelAttribute CommentVO commentVO) {
		
		cmtSvc.insert(commentVO);
		
		return "redirect:/comment/list?bbs_id=" + commentVO.getC_b_id();
		/*
		 * Model에 addAttribute("bbs_id", commentVO.getC_b_id()); 로 속성을 추가해주고
		 * return "redirect:/comment/list"; 를 보내면 자동으로 뒤에 쿼리가 따라간다
		 */
	}
	
	@RequestMapping(value="insert2", method = RequestMethod.POST)
	public String insert(@ModelAttribute CommentVO commentVO, Model model) {
		
		cmtSvc.insert(commentVO);
		model.addAttribute("b_id", commentVO.getC_b_id());
		
		return "redirect:/detail";
	}
	
	@RequestMapping(value="delete", method = RequestMethod.POST)
	public String delete(@ModelAttribute CommentVO commentVO) {
		
		cmtSvc.delete(commentVO.getC_id());
		
		return "redirect:/comment/list?bbs_id=" + commentVO.getC_b_id();
	}

}
