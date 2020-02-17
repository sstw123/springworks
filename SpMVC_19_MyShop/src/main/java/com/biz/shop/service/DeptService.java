package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.shop.domain.DeptVO;
import com.biz.shop.repository.DeptDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeptService {
	
	protected final DeptDao deptDao;

	public List<DeptVO> selectAll() {
		return deptDao.findAll(); 
	}
	
	public void save(DeptVO deptVO) {
		deptDao.save(deptVO);
	}

	public DeptVO findById(long id) {
		return deptDao.findById(id).get();
	}

}
