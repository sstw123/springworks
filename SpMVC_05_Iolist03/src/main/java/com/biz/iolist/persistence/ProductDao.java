package com.biz.iolist.persistence;

import java.util.List;

import com.biz.iolist.domain.ProductDTO;

public interface ProductDao {
	
	public List<ProductDTO> findAll();
	public ProductDTO findById(String p_code);
	public String getMaxPCode();
	public int insert(ProductDTO pdDTO);
	public int update(ProductDTO pdDTO);
	public int delete(String p_code);

}
