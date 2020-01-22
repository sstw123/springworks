package com.biz.memo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.memo.domain.UserDTO;

@RequestMapping(value="/member")
@Controller
public class MemberController {
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join(String str, Model model) {
		
		return "member/member-join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join() {
		
		return "redirect:/";
	}
	
	@RequestMapping(value="login", method=RequestMethod.GET)
	public String login(@RequestParam(value="LOGIN_MSG", required=false, defaultValue="0") String msg, Model model) {
		model.addAttribute("LOGIN_MSG", msg);
		return "member/member-login";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public String login(UserDTO userDTO, Model model, HttpSession httpSession) {
		
		// DB에서 데이터를 가져온 사용자 정보, 현재는 없으니 임시로 DB정보 만들기
		UserDTO getUserDTO = UserDTO.builder()
				.u_id("s")
				.u_pw("1")
				.u_name("닉네임")
				.u_tel("010-1111-1111")
				.build();
		
		// 로그인 할 때 입력한 사용자 ID와 PW를
		// DB의 정보와 일치하는지 검사하기
		if(
				userDTO.getU_id().equalsIgnoreCase( getUserDTO.getU_id() )
				&&
				userDTO.getU_pw().equals(getUserDTO.getU_pw() )
		) {
			
			httpSession.setMaxInactiveInterval(600); // 600초 동안 유지된다
			httpSession.setAttribute("userDTO", getUserDTO);
		} else {
			httpSession.removeAttribute("userDTO");
		}
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