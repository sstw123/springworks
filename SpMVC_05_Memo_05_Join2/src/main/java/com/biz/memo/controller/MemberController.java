package com.biz.memo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.biz.memo.domain.MemoDTO;
import com.biz.memo.domain.UserDTO;
import com.biz.memo.service.UserService;

@RequestMapping(value="/member")
@Controller
public class MemberController {
	
	@Autowired
	UserService uService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@RequestParam(value="LOGIN_MSG", required=false, defaultValue="0") String msg, Model model) {
		
		model.addAttribute("LOGIN_MSG", msg);
		model.addAttribute("BODY", "LOGIN");
		
		return "member/member-login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(String BODY, @ModelAttribute("userDTO") UserDTO userDTO, Model model, HttpSession httpSession) {
		
		// 로그인 입력한 사용자 ID와 PW를
		// DB의 정보와 일치하는지 검사하기
		
		if( uService.userLoginCheck(userDTO) ) {
			httpSession.setMaxInactiveInterval(10*60);
			
			// db로부터 사용자 정보를 가져오기
			userDTO = uService.getUser(userDTO.getU_id());
			httpSession.setAttribute("userDTO", userDTO);
		} else {
			httpSession.removeAttribute("userDTO");
			model.addAttribute("LOGIN_MSG", "FAIL");
			return "redirect:/member/login";
		}
		
		/*
			if(
					userDTO.getU_id().equalsIgnoreCase( "a" )
					&&
					userDTO.getU_pw().equals(getUserDTO.getU_pw() )
			) {
				
				httpSession.setMaxInactiveInterval(600); // 600초 동안 유지된다
				httpSession.setAttribute("userDTO", getUserDTO);
			} else {
				httpSession.removeAttribute("userDTO");
			}
		*/
		model.addAttribute("BODY", BODY);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpSession httpSession) {
		
		// httpSession.setAttribute("userDTO", null);
		// userDTO를 새로 생성했다 지웠다 하면 부담이 많기 때문에 세션은 남겨두고 값만 null로 바꿔서 재사용하기
		// 하지만 tomcat이 가비지 콜렉션을 실행한다고 한다
		httpSession.removeAttribute("userDTO");
		
		return "redirect:/";
	}

}