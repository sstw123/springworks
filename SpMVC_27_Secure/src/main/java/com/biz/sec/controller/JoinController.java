package com.biz.sec.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.service.UserService;
import com.biz.sec.utils.PbeEncryptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security 기반 회원가입 및 Email 인증 프로젝트 메인 컨트롤러
 * @since 2020-04-20
 * @author sianblone
 */
@Slf4j
@SessionAttributes(value = "userVO")
@RequiredArgsConstructor
@RequestMapping(value="/join")
@Controller
public class JoinController {
	
	private final UserService userSvc;
	
	@ModelAttribute(value = "userVO")
	public UserDetailsVO makeUserDetailsVO() {
		return new UserDetailsVO();
	}
	
	/**
	 * 회원가입 화면 보여주기
	 * sesstionAttributes 이용
	 * 
	 * localhost/sec/join : 회원가입 화면 보이기
	 * join/email : 회원가입 버튼 클릭 시
	 * join/emailauth : email 인증 화면에서 인증메일 전송 클릭 시
	 * @since 2020-04-20
	 * 
	 * localhost/sec/join : 회원가입 화면 보이기
	 * join/join_next : 회원가입 버튼 클릭 시 DB에 회원정보를 보여준 후 email 인증 화면 보이기
	 * join/join_last : email 인증 시
	 * @since 2020-04-21
	 * @param userVO
	 * @param model
	 * @return
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String join(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model) {
		
		return "join_v2/join";
	}
	
	/**
	 * 회원가입 화면에서 username, password 입력 후 회원가입 버튼 클릭 시
	 * userVO에 데이터를 받아서(username, password)
	 * sessionAttributes에 저장해두고 
	 * 이메일 인증화면 보여주기
	 * @since 2020-04-21
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="/join-s1", method=RequestMethod.POST)
	public String join_next(@ModelAttribute("userVO") UserDetailsVO userVO) {
		return "join_v2/join_email";
	}
	
	/**
	 * @since 2020-04-21
	 * email 인증 form에서 인증메일 발송 버튼 클릭 시
	 * userVO에 데이터를 받아서(email만)
	 * sessionAttributes에 저장된 userVO를 입력한 부분(email)만 업데이트 하고
	 * DB에 저장한 후
	 * 인증정보를 Email로 보내고 인증코드를 입력받는 화면 다시 보여주기
	 * 이 때 JOIN 변수에 true 문자열을 담아서 보낸다
	 * 화면에는 인증코드를 입력하는 란이 보이도록 설정
	 * 
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="/join-s2", method=RequestMethod.POST)
	public String join_last(@ModelAttribute("userVO") UserDetailsVO userVO, Model model) {
		String email_token = userSvc.insert_getToken(userVO);
		
		model.addAttribute("MY_EMAIL_SECRET", email_token);
		model.addAttribute("JOIN_S2", true);
		return "join_v2/join_email";
	}
	
	@RequestMapping(value="/join-s2-test", method=RequestMethod.GET)
	public String join() {
		
		return "join_v2/join_email";
	}
	
	/**
	 * @since 2020-04-21
	 * 이메일 인증 form에서 인증키와 인증값을 받아서 인증 처리
	 * @param secret_key
	 * @param secret_value
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/email_token_check", method=RequestMethod.POST)
	public boolean email_token_check(
			//@RequestParam("secret_id") String username,
			@ModelAttribute("userVO") UserDetailsVO userVO,
			@RequestParam("secret_key")String secret_key,
			@RequestParam("secret_value")String secret_value,
			SessionStatus status) {
		
		boolean isSuccess = userSvc.email_token_insert(userVO, secret_key, secret_value);
		if(isSuccess) {
			status.setComplete();
		}
		
		return isSuccess;
	}
	
	/**
	 * @since 2020-04-20
	 * 회원가입 시 username, password 전송 후
	 * email 보내기 화면으로 이동하기
	 * 
	 * @param userVO
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/email", method=RequestMethod.POST)
	public String email(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model, SessionStatus session) {
		//userVO.setEmail("");
		return "join/join_email";
	}
	
	/**
	 * @since 2020-04-20
	 * 
	 * @param userVO
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/emailauth", method=RequestMethod.POST)
	public String email_auth(@ModelAttribute(value = "userVO") UserDetailsVO userVO, Model model, SessionStatus session) {
		int ret = userSvc.insert(userVO);
		//session.setComplete();
		
		return "join/join_email";
	}
	
	/**
	 * @since 2020-04-20
	 * 회원가입 중 email 보내기 화면
	 * email 보내기 후 다시 재전송 화면 보이기
	 * @param userVO
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/emailvalid", method=RequestMethod.GET)
	public String email_valid(@ModelAttribute("userVO") UserDetailsVO userVO, SessionStatus session) {
		boolean ret = userSvc.email_valid(userVO.getUsername(), userVO.getEmail());
		session.setComplete();
		if (ret) {
			return "redirect:/user/login";
		} else {
			
			return "join/join_email_fail";
		}
	}
	
}
