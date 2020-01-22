package com.biz.memo.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.memo.domain.MemoDTO;
import com.biz.memo.persistence.MemoDao;

// @Repository를 사용할 수도 있지만 
@Service
public class MemoService {
	
	@Autowired
	SqlSession sqlSession;
	
	MemoDao memoDao;
	
	@Autowired
	public void getMemoDao() {
		memoDao = sqlSession.getMapper(MemoDao.class);
	}
	
	/*
	 * Service를 사용하려고 시도하면 sqlSession으로부터 MemoDao mapper를 추출하여
	 * memoDao 객체를 사용할 수 있도록 초기화하라
	 */
	
	public List<MemoDTO> findAll() { 
		return memoDao.findAll();
	}
	
	public List<MemoDTO> findBySubject(String m_subject) {
		MemoDTO memoDTO = MemoDTO.builder()
				.m_subject(m_subject)
				.build();
		return memoDao.findBySubject(memoDTO);
	}

	public List<MemoDTO> findByCategory(String m_cate) {
		return memoDao.findByCategory(m_cate);
	}

	public int insert(MemoDTO memoDTO) {
		return memoDao.insert(memoDTO);
	}
	
}
