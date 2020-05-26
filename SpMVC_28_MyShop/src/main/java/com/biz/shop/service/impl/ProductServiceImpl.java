package com.biz.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biz.shop.domain.ProductVO;
import com.biz.shop.persistence.ProductDao;
import com.biz.shop.service.FileUploadService;
import com.biz.shop.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	private final ProductDao productDao;
	private final FileUploadService fileSvc;
	
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
	public int save(ProductVO productVO, MultipartFile file) {
		String p_code = productVO.getP_code();
		int result = 0;
		
		// DB에서 상품코드로 조회한 정보 가져오기
		ProductVO dbProductVO = productDao.findByPCode(p_code);
		
		if(dbProductVO == null) {
			if(file != null) {
				String saveName = fileSvc.fileUpload(file);
				productVO.setP_file(saveName);
			}
			
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
