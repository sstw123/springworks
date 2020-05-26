package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biz.shop.domain.FileVO;

@Service
public interface FileUploadService {
	
	public String fileUpload(MultipartFile file);
	
	public List<FileVO> selectAll();
	public List<FileVO> findByPCode(String file_p_code);
	public FileVO findById(long id);
	
	public int save(FileVO fileVO);
	
	// PK로 검색한 파일 1개 삭제
	public int delete(long id);
	// 상품코드와 연결된 모든 파일 삭제
	public int delete(String file_p_code);
	
}
