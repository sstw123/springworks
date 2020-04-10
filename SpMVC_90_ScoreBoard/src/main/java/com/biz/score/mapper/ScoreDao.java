package com.biz.score.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.score.model.ScoreVO;

public interface ScoreDao {
	
	public List<ScoreVO> selectAll();
	
	public ScoreVO findById(long id);
	
	@Select("SELECT * FROM tbl_student WHERE id = #{id}")
	public ScoreVO findByIdFromStudent(long id);
	
	/*
	@Select("SELECT * FROM tbl_score WHERE id = #{id}")
	public ScoreVO findByIdFromScore(long id);
	
	@Select("SELECT * FROM tbl_score WHERE s_num = #{s_num}")
	public ScoreVO findBySNumFromScore(String s_num);
	*/
	
	public int insertStudent(ScoreVO scoreVO);
	public int updateStudent(ScoreVO scoreVO);
	
	public int insertScore(ScoreVO scoreVO);
	public int updateScore(ScoreVO scoreVO);
	
	@Delete("DELETE FROM tbl_student WHERE id = #{id}")
	public int delete(long id);

	
}
