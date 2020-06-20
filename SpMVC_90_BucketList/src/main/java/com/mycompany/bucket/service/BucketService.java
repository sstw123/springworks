package com.mycompany.bucket.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.bucket.mapper.BucketDao;
import com.mycompany.bucket.model.BucketDTO;
import com.mycompany.bucket.model.PaginationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	
	public int orderChange(int b_id, byte order) {
		int ret = 0;
		int count = bucketDao.countAll();
		int max_b_order = bucketDao.getMaxOrder();
		
		BucketDTO bucketDTO = bucketDao.findById(b_id);
		int b_order = bucketDTO.getB_order();
		
		// bucketDao.orderChange(a, b) : a의 순서는 -1, b의 순서는 +1
		// bucketDao.orderChange(3, 2) => 2, 3
		if(order == 1 && b_order > 1) {
			// ▲ 클릭 시(order==1) 현재 tr의 b_order값이 처음 데이터(=1)면 아무 행동 안함
			// 처음 데이터가 아닌 경우 이전 순서와 변경 (ex: 클릭한 tr이 3번이라면 2번이랑 바꾸기)
			ret = bucketDao.orderChange(b_order, b_order-1);
		} else if(order == -1 && count > 1 && b_order != max_b_order){
			// ▼ 클릭 시(order==-1) 전체 데이터 개수가 1 이하면 아무 행동 안함, 현재 tr의 b_order가 마지막 데이터(b_order == maxOrder)라면 아무 행동 안함
			// 마지막 데이터가 아닌 경우 다음 순서와 변경 (ex: 클릭한 tr이 3번이라면 4번이랑 바꾸기)
			ret = bucketDao.orderChange(b_order+1, b_order);
		}
		log.debug("b_id : " + b_id + ", Order : " + order + ", b_order : " + b_order);
		
		return ret;
	}
	
	public void delete(int b_id) {
		BucketDTO bucketDTO = bucketDao.findById(b_id);
		
		// 삭제한 id의 순서값이 가장 마지막 순서가 아닐 때만 -1 하기
		if(bucketDao.getMaxOrder() != bucketDTO.getB_order()) {
			bucketDao.orderMinusOne(bucketDTO.getB_order());
		}
		bucketDao.delete(b_id);
	}

	// 스마트폰 최적화 어플이기 때문에 화면이 작아서 체크박스 일부러 미구현
	// 한번에 삭제할 때 쓰려고 만든 메소드(사용하지 않음)
	public void deleteMany(int[] arr_b_id) {
		BucketDTO bucketDTO = null;
		
		for(int i = 0 ; i < arr_b_id.length ; i++) {
			bucketDTO = bucketDao.findById(arr_b_id[i]);
			if(bucketDao.getMaxOrder() != bucketDTO.getB_order()) {
				bucketDao.orderMinusOne(bucketDTO.getB_order());
			}
			bucketDao.delete(arr_b_id[i]);
		} 
	}

	public List<BucketDTO> selectSuccessTrue() {
		return bucketDao.selectSuccessTrue();
	}
	
	public List<BucketDTO> selectSuccessFalse() {
		return bucketDao.selectSuccessFalse();
	}

}
