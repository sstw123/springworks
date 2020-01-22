package com.biz.iolist.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.iolist.domain.DeptDTO;
import com.biz.iolist.domain.ProductDTO;
import com.biz.iolist.persistence.DeptDao;

@Service
public class DeptService {
	
	@Autowired
	SqlSession sqlSession;
	
	DeptDao deptDao;
	
	@Autowired
	public void deptDaoMapper() {
		deptDao = sqlSession.getMapper(DeptDao.class);
	}
	
	public List<DeptDTO> getAllList() {
		// sqlSession 기본 openSession(true) 상태
//		DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
		List<DeptDTO> deptList = deptDao.selectAll();
		return deptList;
	}

	public int insert(DeptDTO deptDTO) {
//		DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
		
		/*
		 * 거래처코드를 자동생성해서 deptDTO의 d_code에 저장
		 */
		
		String d_code = deptDao.getDCode();
		String d_code_num = d_code.substring(1);
		int int_d_code = Integer.valueOf(d_code_num) + 1;
		
		d_code = d_code.substring(0,1) + String.format("%04d", int_d_code);
		
		deptDTO.setD_code(d_code);
		
		int ret = deptDao.insert(deptDTO);
		return ret;
	}

	public DeptDTO findByDCode(String d_code) {
//		DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
		
		DeptDTO dDTO = deptDao.findByDCode(d_code);
		
		return dDTO;
	}
	
	public List<DeptDTO> findByDName(String d_name) {
		
		List<DeptDTO> deptList = deptDao.findByDName(d_name);
		
		return deptList;
	}
	
	public List<DeptDTO> findByDCodeOrDName(String str) {
		List<DeptDTO> deptList;
		
		DeptDTO deptDTO = this.findByDCode(str);
		
		if(deptDTO == null) {
			deptList = this.findByDName(str);
		} else {
			deptList = new ArrayList<>();
			deptList.add(deptDTO);
		}
		
		return deptList;
	}

	public int delete(String d_code) {
//		DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
		
		int ret = deptDao.delete(d_code);
		
		return ret;
	}

	public int update(DeptDTO deptDTO) {
//		DeptDao deptDao = sqlSession.getMapper(DeptDao.class);
		
		int ret = deptDao.update(deptDTO);
		return 0;
	}
	
}
