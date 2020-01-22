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

	public int insert(ProductDTO proDTO) {
		String p_code = proDao.selectMaxPCode();

		// 만약 상품테이블에 데이터가 하나도 없을 경우, 새로 추가할 때
		// intSuffixCode를 1로, p_prefixCode를 P로 세팅한 후 진행
		int intSuffixCode = 1;
		String p_prefixCode = "P";
		try {
			p_prefixCode = p_code.substring(0,1);
			String p_suffixCode = p_code.substring(1);
			intSuffixCode = Integer.valueOf(p_suffixCode) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String strPCode = String.format("%s%04d", p_prefixCode, intSuffixCode);
		proDTO.setP_code(strPCode);
		
		return proDao.insert(proDTO);
	}

	public int update(ProductDTO proDTO) {
		return proDao.update(proDTO);
	}
	
	

}
