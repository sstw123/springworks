package com.biz.product.service;

import org.springframework.stereotype.Service;

import com.biz.product.domain.PageDTO;

import lombok.extern.slf4j.Slf4j;

/*
 * totalCount 값과 currentPageNo값으로
 * pagination에 사용할 여러 변수 값들을 생성
 */
@Slf4j
@Service
public class PageService {
	
	/*
	 * pagination을 시작할때 한 페이지에 보여줄 기본값을 설정
	 */
	private int listPerPage = 10;
	private int pageCount = 5;
	
	
	// pagination의 기본값을 변경하고자 할 때 사용하는 setter 메소드
	public void setListPerPage(int listPerPage) {
		this.listPerPage = listPerPage;
	}
	
	// pagination의 기본값을 변경하고자 할 때 사용하는 setter 메소드
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public PageDTO getPagination(long totalCount, int currentPageNo) {
		
		if(totalCount < 1) {
			return null;
		}
		
		int finalPageNo = ( (int)totalCount + listPerPage - 1 ) / listPerPage;
		
		if(currentPageNo > finalPageNo) currentPageNo = finalPageNo;
		if(currentPageNo < 1) currentPageNo = 1;
		
		int startPageNo = ((currentPageNo - 1)/ pageCount) * pageCount + 1 ;
		
		// 10, 20, 30으로 설정하기
		int endPageNo = startPageNo + pageCount - 1;
		if(endPageNo > finalPageNo) endPageNo = finalPageNo;
		
		int prePageNo = 1;
		if(currentPageNo > 1) prePageNo = currentPageNo - 1;
		
		int nextPageNo = finalPageNo;
		if(currentPageNo < finalPageNo) nextPageNo = currentPageNo + 1;
		
		// DB에서 데이터 가져올 값 설정
		// 1페이지 선택시:1, 2페이지 선택시:11, 3페이지 선택시:21
		int offset = (currentPageNo - 1) * listPerPage + 1;
		int limit = offset + listPerPage - 1;
		
		PageDTO pageDTO = PageDTO.builder()
				.totalCount(totalCount)
				.pageCount(pageCount)
				
				.listPerPage(listPerPage)
				.offset(offset)
				.limit(limit)
				
				.firstPageNo(1)
				.finalPageNo(finalPageNo)
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				
				.prePageNo(prePageNo)
				.nextPageNo(nextPageNo)
				
				.currentPageNo(currentPageNo)
				.build();
		
		return pageDTO;
	}

}