package com.biz.shop.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.biz.shop.domain.FileVO;
import com.biz.shop.persistence.FileUploadDao;
import com.biz.shop.service.FileUploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// 상품정보 파일 업로드 서비스
@Slf4j
@RequiredArgsConstructor
@Service
public class FileUploadServiceImpl implements FileUploadService {
	
	private final FileUploadDao fileDao;
	private final String filePath;
	
	public String fileUpload(MultipartFile file) {
		log.debug("파일저장경로: {}", filePath);
		File dir = new File(filePath);
		if( !dir.exists() ) {
			//dir.mkdir();// filePath = c:/bizwork/upload를 만들때 upload 폴더만 만들기
			dir.mkdirs();//  filePath = c:/bizwork/upload를 만들때 앞까지 전부 만들기(만약 앞이 전부 있다면 생략하고 upload만 만들기)
		}
		
		// 업로드 할 원본 파일명
		String originName = file.getOriginalFilename();
		String uuid = UUID.randomUUID().toString();
		String saveName = uuid + "_" + originName;
		
		File uploadFile = new File(filePath, saveName);
		try {
			// file 객체의 transferTo() 메소드를 사용하여 업로드하기
			file.transferTo(uploadFile);
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return saveName;
	}
	
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
	public int save(FileVO fileVO) {
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
