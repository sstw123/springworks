package com.biz.memo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.memo.domain.MemoDTO;
import com.biz.memo.domain.UserDTO;
import com.biz.memo.service.MemoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(value="/memo")
@SessionAttributes("memoDTO")
@Controller
public class MemoController {
	
	/*
	 * 1. DB에 사용자 저장하기
	 * 2. pw 부분을 암호화하기
	 * 3. spring form tag를 활용한 유효성 검사
	 */
	
	@ModelAttribute("memoDTO") // @ModelAttribute만 써도 상관없다
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
		
		model.addAttribute("BODY", "MEMO_LIST");
		model.addAttribute("MEMO_LIST", memoList);
		
		return "home";
	}
	
	/*
	 * SessionAttributes에서 설정한 변수(객체)에는
	 * @ModelAttribute를 설정해두어야 한다
	 * 5.x 이하에서는 필수, 5.x 이상에서는 선택
	 */
	
	@RequestMapping(value="/insert", method=RequestMethod.GET)
	public String insert(@ModelAttribute("memoDTO") MemoDTO memoDTO, Model model, HttpSession httpSession) {
		
		
		UserDTO userDTO = (UserDTO)httpSession.getAttribute("userDTO");
		
		if ( userDTO == null ) {

			model.addAttribute("LOGIN_MSG", "TRY");
			model.addAttribute("BODY", "LOGIN");
			
			return "redirect:/member/login";
		}
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat st = new SimpleDateFormat("HH:mm:ss");
		
		memoDTO.setM_date(sd.format(date));
		memoDTO.setM_time(st.format(date));
		
		memoDTO.setM_auth(userDTO.getU_id());
		
		model.addAttribute("BODY", "MEMO_WR");
		model.addAttribute("memoDTO", memoDTO);
		
		return "memo/memo-insert";
	}
	
	/*
	 * insert POST가 memoDTO를 수신할 때 입력 form에서 사용자가 입력한 값들이 있으면 그 값들을 memoDTO의 필드 변수에 setting하고
	 * 만약 없으면 메모리 어딘가에 보관중인 SessionAttributes로 설정된 memoDTO 변수에서 값을 가져와서 비어있는 필드 변수를 채워서 매개변수에 주입한다
	 * 따라서 form에서 보이지 않아도 되는 값들은 별도의 코딩을 하지 않아도 자동으로 insert POST로 전송되는 효과를 낸다
	 * 단, 이 기능을 효율적으로 사용하려면 jsp 코드에서 form이 아닌 Spring-form tag로 input을 코딩해야 한다
	 * 
	 * 기존의 세션 내용 (DTO 지워주기 위해 SessionStatus sStatus 추가해서 지워주기)
	 * sStatus.setComplete();
	 */
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(@ModelAttribute MemoDTO memoDTO, Model model, String search, SessionStatus sStatus) {
		
		log.debug("시퀀스 : " + memoDTO.getM_seq());
		log.debug("날짜 : " + memoDTO.getM_date());
		log.debug("TEXT : " + memoDTO.getM_text());
		
		int ret = mService.insert(memoDTO);
		
		/*
		 * SessionAttributes를 사용할 때 insert, update가 완료되고 view로 보내기 전
		 * 반드시 setComplete()를 실행해서 session에 담긴 값을 clear 해주어야 한다
		 */
		sStatus.setComplete();
		
		return "redirect:/";
	}
	
	@RequestMapping(value="/view", method=RequestMethod.GET)
	public String view(@RequestParam("id") String str_seq, @ModelAttribute MemoDTO memoDTO, Model model, HttpSession hSession) {
		
		long m_seq = 0;
		try {
			m_seq = Long.valueOf(str_seq);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		memoDTO = mService.findBySeq(m_seq);
		
		UserDTO userDTO = (UserDTO)hSession.getAttribute("userDTO");
		
		// 로그인한 id와 메모의 m_auth 값을 비교하여 작성자가 아니면 조치를 취하기(볼 수 없도록 만들기 등)
		if(userDTO != null && userDTO.getU_id().equals( memoDTO.getM_auth() ) ) {
			model.addAttribute("memoDTO", memoDTO);
			model.addAttribute("BODY","MEMO_VI");
			return "memo/memo-view";
		} else {
			model.addAttribute("LOGIN_MSG", "NO_AUTH");
			model.addAttribute("BODY","NEQ_AUTH");
			return "redirect:/memo/login";
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public String update(@RequestParam("id") String str_seq, @ModelAttribute MemoDTO memoDTO, Model model) {
		
		long m_seq = 0;
		try {
			m_seq = Long.valueOf(str_seq);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		memoDTO = mService.findBySeq(m_seq);
		model.addAttribute("memoDTO", memoDTO);
		model.addAttribute("BODY","MEMO_WR");
		
		return "memo/memo-insert";
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute MemoDTO memoDTO, Model model, SessionStatus sStatus) {
		
		int ret = mService.update(memoDTO);
		sStatus.setComplete();
		
		return "redirect:/memo/view?id=" + memoDTO.getM_seq();
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public String delete(@ModelAttribute MemoDTO memoDTO) {
	// public String delete(long m_seq) {
	// 브라우저에서 delete를 호출할 때
	// m_seq 값을 String이 아닌 long으로 받을 경우 값이 비어있거나 다른 문자가 들어가게 되면 400 오류가 발생한다
	// null 또는 숫자가 아닌 문자를 long으로 변환하려고 시도하면 서버 내부에서 exception이 발생하기 때문이다
	// 이것을 방지하기 위해 String으로 일단 받고 try-catch문을 이용하여 Long.valueOf()를 사용하여 변환하는 것이 안전하다
		
		int ret = mService.delete(memoDTO.getM_seq());
		
		return "redirect:/memo/list";
	}

}