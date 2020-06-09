package com.biz.shop.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.shop.domain.ProductColorVO;
import com.biz.shop.domain.ProductOptionsVO;
import com.biz.shop.domain.ProductSizeVO;
import com.biz.shop.persistence.ProductOptionsDao;
import com.biz.shop.service.ProductOptionsService;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductOptionsServiceImpl implements ProductOptionsService {
	
	private final ProductOptionsDao proOptDao;
	
	@Override
	public List<ProductOptionsVO> getColorList() {
		// TODO Auto-generated method stub
		return proOptDao.getColorList();
	}

	@Override
	public List<ProductOptionsVO> getSizeList() {
		// TODO Auto-generated method stub
		return proOptDao.getSizeList();
	}
	
	@Override
	public int countProductSize(ProductSizeVO proSizeVO) {
		// TODO Auto-generated method stub
		return proOptDao.countProductSize(proSizeVO);
	}

	@Override
	public int insert_size(ProductSizeVO proSizeVO) {
		// TODO Auto-generated method stub
		return proOptDao.insert_size(proSizeVO);
	}

	@Override
	public int insert_color(ProductColorVO proColorVO) {
		// TODO Auto-generated method stub
		return proOptDao.insert_color(proColorVO);
	}

	@Override
	public int delete_size(ProductSizeVO proSizeVO) {
		// TODO Auto-generated method stub
		return proOptDao.delete_size(proSizeVO);
	}

}
