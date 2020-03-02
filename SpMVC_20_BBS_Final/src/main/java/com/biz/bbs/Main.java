package com.biz.bbs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.service.FileReaderService;

public class Main {

	// 진입점, endpoint method
	public static void main(String[] args) {
		FileReaderService frSvc = new FileReaderService();
		
		List<BBsVO> bbsList = frSvc.getBBsData();
		/*
//		1-1. sort의 원래 코드
		Collections.sort(bbsList, new Comparator<BBsVO>() {

			@Override
			public int compare(BBsVO o1, BBsVO o2) {
				int s = (int)(o1.getB_id() - o2.getB_id());
				return s;
			}
			
		});
		*/
		
//		1-2. sort의 람다 코드
		Collections.sort(bbsList, (o1, o2)-> 
			(int)(o1.getB_id() - o2.getB_id())
		);
		
//		1-3. 날짜 시간 역순 정렬 : compareTo 사용
		Collections.sort(bbsList, (o1, o2)->
			o2.getB_date_time().compareTo(o1.getB_date_time())
		);
		
		// ---------------------------------------------------------------
		// 부모id(b_p_id)가 0인 리스트(답글이 아닌 원글)만 추출하기
		List<BBsVO> pList = new ArrayList<BBsVO>();
		
		/*
//		2-1. bbsList에 b_p_id가 0인 값만 add
		for(BBsVO vo : bbsList) {
			if(vo.getB_p_id() == 0) pList.add(vo);
		}
		*/
		
		/*
//		2-2. 람다 코드 : List의 forEach 메소드 사용, 2-1과 같은 결과
		bbsList.forEach(vo->{
			if(vo.getB_p_id() == 0) pList.add(vo);
		});
		*/
		
//		2-3. 위와 같은 코드(람다를 쓰지 않는 원래 코드)
		bbsList.forEach(new Consumer<BBsVO>() {
			@Override
			public void accept(BBsVO vo) {
				if(vo.getB_p_id() == 0) pList.add(vo);
			}
		});
		
		/*
//		람다 코드
		bbsList.forEach(vo->{
			System.out.println(vo.toString());
		});
		*/
		
//		위와 같은 더 간결한 람다 코드
		bbsList.forEach(System.out::println);
		
	}
	
}
