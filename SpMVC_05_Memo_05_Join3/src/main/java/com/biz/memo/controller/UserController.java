package com.biz.memo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	@RequestMapping(value="/idcheck", method=RequestMethod.GET)
	public String userIdCheck(String u_id, Model model) {
		
		// true : DB에 이미 등록된 아이디
		// false : DB에 등록되지 않은 아이디
		boolean idTF = uService.userIdCheck(u_id);
		model.addAttribute("idTF", idTF);
		model.addAttribute("u_id", u_id);
		
		return "user/user-idcheck";
	}
	
	@Autowired
	BCryptPasswordEncoder pwEncoder; 
	/*
	 * @ResponseBody
	 * 통상적으로 메소드가 return하는 문자열을 ResolverView에게 보내서 *.jsp 파일과 렌더링하는 일을 하는데
	 * ResponseBody 어노테이션을 붙이면 그렇지 않고 그냥 웹브라우저에 return값을 보여준다
	 */
	@ResponseBody
	@RequestMapping(value="/pw", method=RequestMethod.GET)
	public String pwTest(@RequestParam(value="strText", required=false, defaultValue="KOREA") String strText, Model model) {
		// @RequestParam(value="", required=true/false, defaultValue="")
		// 1. value="" : 쿼리의 변수명 (매개변수 이름을 뭐라고 하건 value에서 정한 변수명의 값을 받는다)
		// 2. required=true/false : strText에 값이 없더라도 400오류 발생시키지 않음, DTO나 VO 등에는 false로 설정하지 말 것(DTO나 VO가 값을 못받음)
		// 3. defaultValue="" : 사용자가 strText에 값을 전달하지 않을 경우 기본값
		String cryptedText = pwEncoder.encode(strText);
		
		return cryptedText;
	}

}
