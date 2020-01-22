package com.biz.product.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.product.domain.ProductFileDTO;

public interface ProductFileDao {
	
	public List<ProductFileDTO> fileList(List<ProductFileDTO> fileList);
	
	// Mapper 개별 Insert
	public void fileInsert(ProductFileDTO file);
	
	// Mapper 전체 Insert
	public void filesInsert(@Param("files") List<ProductFileDTO> files, @Param("p_code") String p_code);
	
	// 파일을 개별적으로 삭제
	public void fileDelete(long file_seq);
	
	// 다수의 파일 삭제
	// 여러 개의 파일을 선택한 후 삭제
	public void filesDelete(List<ProductFileDTO> files);
	
	/*
	 * 상품정보가 삭제되었을 때
	 * 상품테이블과 파일테이블을 외래키로 설정해두면
	 * 상품테이블의 레코드가 삭제될 때 파일 테이블의 정보를 삭제할 수 있다
	 * 
	 * 여러개의 파일의 경우는 특별한 상황이 아니면 파일 정보를 update 하는 것은 거의 없다
	 * (일반적으로 전부 삭제 후 다시 업로드) 
	 */
	
}
