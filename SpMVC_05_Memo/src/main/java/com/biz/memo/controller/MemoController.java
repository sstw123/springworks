package com.biz.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.memo.domain.MemoDTO;
import com.biz.memo.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value="/memo")
@SessionAttributes("MEMO_DTO")
@Controller
public class MemoController {
	
	@ModelAttribute("MEMO_DTO") // @ModelAttribute만 써도 상관없다
	public MemoDTO makeMemoDTO() {
		MemoDTO memoDTO = new MemoDTO();
		return memoDTO;
	}
	
	@Autowired
	MemoService mService;
	
//	@GetMapping
//	@PostMapping	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(String search, Model model) {
		
		List<MemoDTO> memoList;
		if(search == null || search.isEmpty()) {
			memoList = mService.findAll();
		} else {
			memoList = mService.findBySubject(search);
		}
		
		model.addAttribute("MEMO_LIST", memoList);
		
		return "home";
	}
	
	/*
	 * SessionAttributes에서 설정한 변수(객체)에는
	 * @ModelAttribute를 설정해두어야 한다
	 * 5.x 이하에서는 필수, 5.x 이상에서는 선택
	 */
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insert(@ModelAttribute MemoDTO memoDTO, Model model) {

		
		model.addAttribute("MEMO_DTO",memoDTO);
		return "memo-insert";
	}
	
	/*
	 * insert POST가 memoDTO를 수신할 때 입력 form에서 사용자가 입력한 값들이 있으면 그 값들을 memoDTO의 필드 변수에 setting하고
	 * 만약 없으면 메모리 어딘가에 보관중인 SessionAttributes로 설정된 memoDTO 변수에서 값을 가져와서 비어있는 필드 변수를 채워서 매개변수에 주입한다
	 * 따라서 form에서 보이지 않아도 되는 값들은 별도의 코딩을 하지 않아도 자동으로 insert POST로 전송되는 효과를 낸다
	 * 단, 이 기능을 효율적으로 사용하려면 jsp 코드에서 form이 아닌 Spring-form tag로 input을 코딩해야 한다 
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute MemoDTO memoDTO, Model model, String search) {
		
		log.debug("시퀀스 : " + memoDTO.getM_seq());
		log.debug("날짜 : " + memoDTO.getM_date());
		log.debug("TEXT : " + memoDTO.getM_text());
		
		int ret = mService.insert(memoDTO);
		return "redirect:/memo/list";
	}

}