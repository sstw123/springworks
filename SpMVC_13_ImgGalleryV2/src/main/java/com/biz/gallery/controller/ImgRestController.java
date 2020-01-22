package com.biz.gallery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.service.FileService;
import com.biz.gallery.service.ImageFileService;

@RequestMapping("rest")
@RestController
public class ImgRestController {
	
	private final FileService fileSvc;
	private final ImageFileService ifSvc;
	
	@Autowired
	public ImgRestController(FileService fileSvc, ImageFileService ifSvc) {
		super();
		this.fileSvc = fileSvc;
		this.ifSvc = ifSvc;
	}

	@RequestMapping(value="file_up", method=RequestMethod.POST, produces="text/html; charset=UTF-8")
	public String file_up(@RequestParam("file") MultipartFile upFiles) {
		
		String upLoadFileName = fileSvc.file_up(upFiles);
		
		if(upLoadFileName == null) return "FAIL";
		else return upLoadFileName;
	}
	
	@RequestMapping(value="/image_delete", method=RequestMethod.POST)
	public String img_delete(@RequestParam("img_file_seq") String img_file_seq) {
		
		ImageFileVO ifVO = ifSvc.selectBySeq(img_file_seq);
		int ret = ifSvc.deleteFile(img_file_seq);
		if(ret > 0) return "OK";
		else return "FAIL";
	}
	
}