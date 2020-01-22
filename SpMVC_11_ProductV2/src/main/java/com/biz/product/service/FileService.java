package com.biz.product.service;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	@Autowired
	ServletContext context;
	
	private final String filesPath = "/bizwork/files";
	
	public String fileUp(MultipartFile u_file) {
		
		
		// 업로드 된 파일정보에서 파일명만 추출
		String originName = u_file.getOriginalFilename();
		
		// tomcat server가 작동되고 있는 곳에 대한 정보
		// getRealPath("/") : tomcat server가 우리 프로젝트를 서비스 할 때
		// root로 설정하여 여러가지 정보를 저장하고 있는 폴더정보
		String uploadPath = context.getRealPath("/");
		uploadPath += "files/"; // uploadPath : /product/files/ 폴더
		
		uploadPath = filesPath;
		
		if(u_file != null) {
			// /files/ 폴더에 대한 IO 정보를 추출
			File dir = new File(uploadPath);
			
			if(!dir.exists()) {
				// 현재 서버에 /files/라는 폴더가 없으면 폴더생성
				dir.mkdirs();
			}
			
			// original 파일명을 사용하면 파일명을 같이 하는 해킹의 위험이 있기 때문에
			// UUID를 생성하여 기존파일명에 덧붙이는 방식으로 DB table에 저장하기
			String strUUID = UUID.randomUUID().toString();
			strUUID += originName;
			
			// /product/files/ + 2019.jpg 라는 이름으로 파일명을 만들고
			// 해당하는 파일에 대한 정보를 객체로 생성
			File uploadFile = new File(uploadPath, strUUID);
			
			try {
				// web에서 upload된 파일(u_file)을 uploadFile 파일에 전송하기
				// web >> server로 파일이 복사된다
				// u_file을 uploadFile로 복사 및 저장하기
				u_file.transferTo(uploadFile);
				
				// 파일이 정상적으로 upload되면 originName을 Controller로 return하기
				return strUUID;
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
		}
		// 파일이 정상적으로 upload되지 않으면 null값을 Controller로 리턴
		return null;
	}
	
	public void fileDelete(String p_file) {
		File dFile = new File(filesPath, p_file);
		
		log.debug("삭제할 파일 : " + p_file);
		if(dFile.exists()) {
			boolean ok = dFile.delete();
			if(ok) {
				log.debug("파일 삭제 성공");
			} else {
				log.debug("파일 삭제 실패");
			}
		} else {
			log.debug("삭제할 파일이 없음");
		}
	}

}
