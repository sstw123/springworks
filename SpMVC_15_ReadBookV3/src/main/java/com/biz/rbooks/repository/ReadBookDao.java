package com.biz.rbooks.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.biz.rbooks.domain.ReadBookVO;

@Mapper//안넣어도 상관없지만 mapper interface임을 확인시켜주는 어노테이션
public interface ReadBookDao {

	@Select("select * from tbl_read_book")
	public List<ReadBookVO> selectAll();
	
	
	
}
