package com.biz.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


// @Service
// Spring Framework의 Container에 등록될 bean 이라는 선언
// 다른 곳에서 가져와서 쓸 때 이미 등록된 bean을 싱글톤 객체로 생성한 걸 바로 가져다 쓴다
@Slf4j
@Service
public class StudentService {
	
	public List<String> getList(){
		List<String> strList = new ArrayList<>();
		
		strList.add("홍길동");
		strList.add("이몽룡");
		strList.add("성춘향");
		strList.add("임꺽정");
		strList.add("장보고");
		strList.add("이순신");
		
		return strList;

	}

}
