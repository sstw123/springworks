package com.biz.product.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.product.domain.PageDTO;
import com.biz.product.domain.ProductDTO;
import com.biz.product.domain.ProductFileDTO;
import com.biz.product.persistence.ProductFileDao;
import com.biz.product.persistence.ProductDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	@Autowired
	SqlSession sqlSession;
	
	ProductDao proDao;
	ProductFileDao fileDao;
	
	@Autowired
	FileService fSvc;
	
	@Autowired
	public void newDao() {
		proDao = sqlSession.getMapper(ProductDao.class);
		fileDao = sqlSession.getMapper(ProductFileDao.class);
	}
	
	public long totalCount() {
		return proDao.proTotalCount();
	}
	
	public List<ProductDTO> selectPagination(PageDTO pageDTO) {
		List<ProductDTO> proList = proDao.selectPagination(pageDTO);
		return proList;
	}
	
	public ProductDTO selectByPCode(String p_code) {
		return proDao.selectByPCode(p_code);
	}
	
	public List<ProductDTO> selectByPName(String p_name) {
		return proDao.selectByPName(p_name);
	}

	public List<ProductDTO> selectAll() {
		return proDao.selectAll();
	}

	public int insert(ProductDTO proDTO, List<ProductFileDTO> upFileList) {
		String p_code = proDao.selectMaxPCode();

		// 만약 상품테이블에 데이터가 하나도 없을 경우, 새로 추가할 때
		// intSuffixCode를 1로, p_prefixCode를 P로 세팅한 후 진행
		int intSuffixCode = 1;
		String p_prefixCode = "P";
		try {
			p_prefixCode = p_code.substring(0,1);
			String p_suffixCode = p_code.substring(1);
			intSuffixCode = Integer.valueOf(p_suffixCode) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String strPCode = String.format("%s%04d", p_prefixCode, intSuffixCode);
		proDTO.setP_code(strPCode);

		// 파일명 리스트에 상품코드를 등록해서 두 테이블을 연관시키기 위한 메소드 
		if(upFileList != null) {
			// 상품정보에 등록할 p_code를 파일정보(ProductFileDTO)에 업데이트
			int size = upFileList.size();
			fileDao.filesInsert(upFileList,p_code);
			
			/*
			 * 파일의 개수만큼 tbl_files에 insert를 수행해야 하는데
			 * mybatis의 foreach를 활용한 동적쿼리를 작성하여
			 * 한번의 connection으로 다수의 레코드에 insert를 수행한다 
			 */
			
			/*
			for(int i = 0; i < size; i++) {
				upFileList.get(i).setFile_p_code(strPCode);
				log.debug("파일정보 : " + upFileList.get(i).toString());
				
				// 파일정보를 1개씩 DBMS에 insert 수행하기
				// fileDao.fileInsert(upFileList.get(i));
			}
			*/
			
			//fileDao.filesInsert(upFileList);
		}
		
		return proDao.insert(proDTO);
	}
	
	public int update(ProductDTO proDTO) {
		return proDao.update(proDTO);
	}

	public int update(ProductDTO proDTO, List<ProductFileDTO> upFileList) {
		
		if(upFileList != null) {
			String p_code = proDTO.getP_code();
			fileDao.filesInsert(upFileList, p_code);
		}
		return proDao.update(proDTO);
	}

	// sub file 삭제
	// 1. 이미지 파일을 삭제하고 (tbl_files에서 file_seq에 해당하는 파일정보 가져오기)
	// 2. 이미지 파일이 삭제가 완료되면 table에서 파일 정보를 제거 (tbl_product에서 해당 )
	public String subImgDelete(String file_seq) {
		
		ProductFileDTO pfDTO = fileDao.selectByFileSeq(file_seq);
		
		String file_name = pfDTO.getFile_upload_name();// 파일이름
		String p_code = pfDTO.getFile_p_code();// 상품코드
		
		// tbl_product에서 파일명 제거
		fSvc.fileDelete(file_name);
		
		//
		long long_file_seq = Long.valueOf(file_seq);
		fileDao.fileDelete(long_file_seq);
		
		return p_code;
		
	}

	// 상품코드를 매개변수로 받아서 대표이미지와 이미지 정보를 제거
	public void proImgDelete(String p_code) {
		
		ProductDTO proDTO = this.selectByPCode(p_code);
		if(proDTO != null && proDTO.getP_file() != null && !proDTO.getP_file().isEmpty()) {
			fSvc.fileDelete(proDTO.getP_file());
			proDTO.setP_file(null);
			this.update(proDTO);
		}
	}

}