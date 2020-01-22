package com.app.money.service;

import org.springframework.stereotype.Service;

import com.app.money.domain.PaginationDTO;

@Service
public class PaginationService {
	
	private long listPerPage = 10;// 한 페이지에 보여줄 데이터 개수
	private long pageCount = 10;// 한 번에 보여줄 페이지 (1 ~ 10)
	
	public String getListPerPage() {
		return this.listPerPage + "";
	}
	
	public void setListPerPage(long listPerPage) {
		this.listPerPage = listPerPage;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	// DB나 API에서 받아온 totalCount와 currentPageNo를 가지고 계산하기
	public PaginationDTO pagination(long totalCount, long currentPageNo) {
		
		if(totalCount < 1) return null;// 데이터가 없으면 중단
		long lastPageNo = (totalCount + listPerPage - 1) / listPerPage;
//		lastPageNo : (전체 데이터 개수 + 페이지당 보여줄 데이터 개수 - 1 ) / 페이지당 보여줄 데이터 개수
//		만약 lastPageNo = (totalCount/listPerPage) + 1로 만들면 13개/5개 같은 경우 3페이지가 되지만 15개/5개 같이 나누어 떨어질 때 의미없이 페이지가 +1 된다
//		따라서 totalCount에 listPerPage-1을 추가해준 뒤 나누면 나누어 떨어지는 경우까지 정확히 원하는 값을 얻을 수 있다
		
//		현재 보고 있는 페이지가 마지막 페이지보다 크면
		if(currentPageNo > lastPageNo) currentPageNo = lastPageNo;
//		현재 보고 있는 페이지가 1 이하이면
		if(currentPageNo < 1) currentPageNo = 1;
		
//		currentPageNo가 3이면 1 ~ 5, currentPageNo가 10이면 8 ~ 12까지 이런 식으로 보여주기 위한 계산
		long startPageNo = 1;
		long midPage = currentPageNo - pageCount / 2;
		if (midPage > 0) {			
			startPageNo = midPage;
		}
		
		long endPageNo = startPageNo + pageCount - 1;
		if(endPageNo > lastPageNo) endPageNo = lastPageNo;
		
		long prePageNo = 1;
		if(currentPageNo > 1) prePageNo = currentPageNo - 1;
		
		long nextPageNo = lastPageNo;
		if(currentPageNo < lastPageNo) {
			nextPageNo = currentPageNo + 1;
		}
		
		if(endPageNo - startPageNo + 1 < pageCount) {
			startPageNo = lastPageNo - pageCount + 1 > 0 ? lastPageNo - pageCount + 1 : 1; 
		}
		
		PaginationDTO pagiDTO = PaginationDTO.builder()
				.totalCount(totalCount)
				.listPerPage(listPerPage)
				.pageCount(pageCount)
				.firstPageNo(1)
				.lastPageNo(lastPageNo)
				.prePageNo(prePageNo)
				.nextPageNo(nextPageNo)
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				.currentPageNo(currentPageNo)
				.build();
		
		return pagiDTO;
	}

}
