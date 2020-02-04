package com.biz.bbs.service;

import java.util.List;

import com.biz.bbs.domain.BBsVO;

public interface BBsService {
	
	public List<BBsVO> selectAll();
	public BBsVO findById(long bbs_id);
	public List<BBsVO> findBySubject(String subject); // 제목으로 검색하기
	public List<BBsVO> findByWriter(String writer); // 작성자로 검색하기
	public List<BBsVO> findBySubAndWriter(String subject, String writer); // 제목과 작성자로 And 검색하기
	
	public int save(BBsVO bbsVO); // insert와 update를 처리할 service
	public int delete(long bbs_id); // delete
	public BBsVO reply(BBsVO bbsVO);

}
