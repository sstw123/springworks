package com.biz.product.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.product.domain.ProductFileDTO;
import com.biz.product.persistence.ProductFileDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileService {
	
	@Autowired
	ServletContext context;
	
	// private final String filesPath = "/bizwork/files";
	
	@Autowired
	String winFilePath;
	
	@Autowired
	String macFilePath;
	
	String fileUploadPath;
	
	@Autowired
	SqlSession sqlSession;
	
	ProductFileDao fileDao;
	
	public void newDao() {
		this.fileDao = sqlSession.getMapper(ProductFileDao.class);
	}
	
	@Autowired
	public void fileUpPath() {
		
		this.fileUploadPath = this.macFilePath;
		
		// 만약 winFilePath가 존재한다면 (File 클래스 이용) fileUploadPath를 winFilePath로 변경하기
		// 그렇지 않으면 그대로 macFilePath를 업로드 폴더로 사용
		File path = new File(this.winFilePath);
		if(path.exists()) {
			this.fileUploadPath = this.winFilePath;
		}
	}
	
	/*
	 * 여러개의 파일들을 개별 파일로 분리하여 fileUp() 메소드에게 보내서 파일을 업로드하고
	 * fileUp()이 생성한 업로드 파일이름을 리턴받아서 List에 추가한 후
	 * 파일이름 List를 Controller로 리턴받기
	 */
	public List<ProductFileDTO> filesUp(MultipartHttpServletRequest u_files) {
		
		if(u_files.getFile("u_files").getSize() < 1) return null;
		
		List<ProductFileDTO> upFileList = new ArrayList<>();
		
		// 만약 개별파일 업로드 중 문제가 발생했다면 컨트롤러에게 null값을 리턴하여 DB에 저장하지 않도록 하기
		// 따라서 fileUp 메소드에 throws Exception을 붙여주자
		try {
			for(MultipartFile file : u_files.getFiles("u_files")) {
				
				// 파일을 업로드하고 리턴된 변화된 파일명을 upFileName에 저장하기
				String upFileName = this.fileUp(file);
				
				ProductFileDTO pfDTO = ProductFileDTO.builder()
						.file_origin_name(file.getOriginalFilename())
						.file_upload_name(upFileName)
						.build();
				
				upFileList.add(pfDTO);
			}
		} catch (Exception e) {
			log.debug("Exception : " + e.getMessage());
			return null;
		}
		
		return upFileList;
	}
	
	// 1. 하나의 파일을 서버 폴더에 업로드하고
	// 2. 변화된 파일명(UUID + originFileName)을 return
	public String fileUp(MultipartFile u_file) throws Exception {
		
		
		// 업로드 된 파일정보에서 파일명만 추출
		String originName = u_file.getOriginalFilename();
		
		// tomcat server가 작동되고 있는 곳에 대한 정보
		// getRealPath("/") : tomcat server가 우리 프로젝트를 서비스 할 때
		// root로 설정하여 여러가지 정보를 저장하고 있는 폴더정보
		// String uploadPath = context.getRealPath("/");
		// uploadPath += "files/";// uploadPath : /product/files/ 폴더
		
		if(u_file != null) {
			// /files/ 폴더에 대한 IO 정보를 추출
			File dir = new File(fileUploadPath);
			
			if(!dir.exists()) {
				
				if(dir.mkdirs()) {
					log.debug("폴더 생성 OK");
				} else {
					fileUploadPath = this.macFilePath;
				}
				
				// 현재 서버에 /files/라는 폴더가 없으면 폴더생성
				// dir.mkdirs();
			}
			
			// original 파일명을 사용하면 파일명을 같이 하는 해킹의 위험이 있기 때문에
			// UUID를 생성하여 기존파일명에 덧붙이는 방식으로 DB table에 저장하기
			String strUUID = UUID.randomUUID().toString();
			strUUID += originName;
			
			// File(매개변수1, 매개변수2) =
			// /product/files/ + 2019.jpg 라는 이름으로 파일명을 만들고
			// 해당하는 파일에 대한 정보를 객체로 생성
			File uploadFile = new File(fileUploadPath, strUUID);
			
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
		File dFile = new File(fileUploadPath, p_file);
		
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

	public ProductFileDTO selectByFileSeq(String file_seq) {
		return fileDao.selectByFileSeq(file_seq);
	}
	
	public List<ProductFileDTO> getFiles(String p_code) {
		return fileDao.selectByPCode(p_code);
	}

}