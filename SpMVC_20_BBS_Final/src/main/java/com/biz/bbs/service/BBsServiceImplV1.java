package com.biz.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
@Service("bbsSvcV1")
public class BBsServiceImplV1 implements BBsService {

	protected final BBsDao bbsDao;
	public BBsServiceImplV1(BBsDao bbsDao) {
		this.bbsDao = bbsDao;
	}

	/*
	 * pagination을 수행할 때
	 * - 원글 + 댓글 포함한 리스트를 pagination 대상으로 할 것인지(일반적)
	 * - 원글 리스트를 pagination 대상으로 할 것인지
	 * 원글만 pagination 할 것인지
	 * allbbsList를 필요한 개수만큼 pagination으로 분할하여 사용
	 */
	@Override
	public List<BBsVO> selectAll() {
		List<BBsVO> allbbsList = bbsDao.selectAll();
		
		Collections.sort(allbbsList, (o1, o2)-> 
			(int)(o2.getB_id() - o1.getB_id())
		);// 모든 글을 b_id 낮을수록 나중으로 정렬
		
		List<BBsVO> parentList = allbbsList.stream().filter(vo -> vo.getB_p_id() == 0).collect(Collectors.toList());//최상위 글 리스트 추출
		List<BBsVO> bbsList = new ArrayList<>();//모든 글 리스트
		
		parentList.forEach(vo -> {
			bbsList.addAll(selectReply(vo, 0));
		});
		
		return bbsList;
	}
	
	public List<BBsVO> selectReply(BBsVO bbsVO, int depth) {
		List<BBsVO> replyList = new ArrayList<BBsVO>();
		
		if(depth > 0) {
			String b_subject = "";
		
			for(int i = 0; i < depth; i++) {
				b_subject += "|&nbsp;&nbsp;&nbsp;";
			}
			b_subject += "<i class='fas fa-hand-point-right'></i>&nbsp;" + bbsVO.getB_subject();
			bbsVO.setB_subject(b_subject);
		}
		
		replyList.add(bbsVO);
		
		List<BBsVO> tempList = bbsDao.findByPId(bbsVO.getB_id());//b_id를 부모로 하는 자식답글들 전부 가져오기
		if(tempList.size() < 1) return replyList;//자식답글 없으면 원글반환 
		
//		자식답글 있으면 아래로 진행
//		자식답글 하나마다 또 자식답글 가져오기
		for(BBsVO vo : tempList) {
			List<BBsVO> childList = this.selectReply(vo, depth+1);
//			자식답글마다 childList 내용물VO 전부 추가
			replyList.addAll(childList);
		}
		
		return replyList;
	}

	@Override
	public BBsVO findById(long b_id) {
		return bbsDao.findById(b_id);
	}

	@Override
	public List<BBsVO> findBySubject(String b_subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BBsVO> findByWriter(String b_writer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(BBsVO bbsVO) {

		// 작성일자를 현재 저장하는 날짜로 세팅을 하자
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter df 
			= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		bbsVO.setB_date_time(ldt.format(df).toString());
		int ret = bbsDao.insert(bbsVO);
		return ret;
	
	}

	@Override
	public int delete(long b_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(BBsVO bbsVO) {
		
		// 작성일자를 현재 저장하는 날짜로 세팅을 하자
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter df 
			= DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		bbsVO.setB_date_time(ldt.format(df).toString());
		int ret = bbsDao.update(bbsVO);
		return ret ;
	
	}

}
