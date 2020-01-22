package com.biz.gallery.service;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.biz.gallery.domain.ImageFilesVO;
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
	protected final ImageDao imgDao;
	protected final FileService fileSvc;
	protected final ImageFileService ifSvc;
	
	@Autowired
	public ImageService(ImageDao imgDao, FileService fileSvc, ImageFileService ifSvc) {
		super();
		this.imgDao = imgDao;
		this.fileSvc = fileSvc;
		this.ifSvc = ifSvc;
	}
	// 생성자 끝

	public List<ImageVO> selectAll() {
		return imgDao.selectAll();
	}
	
	/*
	 * 일반적으로 Dao.insert(VO)를 호출했을 때
	 * VO에 담아서 전달한 값은 insert()가 수행된 후에 볼 수 있으나
	 * seq처럼 SQL에서 생성된 값은 확인 불가능
	 * 그런데 이 값을 insert() 후에 사용해야 할 경우가 있다
	 * 이 때는 Dao나 Mapper에서 SelectKey를 사용해서 값을 생성하면 insert() 후에 그 값을 사용할 수 있다
	 */
	public int insert(ImageVO imageVO) {
		
		List<String> fileList = imageVO.getImg_files();
		if(fileList != null && fileList.size() > 0) {
//			파일 리스트에서 0번째 파일명을 대표파일명으로 설정
			imageVO.setImg_file(imageVO.getImg_files().get(0));
		}
		
		// 1. tbl_gallery에 데이터 insert
		int ret = imgDao.insert(imageVO);
		
		// 2. 파일이름들을 ImageFilesVO의 리스트에 생성
		// ImageFilesVO에 img_file_p_code 칼럼에 tbl_gallery의 seq 값을 추가해서 리스트 생성
		List<ImageFilesVO> ifList = new ArrayList<ImageFilesVO>();
		
		if(fileList != null) {
			for(String fileName : fileList) {
				ifList.add(
					ImageFilesVO.builder()
					.img_file_upload_name(fileName)
					.img_file_p_code(imageVO.getImg_seq())
					.build()
				);
			}
			// 3. 파일정보를 tbl_images에 insert
			ifSvc.imageFileInsert(ifList);
		}
		
		log.debug(imageVO.toString());
		return ret;
	}

	public ImageVO selectBySeq(String img_seq) {
		return imgDao.selectBySeq(img_seq);
	}

	// 파일이 변경되면 기존 파일을 먼저 제거하고 새로운 파일로 등록하기
	public int update(ImageVO imageVO) {

		ImageVO oldImageVO = imgDao.selectBySeq(imageVO.getImg_seq() + "");
		
		// 기존 이미지 파일명과 새로운 이미지 파일명이 null이 아닐 경우
		if(oldImageVO.getImg_file() != null && imageVO.getImg_file() != null) {
			// 새로운 imageVO에 담긴 파일명이
			// seq로 검색한 기존 테이블의 파일명과 다르면
			// 먼저 이미지파일 삭제하기
			if( !oldImageVO.getImg_file().equals( imageVO.getImg_file() ) ) {
				fileSvc.file_delete(oldImageVO.getImg_file());
			}
		}
		
		return imgDao.update(imageVO);
	}

	public int delete(String img_seq) {
		ImageVO imageVO = imgDao.selectBySeq(img_seq);
		
		if(imageVO.getImg_file() != null) {
			fileSvc.file_delete(imageVO.getImg_file());
		}
		
		return imgDao.delete(img_seq);
	}
	
	public List<String> files_up(MultipartHttpServletRequest mFiles) {
		List<String> fileNames = new ArrayList<String>();// 파일명들을 담을 List 선언
		
		// MultipartHttpServletRequest.getFiles() 수만큼 file 업로드하고
		// return 받은 이름을 List<String>에 add
		for(MultipartFile file : mFiles.getFiles("files")) {
			fileNames.add(fileSvc.file_up(file));
		}
		
		return fileNames;
		
	}
	
}
