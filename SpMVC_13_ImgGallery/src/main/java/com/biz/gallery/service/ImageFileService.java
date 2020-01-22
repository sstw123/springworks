package com.biz.gallery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.gallery.domain.ImageFilesVO;
import com.biz.gallery.repository.ImageFilesDao;

@Service
public class ImageFileService {
	
	protected ImageFilesDao ifDao;
	
	@Autowired
	public ImageFileService(ImageFilesDao ifDao) {
		this.ifDao = ifDao;
	}
	
	public int imageFileInsert(List<ImageFilesVO> ifList) {
		int ret = 0;
		for(ImageFilesVO ifVO : ifList) {
			ret += ifDao.insert(ifVO);
		}
		
		return ret;
	}
	

}
