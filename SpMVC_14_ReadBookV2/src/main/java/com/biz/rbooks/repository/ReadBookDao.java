package com.biz.rbooks.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.rbooks.domain.ReadBookVO;

public interface ReadBookDao {
	
	public List<ReadBookVO> selectAll();
	public ReadBookVO selectBySeq(long rb_seq);// rb_seq로 조회
	public List<ReadBookVO> selectByBcode(String rb_bcode);// 도서코드로 조회
	
	public int insert(ReadBookVO rBookVO);
	
	@UpdateProvider(type = ReadBookSQL.class, method = "update_sql")
	public int update(ReadBookVO rBookVO);
	
	@Delete("delete from tbl_read_book where rb_seq = #{rb_seq}")
	public int delete(long rb_seq);

}
