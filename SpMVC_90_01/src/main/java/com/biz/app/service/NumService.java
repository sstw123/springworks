package com.biz.app.service;

import org.springframework.stereotype.Service;

import com.biz.app.ScoreVO;

/*
 * @Service
 * Controller가 사용자의 요청을 받았는데 단순한 연산을 수행해서 결과를 보낼 사안이 아닐 때
 * 1. 조금 복잡한 무언가를 수행해야 할 때 Controller 기능 보조(Controller에서 일단 Service 클래스로 넘긴 뒤 Service 클래스에서 처리)
 * 2. 
 */
@Service
public class NumService {
	
	public int add(int num1, int num2) {
		int sum = 0;
		sum = num1 + num2;
		
		return sum;
	}

	public int sumOfEven(int start, int end) {
		int sumOfEven = 0;
		
		for(int i = start ; i <= end ; i++) {
			if(i % 2 == 0) {
				sumOfEven += i;
			}
		}
		
		return sumOfEven;
	}
	
	public int[] score(String kor, String eng, String math, String sci, String music) {
		
		int count = 5;
		
		int intKor = 0;
		int intEng = 0;
		int intMath = 0;
		int intSci = 0;
		int intMusic = 0;
		
		try {intKor = Integer.valueOf(kor);} catch (Exception e) {count--;}
		try {intEng = Integer.valueOf(eng);} catch (Exception e) {count--;}
		try {intMath = Integer.valueOf(math);} catch (Exception e) {count--;}
		try {intSci = Integer.valueOf(sci);} catch (Exception e) {count--;}
		try {intMusic = Integer.valueOf(music);} catch (Exception e) {count--;}
		
		int sum = intKor + intEng + intMath + intSci + intMusic;
		int avg = 0;
		if(count > 0) {
			avg = sum / count;
		}
		
		int[] arrScore = new int[3];
		arrScore[0] = sum;
		arrScore[1] = avg;
		arrScore[2] = count;
		
		return arrScore;
	}
	
	public ScoreVO score(ScoreVO scoreVO) {
		
		int sum = 0;
		sum = scoreVO.getKor()
			+ scoreVO.getEng()
			+ scoreVO.getMath()
			+ scoreVO.getSci()
			+ scoreVO.getMusic();
		
		scoreVO.setSum(sum);
		
		int avg = 0;
		scoreVO.setAvg(avg);
		
		scoreVO.setCount(0);
		
		return scoreVO;
	}

}
