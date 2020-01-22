package com.biz.gallery.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
	
	protected String filePath;

	@Autowired
	public FileService(String filePath) {
		super();
		this.filePath = filePath;
	}


	public String file_up(MultipartFile multiFile) {
		if(multiFile == null) {
			return null;
		}
		
		// 폴더 객체 생성
		File dir = new File(filePath);
		
		if(!dir.exists()) {
			
//			c:/bizwork/files
//			bizwork 폴더가 있고, files 폴더만 없을 때는 dir.mkdir() 사용
//			bizwork 폴더가 없고, files 폴더를 찾을 수 없을 때 dir.mkdirs()를 사용하여 모든 경로를 생성
			dir.mkdirs();
		}
		
		String strUUID = UUID.randomUUID().toString();
		
		String originalName = multiFile.getOriginalFilename();
		
		String upLoadFileName = strUUID + originalName;
		
		// 업로드 할 파일 객체 생성
		File upLoadFile = new File(filePath, upLoadFileName);
		
		try {
			multiFile.transferTo(upLoadFile);
			
			return upLoadFileName;
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return null;
		}
		
	}


	public void file_delete(String img_file) {
		
		/*
		 * filePath : /bizwork/files/
		 * img_file : aaa.jpg
		 * 라면
		 * file은 /bizwork/files/aaa.jpg 형식으로 생성된다
		 */
		File file = new File(filePath, img_file);
		
		if(file.exists()) {
			file.delete();
		}
		
	}
	
}
