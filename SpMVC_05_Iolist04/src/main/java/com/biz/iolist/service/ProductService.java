package com.biz.iolist.service;

import java.util.ArrayList;
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
	
	ProductDao pdDao;
	
	@Autowired
	public void mapper() {
		pdDao = sqlSession.getMapper(ProductDao.class);
	}
	
	public List<ProductDTO> getAllList() {
//		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		List<ProductDTO> pdList = pdDao.findAll();
		return pdList;
	}
	
	public ProductDTO findByPCode(String p_code) {
//		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		ProductDTO pdDTO = pdDao.findByPCode(p_code);
		return pdDTO;
	}
	
	public int insert(ProductDTO pdDTO) {
//		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		
		String p_code = pdDao.getMaxPCode();
		int int_p_code = Integer.valueOf(p_code.substring(1)) + 1;
		p_code = p_code.substring(0,1) + String.format("%04d", int_p_code);
		pdDTO.setP_code(p_code);
		
		int ret = pdDao.insert(pdDTO);
		return ret;
	}
	
	public int update(ProductDTO pdDTO) {
//		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		int ret = pdDao.update(pdDTO);
		return ret;
	}
	
	public int delete(String p_code) {
//		ProductDao pdDao = sqlSession.getMapper(ProductDao.class);
		int ret = pdDao.delete(p_code);
		return ret;
	}

	public List<ProductDTO> findByPName(String p_name) {
		
		List<ProductDTO> pdList = pdDao.findByPName(p_name);
		return pdList;
	}
	
	public List<ProductDTO> findByPCodeOrPName(String str) {
		List<ProductDTO> pdList = null;
		
		// 상품코드로 조회하기
		ProductDTO pdDTO = this.findByPCode(str);
		
		if(pdDTO == null) {
			// 만약 상품코드가 존재하지 않으면 이름으로 조회하기
			pdList = this.findByPName(str);
		} else {
			// 만약 상품코드가 존재하면 주소값 주어주고(초기화) 거기에 DTO add시키기
			pdList = new ArrayList<>();
			pdList.add(pdDTO);
		}
		
		return pdList;
	}

}
