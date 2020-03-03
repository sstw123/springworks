package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.repository.BBsDao;

/*
 * 다중 select를 수행하는 메소드들이 있고
 * 재귀호출에 의해서 계속되는 select문이 실행된다
 * 이 때 @Transactional 다중 select를 transaction으로 보호하여
 * 중간에 데이터 fetch가 누락되는 것을 막을 수 있다
 */
@Transactional
@Service("bbsSvcV2")
public class BBsServiceImplV2 extends BBsServiceImplV1 {

	public BBsServiceImplV2(BBsDao bbsDao) {
		super(bbsDao);
	}

	@Override
	public List<BBsVO> selectAll() {
		List<BBsVO> bbsList = bbsDao.selectLevel();
		return bbsList;
	}
}
