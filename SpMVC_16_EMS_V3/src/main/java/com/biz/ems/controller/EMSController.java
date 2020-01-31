package com.biz.ems.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.ems.domain.EmailVO;
import com.biz.ems.domain.NaverMemberResponse;
import com.biz.ems.service.SaveMailService;
import com.biz.ems.service.SendMailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value="/ems")
//@SessionAttributes("emailVO")
public class EMSController {
	
	private final SendMailService sendMailSvc;
	private final SaveMailService mailSvc;
	
	/*
	 * SessionAttributes에서 사용하는 ModelAttribute 생성자 method
	 * controller에서 ModelAttribute 객체가 없거나 null이면 이 method를 실행하여 emailVO를 만든다
	 * 한번 생성되어 SessionAttributes에 등록되면 다시 실행되지 않는다
	 */
	@ModelAttribute("emailVO")
	public EmailVO makeEmailVO() {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		
		String curDate = sd.format(date);
		String curTime = st.format(date);
		
		return EmailVO.builder()
				.send_date(curDate)
				.send_time(curTime).build();
	}
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public String list(Model model) {
		List<EmailVO> mailList = mailSvc.selectAll();
		
		model.addAttribute("LIST", mailList);
		
		return "home";
	}

	@RequestMapping(value="input", method=RequestMethod.GET)
	public String input(@ModelAttribute("emailVO") EmailVO emailVO, Model model, SessionStatus status, HttpSession httpSession) {
		
		NaverMemberResponse nMemberResponse = (NaverMemberResponse) httpSession.getAttribute("MEMBER");
		
		if(nMemberResponse == null) {
			model.addAttribute("LOGIN", "NO");
			return "home";
		}
		
		emailVO = this.makeEmailVO();
		
		if(nMemberResponse != null) {
			emailVO.setFrom_email(nMemberResponse.getEmail());
			emailVO.setFrom_name(nMemberResponse.getName());
		}
		
		//status.setComplete();
		
		model.addAttribute("emailVO", emailVO);
		model.addAttribute("BODY", "WRITE");
		
		return "home";
	}
	
	@RequestMapping(value="input", method=RequestMethod.POST)
	public String input(@ModelAttribute("emailVO") EmailVO emailVO) {
		
		//sendMailSvc.sendMail(emailVO);//메일보내기
		mailSvc.insert(emailVO);//보낸 메일 목록 input하기
		
		return "redirect:/";
	}
	
	@RequestMapping(value="view/{emsSeq}", method=RequestMethod.GET)
	public String view(@PathVariable("emsSeq")long emsSeq, Model model) {
		EmailVO emailVO = mailSvc.findBySeq(emsSeq);
		
		model.addAttribute("BODY", "VIEW");
		model.addAttribute("emailVO", emailVO);
		
		return "home";
	}
	
	@RequestMapping(value="update/{emsSeq}", method=RequestMethod.GET)
	public String update(@PathVariable("emsSeq")long emsSeq, Model model) {
		EmailVO emailVO = mailSvc.findBySeq(emsSeq);
		
		model.addAttribute("BODY", "WRITE");
		model.addAttribute("emailVO", emailVO);
		
		return "home";
	}
	
	@RequestMapping(value="update/{emsSeq}", method=RequestMethod.POST)
	public String update(@ModelAttribute("emailVO") EmailVO emailVO, @PathVariable("emsSeq")long emsSeq, Model model) {
		sendMailSvc.sendMail(emailVO);
		mailSvc.update(emailVO);
		
		return "redirect:/";
	}
	
	@RequestMapping(value="delete/{emsSeq}", method=RequestMethod.GET)
	public String delete(@PathVariable("emsSeq")long emsSeq) {
		mailSvc.delete(emsSeq);
		return "redirect:/";
	}
	
	
}
