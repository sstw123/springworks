package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
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
	
	/*
	 * MySQL에서 auto_increment로 되어있는 s_seq 칼럼을 @Options로 설정해주면
	 * insert를 수행한 후 새로 생성된 s_seq값을 ProductSizeVO에 담아서 리턴한다
	 * 이렇게 해주지 않으면 s_seq에는 아무 값도 담겨있지 않다
	 */
	@Options(useGeneratedKeys = true, keyProperty = "s_seq")
	@Insert("INSERT INTO tbl_product_size(p_code, s_size) VALUES (#{p_code}, #{s_size})")
	public int insert_size(ProductSizeVO proSizeVO);
	public int insert_color(ProductColorVO proColorVO);
	
	@Delete("DELETE FROM tbl_product_size WHERE s_seq = #{s_seq}")
	public int delete_size(ProductSizeVO proSizeVO);

}
