package com.biz.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
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
		List<CommentVO> parentList = cmtDao.findByBId(c_b_id);//최상위 댓글들만 가져오기
		
		//최상위 댓글들 id 순서대로 sort하기 (SQL이 아닌 java에서 sort하기)
		Collections.sort(parentList, new Comparator<CommentVO>() {
			@Override
			public int compare(CommentVO o1, CommentVO o2) {
				int sort = (int) (o1.getC_id() - o2.getC_id());
				//compare() : 음수나 0이면 그대로, 양수면 자리 변경
				//1-2 = 음수 / 2-1 = 양수 : id 숫자가 낮을수록 앞에 위치하게 된다
				
				return sort;
			}
		});
		
		List<CommentVO> cmtList = new ArrayList<>();//모든 댓글들을 순서대로 저장할 List
		
//		최상위 댓글 리스트
		parentList.forEach(vo -> {
			// 최상위 댓글 마다 자식댓글,자식의자식댓글.. 가져와서 순서대로 모든 댓글 List에 add
			cmtList.addAll(this.findCommentByBId(vo, 0));
		});
		
		return cmtList;//웹에 보여줄 댓글 List
	}
	
	// 부모댓글 하나에 달린 자식댓글 전부 가져오는 메소드
	// 재귀로 더 이상 자식 댓글이 없을 떄까지 가져오기(밑에 설명)
	private List<CommentVO> findCommentByBId(CommentVO cmtVO, int depth) {
		List<CommentVO> cmtList = new ArrayList<>();
		if(depth > 0) {
			String c_subject = "";
			for(int i = 0 ; i < depth ; i++) {
				c_subject += "|&nbsp;&nbsp;&nbsp;";
			}
			c_subject += cmtVO.getC_subject();
			cmtVO.setC_subject(c_subject);
		}
		// 댓글내용 prefix 세팅 끝
		cmtList.add(cmtVO);
		// 코드 1. 첫번째는 부모댓글을 리스트에 저장
		// 코드 2. 두번째부턴 자식댓글
		
		List<CommentVO> tempList = cmtDao.findByPId(cmtVO.getC_id());//부모댓글 기준으로 자식댓글 가져오기
		// Collections.sort(tempList, (o1, o2) -> (int)(o1.getC_id() - o2.getC_id()));//id 숫자 빠를수록 앞쪽에 위치
		
		if(tempList.size() < 1) return cmtList;//코드 3. 자식댓글 없으면 부모댓글만 리턴
		
		// 자식댓글 있으면 첫번째 자식댓글(vo)부터 순서대로 그 아래의 자식댓글 또 찾고 또 찾아서 더 이상 자식 댓글이 없을 때까지 찾음
//		예를 들어 댓글1,댓글2-1,댓글3-1,댓글3-2,댓글2-2,댓글3-1 이 달려있다고 가정해보자
//		-> 4. cmtList[1] : 댓글1 저장 -> vo1
		
//		-> 5. cmtList[2-1] : 댓글2-1 저장 -> vo2[1]
		
//		-> 6-1. cmtList[3-1] : 코드 3에서 cmtList[3-1]리턴, cmtList[3-1]에는 vo3[1]만 저장되어 있다
//		->		위의 cmtList[3-1]을 cmtList[2-1]에 addAll하고 진행 -> cmtList[2-1] 상태는 vo2[1], vo3[1]
//		-> 6-2. cmtList[3-2] : 코드 3에서 cmtList[3-2]리턴, cmtList[3-2]에는 vo3[2]만 저장되어 있다
//		->		위의 cmtList[3-2]을 cmtList[2-1]에 addAll하고 진행 -> cmtList[2-1] 상태는 vo2[1], vo3[1], vo3[2]
//		-> 6-3. cmtList[2-1]에는 vo2[1], vo3[1], vo3[2] 3개가 순서대로 들어있다. cmtList[1]에 cmtList[2-1]을 addAll하고 진행
//		->		cmtList[1]에는 vo1, vo2[1], vo3[1], vo3[2] 4개가 순서대로 들어있다
		
//		-> 7. cmtList[2-2] : 댓글2-2 저장 -> vo2[2]
		
//		-> 8-1. cmtList[3-1] : 코드 3에서 cmtList[3-1]리턴, cmtList[3-1]에는 vo3[1]만 담겨있다
//		->		위의 cmtList[3-1]을 cmtList[2-2]에 addAll하고 진행 -> cmtList[2-2] 상태는 vo2[2], vo3[1]
//		-> 8-2. cmtList[2-2]에는 vo2[2], vo3[1] 2개가 순서대로 들어있다. cmtList[1]에 cmtList[2-2]를 addAll하고 진행
//		->		cmtList[1]에는 vo1, vo2[1], vo3[1], vo3[2], vo2[2], vo[1] 6개가 순서대로 들어있다
		
//		-> 9. 모든 댓글을 cmtList[1]에 순서대로 저장한 뒤 for문이 끝나고 cmtList[1] 리턴 
		for(CommentVO vo : tempList) {
			List<CommentVO> childList = this.findCommentByBId(vo, depth+1);
			// 3에서 리턴된 후 아래로 진행
			cmtList.addAll(childList);
		}
		return cmtList;
	}
	
	@Override
	public List<CommentVO> findByPId(long c_p_id) {
		return cmtDao.findByPId(c_p_id);
	}
	
	@Override
	public int insert(CommentVO commentVO) {
		int ret = 0;
		if(commentVO.getC_id() > 0) {
			
			ret = cmtDao.update(commentVO);
			
		} else {
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter dtFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String c_date_time = ldt.format(dtFormat);
			
			commentVO.setC_date_time(c_date_time);
			
			ret = cmtDao.insert(commentVO);
		}
		
		return ret;
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
