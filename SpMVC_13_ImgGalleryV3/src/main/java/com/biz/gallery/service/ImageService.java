package com.biz.gallery.service;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.domain.ImageVO;
import com.biz.gallery.repository.ImageDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageService {
	
	// setter 주입방식 : @Autowired 클래스 객체
	
	/*
		생성자 주입방식
		1. 사용하는 객체를 final로 선언이 가능해서 보호할 수 있고
		   혹시 모를 해킹에 의한 데이터 변조를 막을 수 있다
		2. 클래스의 교차참조(stack overflow)를 컴파일러 차원에서 방지할 수 있다
		
		코드가 다소 번잡할 수 있지만 setter 주입보다는 constructor(생성자) 주입방식을 사용하자
		참고로 이클립스에서는 문제가 없지만 inteliJ에서는 setter 주입방식을 사용하면 IDE가 경고를 보낸다
	*/
	protected final ImageDao imageDao;
	protected final FileService fileSvc;
	protected final ImageFileService ifSvc;
	
	@Autowired
	public ImageService(ImageDao imgDao, FileService fileSvc, ImageFileService ifSvc) {
		super();
		this.imageDao = imgDao;
		this.fileSvc = fileSvc;
		this.ifSvc = ifSvc;
	}
	// 생성자 끝

	public List<ImageVO> selectAll() {
		return imageDao.selectAll();
	}
	
	public int insert(ImageVO imageVO) {

		// 1. 파일명 리스트를 추출
		List<String> fileNameList = imageVO.getImg_file_upload_names();
		
		// 2. 리스트가 있는지 검사하여 flag 세팅
		boolean tfList = fileNameList != null && fileNameList.size() > 0;
		
		// 3. 추출한 파일명 리스트가 있는지 검사
		if(tfList) {
			// 4. 파일 리스트에서 0번째 파일명을 대표파일명으로 설정
			imageVO.setImg_file(imageVO.getImg_file_upload_names().get(0));
		}
		
		// 4. tbl_gallery에 데이터 insert
		int ret = imageDao.insert(imageVO);
		long img_p_code = imageVO.getImg_seq();
		
		// 5. ImageFilesVO 리스트 생성
		// ImageFileVO의 img_file_p_code 필드에 tbl_gallery의 seq 값을 추가해서 리스트 생성
		List<ImageFileVO> ifList = new ArrayList<ImageFileVO>();
		
		// 6. 리스트가 있는지 검사
		if(tfList) {
			
			// 7. 파일명을 ImageFile DB에 저장하기 위한 ImageFileVO 리스트 생성
			for(String fileName : fileNameList) {
				ifList.add(
					ImageFileVO.builder()
					.img_file_p_code(img_p_code)
					.img_file_upload_name(fileName)
					.img_file_origin_name(fileName.substring(36))
					.build()
				);
			}
			
			// 8. 파일정보(ImageFileVO)를 tbl_images에 bulk insert
			ifSvc.imageFileInsert(ifList, img_p_code);
		}
		
		return ret;
	}
	
	/*
	 * 일반적으로 Dao.insert(VO)를 호출했을 때
	 * VO에 담아서 전달한 값은 insert()가 수행된 후에 볼 수 있으나
	 * seq처럼 SQL에서 생성된 값은 확인 불가능
	 * 그런데 이 값을 insert() 후에 사용해야 할 경우가 있다
	 * 이 때는 Dao나 Mapper에서 SelectKey를 사용해서 값을 생성하면 insert() 후에 그 값을 사용할 수 있다
	 */
	public int insert(ImageVO imageVO, String dummy) {
		
		List<String> fileNameList = imageVO.getImg_file_upload_names();
		if(fileNameList != null && fileNameList.size() > 0) {
			// 파일 리스트에서 0번째 파일명을 대표파일명으로 설정
			imageVO.setImg_file(imageVO.getImg_file_upload_names().get(0));
		}
		
		// 1. tbl_gallery에 데이터 insert
		int ret = imageDao.insert(imageVO);
		
		// 2. 파일이름들을 ImageFilesVO의 리스트에 생성
		// ImageFilesVO에 img_file_p_code 칼럼에 tbl_gallery의 seq 값을 추가해서 리스트 생성
		List<ImageFileVO> ifList = new ArrayList<ImageFileVO>();
		
		if(fileNameList != null) {
			for(String fileName : fileNameList) {
				ifList.add(
					ImageFileVO.builder()
					.img_file_upload_name(fileName)
					.img_file_p_code(imageVO.getImg_seq())
					.build()
				);
			}
			// 3. 파일정보를 tbl_images에 insert
			ifSvc.imageFileInsert(ifList, imageVO.getImg_seq());
		}
		
		log.debug(imageVO.toString());
		return ret;
	}

	public ImageVO selectBySeq(String img_seq) {
		return imageDao.selectBySeq(img_seq);
	}

	// 파일이 변경되면 기존 파일을 먼저 제거하고 새로운 파일로 등록하기	
	public int update(ImageVO imageVO) {
		
		long img_seq = imageVO.getImg_seq();
		String img_file = imageVO.getImg_file();
		
		ImageVO imageVO2 = imageDao.selectBySeqAndFile(img_seq, img_file);
		
		if(imageVO2 == null) {
			ImageVO oldImageVO = imageDao.selectBySeq(img_seq + "");
			if(oldImageVO.getImg_file() != null) {
				fileSvc.file_delete(oldImageVO.getImg_file());
			}
		}
		
		List<String> fileNameList = imageVO.getImg_file_upload_names();
		if(fileNameList != null && fileNameList.size() > 0) {
			List<ImageFileVO> ifList = new ArrayList<>();
		
			for(String fileName : fileNameList) {
				
				ifList.add(
						ImageFileVO.builder()
						.img_file_origin_name(fileName.substring(36))
						.img_file_upload_name(fileName)
						.build()
				);
			}
			
			ifSvc.imageFileInsert(ifList, imageVO.getImg_seq());
			// imageVO.setImg_file(fileNameList.get(0));
		}
		
		return imageDao.update(imageVO);
	}

	public int delete(String img_seq) {
		ImageVO imageVO = imageDao.selectBySeq(img_seq);
		
		if(imageVO.getImg_file() != null) {
			fileSvc.file_delete(imageVO.getImg_file());
		}
		
		return imageDao.delete(img_seq);
	}
	
	public List<ImageFileVO> files_up(MultipartHttpServletRequest mFiles) {
		List<ImageFileVO> ifList = new ArrayList<>();// ImageFileVO들을 담을 List 선언
		
		// MultipartHttpServletRequest.getFiles() 수만큼 file 업로드하고
		// return 받은 이름을 List<String>에 add
		for(MultipartFile file : mFiles.getFiles("files")) {
			
			ImageFileVO ifVO = ImageFileVO.builder()
					.img_file_origin_name(file.getOriginalFilename())
					.img_file_upload_name(fileSvc.file_up(file))
					.build();
			
			ifList.add(ifVO);
		}
		
		return ifList;
	}
	
}