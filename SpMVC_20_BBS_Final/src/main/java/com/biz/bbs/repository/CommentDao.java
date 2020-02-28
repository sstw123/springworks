package com.biz.bbs.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.CommentVO;

public interface CommentDao {
	
	@Select("SELECT * FROM tbl_comment")
	public List<CommentVO> selectAll();
	
	@Select("SELECT * FROM tbl_comment WHERE c_id = #{c_id}")
	public CommentVO findById(long c_id);
	
	// 부모 글에 달린 코멘트들 가져오기, 실제로 사용할 메소드
	@Select("SELECT * FROM tbl_comment WHERE c_b_id = #{c_b_id} ORDER BY c_date_time DESC")
	public List<CommentVO> findByBId(long c_b_id);
	
	// 부모 코멘트에 달린 코멘트들 가져오기
	@Select("SELECT * FROM tbl_comment WHERE c_p_id = #{c_p_id}")
	public List<CommentVO> findByPId(long c_p_id);
	
	public int insert(CommentVO commentVO);
	public int update(CommentVO commentVO);
	
	@Delete("DELETE FROM tbl_comment WHERE C_ID = #{c_id}")
	public int delete(long c_id);

}
