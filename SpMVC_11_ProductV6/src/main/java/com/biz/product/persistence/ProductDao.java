package com.biz.product.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.product.domain.PageDTO;
import com.biz.product.domain.ProductDTO;

public interface ProductDao {
	
	public List<ProductDTO> selectAll();
	public long proTotalCount(@Param("p_names") List<String> p_nameList);
	// 오라클DB의 rownum이 매개변수 offset부터 limit까지의 데이터만 추출
	public List<ProductDTO> selectPagination(PageDTO pageDTO);
	
	public ProductDTO selectByPCode(String p_code);
	public List<ProductDTO> selectByPName(String p_name);
	
	public List<ProductDTO> selectByPNameListAndPagiNation(@Param("p_names") List<String> p_names, @Param("pageDTO") PageDTO pageDTO);
	
	// 상품코드의 가장 마지막 값 가져오기
	public String selectMaxPCode();
	
	public int insert(ProductDTO productDTO);
	public int update(ProductDTO productDTO);
	public int delete(String p_code);

}
