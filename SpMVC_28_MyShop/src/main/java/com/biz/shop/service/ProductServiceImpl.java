package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.shop.domain.ProductVO;
import com.biz.shop.persistence.ProductDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao;
	/*
	private final DDL_Dao ddl_Dao;
	
	public ProductServiceImpl(ProductDao proDao, DDL_Dao ddl_Dao) {
		this.proDao = proDao;
		this.ddl_Dao = ddl_Dao;
		
		ddl_Dao.create_table(CreateTableSQL.create_tbl_product);
		ddl_Dao.create_table(CreateTableSQL.create_tbl_product_size);
		ddl_Dao.create_table(CreateTableSQL.create_tbl_product_color);
	}
	*/
	@Override
	public List<ProductVO> selectAll() {
		return productDao.selectAll();
	}

	@Override
	public ProductVO findByPCode(String p_code) {
		return productDao.findByPCode(p_code);
	}

	@Override
	public List<ProductVO> findByPName(String p_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int save(ProductVO productVO) {
		int result = 0;
		
		if(productVO.getP_code() == null || productVO.getP_code().isEmpty()) {
			result = productDao.insert(productVO);
		} else {
			result = productDao.update(productVO);
		}
		
		return result;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
