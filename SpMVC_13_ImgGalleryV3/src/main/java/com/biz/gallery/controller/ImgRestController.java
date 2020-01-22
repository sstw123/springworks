package com.biz.gallery.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.domain.ImageVO;
import com.biz.gallery.repository.ImageDao;
import com.biz.gallery.repository.ImageFileDao;
import com.biz.gallery.service.FileService;
import com.biz.gallery.service.ImageFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("rest")
@RestController
public class ImgRestController {
	
	private final FileService fileSvc;
	private final ImageFileService ifSvc;
	private final ImageDao imageDao;
	private final ImageFileDao ifDao;
	
	@Autowired
	public ImgRestController(FileService fileSvc, ImageFileService ifSvc, ImageDao imageDao, ImageFileDao ifDao) {
		super();
		this.fileSvc = fileSvc;
		this.ifSvc = ifSvc;
		this.imageDao = imageDao;
		this.ifDao = ifDao;
	}

	@RequestMapping(value="file_up", method=RequestMethod.POST, produces="text/html; charset=UTF-8")
	public String file_up(@RequestParam("file") MultipartFile upFiles) {
		
		String upLoadFileName = fileSvc.file_up(upFiles);
		
		if(upLoadFileName == null) return "FAIL";
		else return upLoadFileName;
	}
	
	/*
	 * File Download
	 * 1. 단순히 파일을 클릭했을 때 링크를 주고 다운로드 받는 방법(별도의 코드 구현 필요X)
	 * 		파일명이 서버에 저장된 파일명 그대로 다운로드가 이루어지고
	 * 		서버에 대한 정보가 노출되기 쉽다
	 * 
	 * 2. response에 파일을 실어서 보내고
	 * 		web client 입장에서는 Http 프로토콜의 body에 실려오는 데이터를 수신하는 형태
	 * 		실제로 서버에 저장된 파일을 노출시키지 않더라도 파일을 다운로드 할 수 있다
	 * 		이미지 이외의 파일일 경우 감춰진 폴더에 저장해두었다가
	 * 		별도의 다운로드 기능을 구현하여 다운받을 수 있도록 하는 방법
	 */
	@RequestMapping(value="file_down/{img_file_seq}", method=RequestMethod.GET)
	public String file_down(@PathVariable("img_file_seq") String img_file_seq, HttpServletRequest request, HttpServletResponse response) {
		// 1. img_file_seq 값으로 다운로드를 수행할 파일정보를 DB로부터 추출
		ImageFileVO ifVO = ifDao.selectBySeq(Long.valueOf(img_file_seq));
		
		// 2. 서버에 저장된 파일명 가져오기
		String downFileName = ifVO.getImg_file_upload_name();
		
		// 3. 파일명과 서버에 저장된 path 정보 연결
		File downFile = fileSvc.file_down(downFileName);
		if(downFile == null) return "FILE_NOT_FOUND";
		
		// 4. 실제 업로드전 원래 이름으로 다운로드할 수 있도록 준비
		String fileOriginName = ifVO.getImg_file_origin_name();
		if(fileOriginName == null || fileOriginName.isEmpty()) {
			fileOriginName = "noname.file";
		}
		
		// download를 요청한 브라우저의 종류 가져오기
		String browser = request.getHeader("User-Agent");
		
		try {
			if(browser.contains("MSIE") || browser.contains("Chrome") || browser.contains("Trident")) {
				fileOriginName = URLEncoder.encode(fileOriginName, "UTF-8")// 브라우저가 위의 셋 중 하나라면 먼저 UTF-8로 인코딩한 뒤
						.replaceAll("\\+", "%20");// +기호가 있다면 %20(공백 퍼센트 문자열)으로
			} else {
				fileOriginName = new String(fileOriginName.getBytes("UTF-8"), "ISO-8850-1");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.debug("파일명 인코딩 중 오류발생");
		}
		
		// response에 파일을 싣기 위한 설정
		response.setContentType("application/octer-stream");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		// 파일을 보낼때 원래 이름으로 보이도록 만드는 설정
		fileOriginName = String.format("attachment;filename=%s", fileOriginName);
		response.setHeader("Content-Disposition", fileOriginName);
		
		try {
			// 통로 열기
			OutputStream os = response.getOutputStream();
			
			// 서버에 저장된 파일 읽어오기
			FileInputStream fs = new FileInputStream(downFile);
			
			int nCount = 0;
			byte[] sendData = new byte[512];// 보통 512 또는 1024정도, 크기가 크면 속도는 빠르지만 네트워크 환경이 불안정하면 파일이 손실될 수 있다
			
			while(true) {
				nCount = fs.read(sendData);
				if(nCount == -1) break;
				
				os.write(sendData, 0, nCount);
				// 0부터 512개씩 데이터를 다 보내고 한 5byte정도가 남았다면 nCount는 5가 된다
				// 그때는 0~5까지 보내고 nCount가 -1이 된다면 break
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("다운로드 중 오류 발생");
		}
		
		return "OK";
	}
	
	@RequestMapping(value="/image_delete", method=RequestMethod.POST)
	public String img_delete(@RequestParam("img_file_seq") String img_file_seq) {
		
		ImageFileVO ifVO = ifSvc.selectBySeq(img_file_seq);
		int ret = ifSvc.deleteFile(img_file_seq);
		if(ret > 0) return "OK";
		else return "FAIL";
	}
	
	@RequestMapping(value="main_image", method=RequestMethod.POST)
	public String main_image(@RequestParam("img_seq") String img_seq, @RequestParam("img_fileName")String img_fileName) {
		
		ImageVO imageVO = imageDao.selectBySeq(img_seq);
		imageVO.setImg_file(img_fileName);
		int ret = imageDao.update(imageVO);
		
		return ret + "";
	}
}