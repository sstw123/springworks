package com.biz.rbooks.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.rbooks.domain.BookVO;

/*
 * mybatis-context.xml에서 <mybatis-spring:scan base-package="com.biz.rbooks.repository"/>를 설정해주었다
 * Dao의 method에 Annotation을 붙이지 않으면
 * mapper.xml에서 id를 검색하여 해당 query로 사용한다
 */
public interface BookDao {
	@Select("select * from tbl_books")
	public List<BookVO> selectAll();
	
	public int insert(BookVO bookVO);
	public int update(BookVO bookVO);

	public List<BookVO> selectByBnameList(@Param("bnameList")List<String> bnameList);

	@Select("select * from tbl_books where b_code = #{b_code}")
	public BookVO selectByBcode(String b_code);
}
