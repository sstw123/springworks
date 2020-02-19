package com.biz.shop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.biz.shop.domain.ProductVO;
import com.biz.shop.persistence.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductService {
	
	private final ProductRepository pDao;
	
	public void save(ProductVO productVO) {
		ProductVO pVO = pDao.save(productVO);
		log.debug("상품정보 : " + pVO.toString());
	}
	
	public List<ProductVO> selectAll() {
		List<ProductVO> proList = pDao.findAll();
		return proList;
	}

	public ProductVO findById(long id) {
		/*
		 * hibernate의 기본 조회 메소드들은 모든 VO클래스를 Optional 클래스로 감싸서 리턴한다
		 * NullPointerException 방지를 위한 조치인데 실제 VO 객체를 추출하려면
		 * .get() 메소드를 이용해 가져온다
		 */
		Optional<ProductVO> productVO = pDao.findById(id);
		return productVO.get();
	}

}
