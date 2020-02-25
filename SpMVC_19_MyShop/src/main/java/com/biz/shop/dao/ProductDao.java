package com.biz.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.shop.domain.ProductVO;

public interface ProductDao {
	
	@Select("SELECT * FROM tbl_product WHERE p_name LIKE CONCAT('%', #{search}, '%')")
	public List<ProductVO> selectByName(String search);
	
	@Delete("delete from tbl_product where p_id = #{id}")
	public int delete(long id);

}
