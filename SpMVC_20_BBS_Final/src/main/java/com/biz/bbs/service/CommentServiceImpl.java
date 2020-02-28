package com.biz.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.repository.CommentDao;

@Service("cmtSvcV1")
public class CommentServiceImpl implements CommentService {
	
	protected final CommentDao cmtDao;
	
	public CommentServiceImpl(CommentDao cmtDao) {
		super();
		this.cmtDao = cmtDao;
	}
	// 생성자 끝

	@Override
	public List<CommentVO> selectAll() {
		return cmtDao.selectAll();
	}

	@Override
	public CommentVO findById(long c_id) {
		return cmtDao.findById(c_id);
	}

	@Override
	public List<CommentVO> findByBId(long c_b_id) {
		return cmtDao.findByBId(c_b_id);
	}
	
	@Override
	public List<CommentVO> findByPId(long c_p_id) {
		return cmtDao.findByPId(c_p_id);
	}
	
	@Override
	public int insert(CommentVO commentVO) {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String c_date_time = ldt.format(dtFormat);
		
		commentVO.setC_date_time(c_date_time);
		
		return cmtDao.insert(commentVO);
	}

	@Override
	public int update(CommentVO commentVO) {
		return cmtDao.update(commentVO);
	}

	@Override
	public int delete(long c_id) {
		return cmtDao.delete(c_id);
	}

}
