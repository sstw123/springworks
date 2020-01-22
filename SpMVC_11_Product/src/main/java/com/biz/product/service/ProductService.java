package com.biz.product.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.product.domain.ProductDTO;
import com.biz.product.persistence.ProductDao;

@Service
public class ProductService {
	
	@Autowired
	SqlSession sqlSession;
	
	ProductDao proDao;
	
	@Autowired
	public void proDao() {
		proDao = sqlSession.getMapper(ProductDao.class);
	}
	
	public ProductDTO selectByPCode(String p_code) {
		return proDao.selectByPCode(p_code);
	}
	
	public List<ProductDTO> selectByPName(String p_name) {
		return proDao.selectByPName(p_name);
	}

	public List<ProductDTO> selectAll() {
		return proDao.selectAll();
	}
	
	

}
