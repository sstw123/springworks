package com.biz.rbooks.service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.biz.rbooks.domain.BookVO;
import com.biz.rbooks.domain.ReadBookVO;
import com.biz.rbooks.repository.BookDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor//초기화 되지 않은 final 필드와 @NonNull 어노테이션이 붙은 필드에 대한 생성자 생성
@Service
public class BookService {
	
	private final BookDao bookDao;

	public List<BookVO> selectAll() {
		return bookDao.selectAll();
	}

	public int insert(BookVO bookVO) {
		
		Random rnd = new Random();
		String b_code = rnd.nextGaussian() + "";
		
		bookVO.setB_code(b_code);
		bookVO.setB_name("연습하기");
		bookVO.setB_auther("홍길동");
		
		return bookDao.insert(bookVO);
	}

	public List<BookVO> selectByBnameList(String strText) {
		
		/*
		 * strText를 빈칸을 기준으로 분해해서 문자열 배열을 만들고
		 * 그 배열을 List<String>형으로 변환한 다음
		 * bnameList에 저장
		 */
		List<String> bnameList = Arrays.asList(strText.split(" "));
		
		return bookDao.selectByBnameList(bnameList);
	}

	public BookVO selectByBcode(String b_code) {
		return bookDao.selectByBcode(b_code);
	}

}