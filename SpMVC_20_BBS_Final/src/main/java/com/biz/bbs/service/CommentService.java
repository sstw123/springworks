package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.CommentVO;

@Service
public interface CommentService {
	
	public List<CommentVO> selectAll();
	public CommentVO findById(long c_id);
	
	// 부모 글에 달린 코멘트들 추출하기
	public List<CommentVO> findByBId(long c_b_id);
	
	// 부모 코멘트에 달린 코멘트들 추출하기
	public List<CommentVO> findByPId(long c_p_id);
	
	public int insert(CommentVO commentVO);
	public int update(CommentVO commentVO);
	public int delete(long c_id);

}
