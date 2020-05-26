package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.biz.shop.domain.ProductColorVO;
import com.biz.shop.domain.ProductOptionsVO;
import com.biz.shop.domain.ProductSizeVO;

public interface ProductOptionsDao {
	
	@Select("SELECT * FROM tbl_options WHERE o_division = 'COLOR' ")	
	public List<ProductOptionsVO> getColorList();
	
	@Select("SELECT * FROM tbl_options WHERE o_division = 'SIZE' ")
	public List<ProductOptionsVO> getSizeList();
	
	@Select("SELECT COUNT(*) FROM tbl_product_size WHERE p_code = #{p_code} AND s_size = #{s_size}")
	public int countProductSize (ProductSizeVO proSizeVO);
	
	@Insert("INSERT INTO tbl_product_size(p_code, s_size) VALUES (#{p_code}, #{s_size})")
	public int insert_size(ProductSizeVO proSizeVO);
	public int insert_color(ProductColorVO proColorVO);
	
	@Delete("DELETE FROM tbl_product_size WHERE p_code = #{p_code} AND s_size = #{s_size}")
	public int delete_size(ProductSizeVO proSizeVO);

}
