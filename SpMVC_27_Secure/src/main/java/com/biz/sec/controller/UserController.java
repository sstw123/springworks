package com.biz.sec.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/user")
@Controller
public class UserController {
	
	private final UserService userSvc;
	
	@RequestMapping(value = "/login", method=RequestMethod.GET)
	public String login() {
		return "auth/login";
	}
	
	@RequestMapping(value = "/join", method=RequestMethod.GET)
	public String join() {
		return "auth/join";
	}
	
	@ResponseBody
	@RequestMapping(value = "/join", method=RequestMethod.POST, produces="text/html; charset=UTF-8")
	public String join(String username, String password) {
		
		log.debug("아이디 {}, 비밀번호 {}", username, password);
		
		userSvc.insert(username, password);
		
		//return "redirect:/";
		return String.format("<b>아이디</b> : %s, <b>비밀번호</b> : %s", username, password);
	}
	
	@ResponseBody
	@RequestMapping(value="/idcheck", method=RequestMethod.GET)
	public boolean idcheck(String username) {
		UserDetailsVO userDetailsVO = userSvc.findByUserName(username);
		
		boolean ret = false;
		
		// 이미 DB에 회원정보가 저장되어있는 경우 체크하기
		// null이 아니면 && 뒤쪽의 if문 진행
		// 먼저 null 체크를 해서 NullPointerException 방지
		if(userDetailsVO != null && userDetailsVO.getUsername().equalsIgnoreCase(username)) {
			ret = true;
		}
		return ret;
	}
	
	@RequestMapping(value="/pwcheck", method=RequestMethod.GET)
	public String pw_check() {
		
		return "user/pw_check";
	}
	
	@RequestMapping(value="/pwcheck", method=RequestMethod.POST)
	public String pw_check(String password) {
		boolean ret =  userSvc.pw_check(password);
		
		String page = "";
		if(ret) {
			page = "user/pw_change";
		} else {
			page = "user/pw_check_false";
		}
		return page;
	}
	
	@RequestMapping(value="/pwchange", method=RequestMethod.POST)
	public String pw_change(String password, String re_password) {
		if(password.isEmpty() || re_password.isEmpty() || password != re_password) {
			return "user/pw_change_false";
		}
		
		userSvc.pw_change(password);
		
		return "redirect:/";
	}
	
	@ResponseBody
	@RequestMapping(value = "", method=RequestMethod.GET)
	public String user() {
		return "user home";
	}
	
	@RequestMapping(value = "/mypage", method=RequestMethod.GET)
	public String mypage(Principal principal, Model model) {
		UsernamePasswordAuthenticationToken upaToken = (UsernamePasswordAuthenticationToken) principal;
		UserDetailsVO loginVO = (UserDetailsVO) upaToken.getPrincipal();
		loginVO.setAuthorities(upaToken.getAuthorities());
		
		model.addAttribute("BODY", "MYPAGE");
		model.addAttribute("loginVO", loginVO);
		
		return "user/mypage";
	}
	
	/**
	 * mypage에서 저장을 눌렀을 때 form에 입력된 데이터가 userVO에 담겨서 온다
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value = "/mypage", method=RequestMethod.POST)
	public String mypage(UserDetailsVO userVO) {
		
		/*
		 * 아래 코드는 Security Session 정보가 저장된 메모리(Principal)에 직접 접근하여 수정하는 방법이다
		 * 코드는 쉬워지지만 보안에 굉장히 위험할 수 있으므로 principal을 수정하는 방법은 사용하지 않는다
		// 현재 로그인 된 사용자 정보 VO로 가져오기
		UsernamePasswordAuthenticationToken upaToken = (UsernamePasswordAuthenticationToken) principal;
		UserDetailsVO loginVO = (UserDetailsVO) upaToken.getPrincipal();
		
		// form에서 입력받은 값 저장 후 update 메소드로 VO 보내기
		loginVO.setEmail(userVO.getEmail());
		loginVO.setPhone(userVO.getPhone());
		loginVO.setAddress(userVO.getAddress());
		*/
		
		int ret= userSvc.updateInfo(userVO);
		
		return "redirect:/user/mypage";
	}
	
	@RequestMapping(value="/find-id", method=RequestMethod.GET)
	public String findId() {
		return "user/find_id_pw";
	}
	
	@ResponseBody
	@RequestMapping(value="/find-id", method=RequestMethod.POST)
	public List<String> findId(UserDetailsVO userVO) {
		List<UserDetailsVO> userList = userSvc.findId(userVO);
		List<String> usernameList = new ArrayList<>();
		for(UserDetailsVO vo : userList) {
			// 앞의 2글자만 보이고 남은 글자 * 만들기
			String username = vo.getUsername();
			String first = "";
			String after = "";
			
			if(username.length() <= 1) {
				first = username;
			} else if(username.length() > 1){
				first = username.substring(0,2);
				for(int i = 0 ; i < vo.getUsername().substring(2).length() ; i++) {
					after += "*";
				} 
			}
			
			usernameList.add(first + after);
		}
		
		return usernameList;
	}
	
	@RequestMapping(value="/find-pw", method=RequestMethod.GET)
	public String findPW(@RequestParam("link") String enc_username, Model model) {
		model.addAttribute("ENC",enc_username);
		return "user/find_pw";
	}
	
	@RequestMapping(value="/find-pw", method=RequestMethod.POST)
	public String findPW(UserDetailsVO userVO) {
		// userVO에는 username, password만 담겨있다
		int ret = userSvc.setPW(userVO);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/mypage1", method=RequestMethod.GET)
	public String mypage1(Model model) {
		
		// 로그인한 사용자 정보
		UserDetailsVO userVO = (UserDetailsVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// 권한(ROLE) 리스트 추출하여 userVO에 setting
		userVO.setAuthorities(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
		
		model.addAttribute("userVO", userVO);
		return "user/mypage";
	}
	
	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout() {
		return "redirect:/";
	}

}
