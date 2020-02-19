package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.shop.dao.DeptDao;
import com.biz.shop.domain.DeptVO;
import com.biz.shop.persistence.DeptRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeptService {
	
	protected final DeptRepository deptRepo;
	protected final DeptDao deptDao;

	public List<DeptVO> selectAll() {
		return deptRepo.findAll(); 
	}
	
	public void save(DeptVO deptVO) {
		deptRepo.save(deptVO);
	}

	public DeptVO findById(long id) {
		return deptRepo.findById(id).get();
	}

	public List<DeptVO> findByDName(String search) {
		return deptDao.findByDName(search);
	}

}
