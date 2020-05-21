package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.shop.domain.FileVO;
import com.biz.shop.persistence.FileUploadDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	private final FileUploadDao fileDao;
	/*
	private final DDL_Dao ddl_Dao;
	
	public FileUploadServiceImpl(FileUploadDao fileDao, DDL_Dao ddl_Dao) {
		this.fileDao = fileDao;
		this.ddl_Dao = ddl_Dao;
		
		ddl_Dao.create_table(create_file_table);
	}
	*/

	@Override
	public List<FileVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FileVO> findByPCode(String file_p_code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FileVO findById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(FileVO productFileVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(FileVO productFileVO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String file_p_code) {
		// TODO Auto-generated method stub
		return 0;
	}

}
