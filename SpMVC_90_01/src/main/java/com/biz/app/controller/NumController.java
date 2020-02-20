package com.biz.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.app.ScoreVO;
import com.biz.app.service.NumService;

@RequestMapping(value="/number")
@Controller
public class NumController {
	
	@Autowired
	protected NumService nSvc;
	
	@ResponseBody
	@RequestMapping(value="/add", produces = "text/html; charset=UTF-8")
	public String add() {
		
//		NumService numSvc = new NumService();
//		int sum1 = numSvc.add(30, 40);
		
		int sum2 = nSvc.add(30, 40);
		
		return "두 수의 합 : " + sum2;
	}
	
	/*
	 * 사용자가 /number/even 이라고 요청하면
	 * 1~100 숫자 중에서 짝수의 덧셈만 수행하여 결과를 알려주고 싶을 때 사용하는 메소드
	 */
	@ResponseBody
	@RequestMapping(value="/even", produces = "text/html; charset=UTF-8")
	public String even() {
		
		// Service에게 요청해서 짝수 덧셈 수행
		int start = 1;
		int end = 100;
		int sumOfEven = nSvc.sumOfEven(start, end);
		
		String str = String.format("%d부터 %d까지의 숫자 중 짝수의 합 : %d", start, end, sumOfEven);
		
		return str;
	}
	
	/*
	 * 사용자가 요청한 변수=값의 형태는 무조건 값이 문자열이다
	 * 매개변수 type을 int로 선언하면 spring은 사용자의 변수를 수신한 후 자동으로 int형으로 변환해준다
	 * (ex : Integer.valueOf(변수) 메소드를 이용)
	 * 
	 * 만약 수신한 값이 숫자로 변환이 불가능하면 사용자에게 400오류 전송
	 * 
	 * 따라서, int val = 0, try-catch + Integer.valueOf()를 이용해 처리해주면 이상한 값이 전달되었을 경우
	 * try-catch에 의해 아무것도 실행되지 않고 int val = 0에 의해 기본값은 0으로 설정되어 처리된다
	 */
	@ResponseBody
	@RequestMapping(value="/num2even", produces = "text/html;charset=UTF-8")
	public String num2even(int start, String strEnd) {
		
		int end = 0;
		try {
			end = Integer.valueOf(strEnd);
		} catch (Exception e) {
			// TODO: handle exception
		}
		int sumOfEven = nSvc.sumOfEven(start, end);
		
		String str = String.format("%d부터 %d까지의 숫자 중 짝수의 합 : %d", start, end, sumOfEven);
		
		return str;
	}
	
	/*
	 * 국어,영어,수학,과학,음악점수를 request로 받아서
	 * 총점과 평균을 계산한 후 response 하려고 할 때
	 */
	@ResponseBody
	@RequestMapping(value="/score", produces = "text/html;charset=UTF-8")
	public String score(String kor, String eng, String math, String sci, String music) {
		
		int[] arrScore = nSvc.score(kor, eng, math, sci, music);
		int sum = arrScore[0];
		int avg = arrScore[1];
		int count = arrScore[2];
		
		return String.format("과목 점수 총 합 : %d\n과목 점수 평균 : %d\n과목 개수 : %d", sum, avg, count);
	}
	
	/*
	 * 매개변수로 Model 클래스를 설정하고 model 객체에 addAttribute("변수명",값) 형식으로 내용을 추가하고
	 * jsp파일을 return하면 Rendering을 한다
	 * spring, tomcat은
	 */
	@RequestMapping(value="/score_jsp", produces = "text/html;charset=UTF-8")
	public String score_jsp(String kor, String eng, String math, String sci, String music, Model model) {
		
		int[] arrScore = nSvc.score(kor, eng, math, sci, music);
		int sum = arrScore[0];
		int avg = arrScore[1];
		int count = arrScore[2];
		
		model.addAttribute("arrScore", arrScore);
		
		// score.jsp 파일을 읽어서 model에 담겨있는 변수들과 함께 Rendering하기		
		return "score";
	}
	
	@RequestMapping(value="/score_input", method=RequestMethod.GET)
	public String score_input() {
		return "score_input";
	}
	
	@RequestMapping(value="/score_input", method=RequestMethod.POST)
	public String score_input(ScoreVO scoreVO, Model model) {
		
		System.out.println("=========== 오류가 나는지 확인하는 코드 ===========");
		
		nSvc.score(scoreVO);//scoreVO 세팅
		
		model.addAttribute("scoreVO", scoreVO);
		
		return "score_input";
	}

}
