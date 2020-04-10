package com.biz.score.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.score.mapper.ScoreDao;
import com.biz.score.model.ScoreVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ScoreService {
	
	private final ScoreDao scoreDao;
	
	public List<ScoreVO> selectAll() {
		return scoreDao.selectAll();
	}
	
	public ScoreVO findById(long id) {
		return scoreDao.findById(id);
	}
	/*
	public StudentVO findByIdFromStudent(long id) {
		return scoreDao.findByIdFromStudent(id);
	}
	
	public ScoreVO findByIdFromScore(long id) {
		return scoreDao.findByIdFromScore(id);
	}
	*/
	public int save(ScoreVO scoreVO) {
		int ret = 0;
		
		ScoreVO scVO = scoreDao.findByIdFromStudent(scoreVO.getId());
		
		// Student 테이블에서 ID로 검색한 결과가 없으면 학생정보와 점수 insert
		if(scVO == null) {
			// student 테이블 insert
			ret = scoreDao.insertStudent(scoreVO);
			
			// score 테이블 insert
			scoreVO.setS_num(scoreVO.getSt_num());
			for(int i = 0 ; i < 3 ; i++) {
				switch(i) {
					case 0 :
						scoreVO.setS_subject("국어");
						scoreVO.setS_score(scoreVO.getS_kor());
						break;
					case 1 :
						scoreVO.setS_subject("영어");
						scoreVO.setS_score(scoreVO.getS_eng());
						break;
					case 2 :
						scoreVO.setS_subject("수학");
						scoreVO.setS_score(scoreVO.getS_math());
						break;
				}
				ret += scoreDao.insertScore(scoreVO);
			}
			
		} else {
			// Student 테이블에서 ID로 검색한 결과가 있으면 학생정보와 점수 update
			// student 테이블 update
			ret = scoreDao.updateStudent(scoreVO);
			
			// score 테이블 update
			scoreVO.setS_num(scoreVO.getSt_num());
			for(int i = 0 ; i < 3 ; i++) {
				switch(i) {
					case 0 :
						scoreVO.setS_subject("국어");
						scoreVO.setS_score(scoreVO.getS_kor());
						break;
					case 1 :
						scoreVO.setS_subject("영어");
						scoreVO.setS_score(scoreVO.getS_eng());
						break;
					case 2 :
						scoreVO.setS_subject("수학");
						scoreVO.setS_score(scoreVO.getS_math());
						break;
				}
				ret += scoreDao.updateScore(scoreVO);
			}
		}
		
		return ret;
	}
	
	public int delete(long id) {
		return scoreDao.delete(id);
	}
	
}
