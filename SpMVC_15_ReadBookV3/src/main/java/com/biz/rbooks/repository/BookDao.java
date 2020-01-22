package com.biz.rbooks.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.rbooks.domain.BookVO;

@Mapper//안넣어도 상관없지만 mapper interface임을 확인시켜주는 어노테이션
public interface BookDao {

	@Select("select * from tbl_books")
	public List<BookVO> selectAll();
	
	
	
}
