package com.biz.gallery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.repository.ImageFileDao;

@Service
public class ImageFileService {
	
	protected ImageFileDao ifDao;
	protected FileService fileSvc;
	
	@Autowired
	public ImageFileService(ImageFileDao ifDao, FileService fileSvc) {
		this.ifDao = ifDao;
		this.fileSvc = fileSvc;
	}
	
	public int imageFileInsert(List<ImageFileVO> ifList) {
		int ret = 0;
		for(ImageFileVO ifVO : ifList) {
			ret += ifDao.insert(ifVO);
		}
		
		return ret;
	}
	
	public int imageFileInsert(List<ImageFileVO> ifList, long img_p_code) {
		if(ifList == null) return -1;
		int ret = ifDao.bulk_insert(ifList, img_p_code);
		
		return ret;
	}

	public int deleteFile(String img_file_seq) {
		
		ImageFileVO ifVO = this.selectBySeq(img_file_seq);
		
		if(ifVO != null) {
			fileSvc.file_delete(ifVO.getImg_file_upload_name());
		}
		
		return ifDao.delete(Long.valueOf(img_file_seq));
	}

	public ImageFileVO selectBySeq(String img_file_seq) {
		return ifDao.selectBySeq(Long.valueOf(img_file_seq));
	}

}
