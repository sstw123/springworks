package com.mycompany.bucket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.bucket.mapper.BucketDao;
import com.mycompany.bucket.model.BucketDTO;
import com.mycompany.bucket.model.PaginationDTO;

@Service
public class BucketService {
	
	@Autowired
	BucketDao bucketDao;
	
	public List<BucketDTO> selectAll() {
		return bucketDao.selectAll();
	}
	
	public List<BucketDTO> selectAllByPage(PaginationDTO pageDTO) {
		return bucketDao.selectAllByPage(pageDTO);
	}
	
	public int countAll() {
		return bucketDao.countAll();
	}

	public int save(BucketDTO bucketDTO) {
		int result = 0;
		
		// 받은 DTO에 id값이 없다면(=insert라면)
		if(bucketDTO.getB_id() == 0) {
			
			//1. b_order 생성
			Integer order = bucketDao.getMaxOrder();
			//1-1. DB에 아무 값도 없다면 1로, 하나라도 있다면 기존의 order+1로 생성
			if(order == null) {
				order = 1;
			} else {
				++order;
			}
			//생성한 order값 저장
			bucketDTO.setB_order(order);
			//2. b_success는 false로 저장
			bucketDTO.setB_success(false);
			//3. 세팅한 DTO로 insert 실행
			result = bucketDao.insert(bucketDTO);
		} else {
			// 받은 DTO에 id값이 있다면(=update라면) 기존 값 가져와서 content만 변경한 뒤 update 실행
			BucketDTO newBucketDTO = this.findById(bucketDTO.getB_id());
			newBucketDTO.setB_content(bucketDTO.getB_content());
			result = bucketDao.update(newBucketDTO);
		}
		
		return result;
	}

	public BucketDTO findById(int b_id) {
		return bucketDao.findById(b_id);
	}

	public int scUpdate(int b_id) {
		BucketDTO bucketDTO = this.findById(b_id);
		
		bucketDTO.setB_success(!bucketDTO.isB_success());
		
		return bucketDao.update(bucketDTO);
	}
	
	public void delete(int b_id) {
		bucketDao.delete(b_id);
		bucketDao.updateOrder(b_id);
	}

	public List<BucketDTO> selectSuccessTrue() {
		return bucketDao.selectSuccessTrue();
	}
	
	public List<BucketDTO> selectSuccessFalse() {
		return bucketDao.selectSuccessFalse();
	}

}
