package com.biz.iolist.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.iolist.domain.ProductDTO;
import com.biz.iolist.persistence.ProductDao;

@Service
public class ProductService {
	
	@Autowired
	SqlSession sqlSession;
	
	public List<ProductDTO> getAllList() {
		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		List<ProductDTO> pdList = pdDao.findAll();
		return pdList;
	}
	
	public ProductDTO findById(String p_code) {
		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		ProductDTO pdDTO = pdDao.findById(p_code);
		return pdDTO;
	}
	
	public int insert(ProductDTO pdDTO) {
		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		
		String p_code = pdDao.getMaxPCode();
		int int_p_code = Integer.valueOf(p_code.substring(1)) + 1;
		p_code = p_code.substring(0,1) + String.format("%04d", int_p_code);
		pdDTO.setP_code(p_code);
		
		int ret = pdDao.insert(pdDTO);
		return ret;
	}
	
	public int update(ProductDTO pdDTO) {
		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		int ret = pdDao.update(pdDTO);
		return ret;
	}
	
	public int delete(String p_code) {
		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		int ret = pdDao.delete(p_code);
		return ret;
	}

}
