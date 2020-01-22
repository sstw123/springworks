package com.biz.gallery.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.gallery.domain.ImageFileVO;
import com.biz.gallery.domain.ImageVO;
import com.biz.gallery.repository.ImageDao;

@Service("ImageServiceV2")
public class ImageServiceV2 extends ImageService {

	@Autowired
	public ImageServiceV2(ImageDao imgDao, FileService fileSvc, ImageFileService ifSvc) {
		super(imgDao, fileSvc, ifSvc);
	}
	
	@Override
	public int insert(ImageVO imageVO) {
		List<ImageFileVO> ifList = imageVO.getImg_files();
		
		if(ifList != null && ifList.size() > 0) {
			imageVO.setImg_file(ifList.get(0).getImg_file_upload_name());
			
			for(ImageFileVO ifVO : ifList) {
				ifVO.setImg_file_origin_name(ifVO.getImg_file_upload_name().substring(36));
			}
		}
		
		imageDao.insert(imageVO);
		long img_p_code = imageVO.getImg_seq();
		
		int ret = ifSvc.imageFileInsert(ifList, img_p_code);
		
		return ret;
	}

	public int update(ImageVO imageVO) {
		List<String> fileNameList = imageVO.getImg_file_upload_names();
		
		if(fileNameList != null && fileNameList.size() > 0) {
			imageVO.setImg_file(fileNameList.get(0));
			
			List<ImageFileVO> ifList = new ArrayList<>();
			
			for(String fileName : fileNameList) {
				ifList.add(
							ImageFileVO.builder()
							.img_file_origin_name(fileName.substring(36))
							.img_file_upload_name(fileName)
							.img_file_p_code(imageVO.getImg_seq())
							.build()
						);
			}
			
			ifSvc.imageFileInsert(ifList, imageVO.getImg_seq());
		}
		
		int ret = imageDao.update(imageVO);
		
		return 0;
	}

}
