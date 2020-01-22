package com.biz.memo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.memo.domain.UserDTO;
import com.biz.memo.service.UserService;

@RequestMapping(value="/user")
@Controller
public class UserController {
	
	@Autowired
	UserService uService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(Model model) {
		
		/*
		 * user-insert.jsp에서 spring form 태그를 사용하면서 modelAttribute를 설정해두었는데
		 * 해당 Attribute를 전달해주지 않으면 form을 열 때 오류가 발생한다
		 * 
		 * 때문에 비어있는(초기화만 된) userDTO를 model에 실어서 보낸다(자동으로 렌더링 됨)
		 */
		UserDTO userDTO = new UserDTO();
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("BODY","JOIN");
		
		return "user/user-insert";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute("userDTO") @Valid UserDTO userDTO, BindingResult bResult, Model model) {
		
		if(bResult.hasErrors()) {
			return "user/user-insert";
		} else {
			int ret = uService.userJoin(userDTO);
			return "redirect:/memo/list";
		}
	}
	
	@RequestMapping(value="/mypage", method=RequestMethod.GET)
	public String mypage(Model model, HttpSession httpSession) {
		
		UserDTO userDTO = (UserDTO)httpSession.getAttribute("userDTO");
		
		model.addAttribute("userDTO", userDTO);
		model.addAttribute("TITLE", "회원정보 수정");
		
		return "user/user-insert";
	}

}
