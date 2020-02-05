package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.mapper.CommentDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	
	private final CommentDao cmtDao;
	
	public List<CommentVO> selectAll(long cmt_p_id) {
		return cmtDao.selectAll(cmt_p_id);
	}

	public int insert(CommentVO cmtVO) {
		return cmtDao.insert(cmtVO);
	}

}
