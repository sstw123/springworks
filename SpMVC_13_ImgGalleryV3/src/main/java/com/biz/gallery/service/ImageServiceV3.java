package com.biz.gallery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.domain.ImageVO;
import com.biz.gallery.repository.ImageDao;

@Service
public class ImageServiceV3 extends ImageServiceV2 {
	
	@Autowired
	public ImageServiceV3(ImageDao imgDao, FileService fileSvc, ImageFileService ifSvc) {
		super(imgDao, fileSvc, ifSvc);
	}

	@Override
	public int insert(ImageVO imageVO) {
		
		imageVO = this.updateImageFiles(imageVO);
		
		int ret = imageDao.insert(imageVO);
		
		ret += ifSvc.imageFileInsert(imageVO.getImg_up_files(), imageVO.getImg_seq());
		
		return ret;
	}

	@Override
	public int update(ImageVO imageVO) {
		imageVO = this.updateImageFiles(imageVO);
		
		int ret = imageDao.update(imageVO);
		
		ret += ifSvc.imageFileInsert(imageVO.getImg_up_files(), imageVO.getImg_seq());
		
		return ret;
	}
	
	protected ImageVO updateImageFiles (ImageVO imageVO) {
		List<ImageFileVO> ifList = imageVO.getImg_up_files();
		
		if(ifList != null && ifList.size() > 0) {
			imageVO.setImg_file(ifList.get(0).getImg_file_upload_name());
			
			for(ImageFileVO ifVO : ifList) {
				ifVO.setImg_file_origin_name(ifVO.getImg_file_upload_name().substring(36));
			}
		}
		
		return imageVO;
	}
	
}
