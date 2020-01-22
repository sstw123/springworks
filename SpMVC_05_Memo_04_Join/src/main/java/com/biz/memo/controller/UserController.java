package com.biz.memo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.memo.domain.HobbyListDTO;
import com.biz.memo.domain.UserDTO;
import com.biz.memo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SessionAttributes("userDTO")
@RequestMapping(value="/user")
@Controller
public class UserController {
	
	// UserService 인터페이스로 uService 객체를 선언하고
	// UserServiceImp 클래스로 초기화,생성을 수행
	@Autowired
	UserService uService;
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(Model model) {
		
		// UserDTO userDTO = new UserDTO();
		UserDTO userDTO = UserDTO.builder().build();
		
		// 취미 리스트 전부 가져오기
		List<HobbyListDTO> hList = uService.getHobbyList();
		
		// String[] arrHList = { hList.get(0).getH_code(), hList.get(3).getH_code() };
		// userDTO.setU_hobby( arrHList );
		
		model.addAttribute("HB_LIST", hList);		
		model.addAttribute("userDTO", userDTO);
		
		return "user/user-insert";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@ModelAttribute UserDTO userDTO, Model model) {
		int ret = uService.userJoin(userDTO);
		return "redirect:/memo/list";
	}

}
