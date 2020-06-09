package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.shop.domain.ProductSizeVO;
import com.biz.shop.domain.ProductVO;
import com.biz.shop.persistence.sql.ProductSQL;

public interface ProductDao {
	
	@Select("SELECT * FROM tbl_product")
	public List<ProductVO> selectAll();
	
	@Results(value = {
			@Result(property = "p_code", column = "p_code"),
			@Result(property = "p_size_list", column = "p_code", javaType = List.class, many = @Many(select = "getProductSize"))
			}
	)
	@Select("SELECT * FROM tbl_product WHERE p_code = #{p_code}")
	public ProductVO findByPCode(String p_code);
	
	@Select("SELECT * FROM tbl_product_size "
			+ " LEFT JOIN tbl_options "
			+ " ON s_size = o_standard AND o_division = 'SIZE' "
			+ " WHERE p_code = #{p_code}")
	public ProductSizeVO getProductSize(String p_code);
	
	@Select("SELECT * FROM tbl_product WHERE p_name LIKE CONCAT('%', #{p_name}, '%')")
	public List<ProductVO> findByPName(String p_name);
	
	@InsertProvider(type = ProductSQL.class, method = "insert")
	public int insert(ProductVO productVO);
	
	@UpdateProvider(type = ProductSQL.class, method = "update")
	public int update(ProductVO productVO);
	
	@Delete("DELETE FROM tbl_product WHERE p_code = #{p_code}")
	public int delete(long p_code);
	
}
