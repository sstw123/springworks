package com.biz.shop.persistence;

import org.apache.ibatis.annotations.Select;

public interface DDL_Dao {
	
	// 동적 쿼리 문자열을 받아서 테이블 생성하기
	@Select("${create_product_table}")
	public void create_table(String create_product_table);

}
