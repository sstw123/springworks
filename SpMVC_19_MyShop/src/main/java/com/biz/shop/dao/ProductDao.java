package com.biz.shop.dao;

import org.apache.ibatis.annotations.Delete;

public interface ProductDao {
	
	@Delete("delete from tbl_product where p_id = #{id}")
	public int delete(long id);

}
