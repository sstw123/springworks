package com.biz.shop.controller;

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

import com.biz.shop.model.UserDetailsVO;
import com.biz.shop.service.UserService;

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
	
	// mypage 비밀번호 변경 시 먼저 비밀번호 검사 화면 보여주기
	@RequestMapping(value="/pwcheck", method=RequestMethod.GET)
	public String pw_check() {
		return "user/pw_check";
	}
	
	// 비밀번호 검사
	@RequestMapping(value="/pwcheck", method=RequestMethod.POST)
	public String pw_check(String password) {
		boolean ret = userSvc.pw_check(password);
		
		String page = "";
		if(ret) {
			page = "user/pw_change";
		} else {
			page = "user/pw_check_false";
		}
		return page;
	}
	
	// 새로운 비밀번호로 변경
	@ResponseBody
	@RequestMapping(value="/pwchange", method=RequestMethod.POST)
	public boolean pw_change(@RequestParam("password")String password, @RequestParam("re_password")String re_password) {
		userSvc.pw_change(password, re_password);
		return true;
	}
	
	@RequestMapping(value = "/mypage", method=RequestMethod.GET)
	public String mypage(Principal principal, Model model) {
		UsernamePasswordAuthenticationToken upaToken = (UsernamePasswordAuthenticationToken) principal;
		UserDetailsVO loginVO = (UserDetailsVO) upaToken.getPrincipal();
		loginVO.setAuthorities(upaToken.getAuthorities());
		
		model.addAttribute("loginVO", loginVO);
		
		return "user/mypage";
	}
	
	/**
	 * mypage에서 정보 수정 후 저장 시 form에 입력된 데이터가 userVO에 담겨서 온다
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
	
	// 아이디 찾기 화면
	@RequestMapping(value="/find-id", method=RequestMethod.GET)
	public String findId() {
		return "user/find_id_pw";
	}
	
	// 아이디 찾기
	// ajax로 요청할 메소드
	@ResponseBody
	@RequestMapping(value="/find-id", method=RequestMethod.POST)
	public List<String> findId(UserDetailsVO userVO) {
		List<UserDetailsVO> userList = userSvc.findId(userVO);
		List<String> usernameList = null;
		if(userList == null || userList.size() < 1) {
			// 이메일로 검색한 결과가 없으면 null 반환
		} else {
			// 이메일로 검색한 결과가 있으면 usernameList에 아이디 저장
			usernameList = new ArrayList<>();
			
			for(UserDetailsVO vo : userList) {
				String username = vo.getUsername();
				String first = "";
				String after = "";
				
				// 앞의 2글자만 보이고 남은 글자 * 만들기
				// 아이디가 2글자 이하인 경우 다 보여주기
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
		}
		return usernameList;
	}
	
	// 비밀번호 찾기
	// ajax로 요청할 메소드
	@ResponseBody
	@RequestMapping(value="/find-pw", method=RequestMethod.POST)
	public byte findPW(UserDetailsVO userVO) {
		// userVO에는 username, password만 담겨있다
		byte ret = userSvc.findPW(userVO);
		return ret;
	}
	
	@RequestMapping(value="/new-pw", method=RequestMethod.GET)
	public String new_pw(@RequestParam("link") String enc_username, Model model) {
		model.addAttribute("ENC",enc_username);
		return "user/new_pw";
	}
	
	@RequestMapping(value="/new-pw", method=RequestMethod.POST)
	public String new_pw(UserDetailsVO userVO, String re_password) {
		// userVO에는 암호화 된 username, password만 담겨있다
		int ret = userSvc.new_pw(userVO, re_password);
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
