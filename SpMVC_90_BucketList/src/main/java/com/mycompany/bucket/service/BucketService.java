package com.mycompany.bucket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.bucket.mapper.BucketDao;
import com.mycompany.bucket.model.BucketDTO;

@Service
public class BucketService {
	
	@Autowired
	BucketDao bucketDao;
	
	public List<BucketDTO> selectAll() {
		return bucketDao.selectAll();
	}

	public int save(BucketDTO bucketDTO) {
		int result = 0;
		
		Integer order = bucketDao.getMaxOrder();
		if(order == null) {
			order = 1;
		} else {
			++order;
		}
		
		if(bucketDTO.getB_id() == 0) {
			bucketDTO.setB_order(order);
			bucketDTO.setB_success(false);
			result = bucketDao.insert(bucketDTO);
		} else {
			result = bucketDao.update(bucketDTO);
		}
		
		return result;
	}

	public BucketDTO findById(int b_id) {
		return bucketDao.findById(b_id);
	}

	public int scUpdate(BucketDTO bucketDTO) {
		bucketDTO.setB_success(!bucketDTO.isB_success());
		
		return bucketDao.update(bucketDTO);
	}

	public void delete(int b_id) {
		bucketDao.delete(b_id);
		bucketDao.updateOrder(b_id);
	}

}
