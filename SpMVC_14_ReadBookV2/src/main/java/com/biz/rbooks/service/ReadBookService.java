package com.biz.rbooks.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.rbooks.domain.ReadBookVO;
import com.biz.rbooks.repository.ReadBookDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReadBookService {
	
	private final ReadBookDao rBookDao;
	
	public List<ReadBookVO> selectAll() {
		return rBookDao.selectAll();
	}

	public ReadBookVO selectBySeq(long rb_seq) {
		return rBookDao.selectBySeq(rb_seq);
	}
	
	public List<ReadBookVO> selectByBcode(String rb_bcode) {
		return rBookDao.selectByBcode(rb_bcode);
	}
	
	public int insert(ReadBookVO rBookVO) {
		return rBookDao.insert(rBookVO);
	}

	public int update(ReadBookVO rBookVO) {
		return rBookDao.update(rBookVO);
	}

	public int delete(long rb_seq) {
		return rBookDao.delete(rb_seq);
	}

	
}
