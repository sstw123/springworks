package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.mapper.BBsDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("bbsSvcV1")
public class BBsServiceImplV1 implements BBsService {
	
	// protected : 자신과 상속받은 클래스에서만 접근 가능하도록
	protected final BBsDao bbsDao;

	@Override
	public List<BBsVO> selectAll() {
//		return bbsDao.selectAll();
		return bbsDao.selectMain();
	}

	@Override
	public BBsVO findById(long bbs_id) {
		return bbsDao.findById(bbs_id);
	}

	@Override
	public List<BBsVO> findBySubject(String subject) {
		return bbsDao.findBySubject(subject);
	}

	@Override
	public List<BBsVO> findByWriter(String writer) {
		return bbsDao.findByWriter(writer);
	}

	@Override
	public List<BBsVO> findBySubAndWriter(String subject, String writer) {
		return bbsDao.findBySubAndWriter(subject, writer);
	}

	@Override
	public int save(BBsVO bbsVO) {
		
		long bbs_id = bbsVO.getBbs_id();
		if(bbs_id == 0) {
			return bbsDao.insert(bbsVO);
		} else {
			return bbsDao.update(bbsVO);
		}
		
	}

	@Override
	public int delete(long bbs_id) {
		return bbsDao.delete(bbs_id);
	}

	@Override
	public BBsVO reply(BBsVO bbsVO) {
		bbsVO.setBbs_p_id(bbsVO.getBbs_id());
		bbsVO.setBbs_id(0);//id값 초기화
		bbsVO.setBbs_subject("re : " + bbsVO.getBbs_subject());
		
		bbsDao.insert(bbsVO);
		
		return bbsVO;
	}

}
