package com.biz.crawl.service;

import org.springframework.stereotype.Service;

import com.biz.crawl.domain.CrawlDTO;
import com.biz.crawl.domain.PaginationDTO;

@Service
public class PaginationService {
	
	private int listPerPage = 10;// 한 페이지에 보여줄 데이터 개수
	private int pageCount = 10;// 한 번에 보여줄 페이지 (1 ~ 10)
	
	// pagination의 기본값을 변경하고자 할 때 사용하는 setter 메소드
	public void setListPerPage(int listPerPage) {
		this.listPerPage = listPerPage;
	}
	
	
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	// DB나 API에서 받아온 totalCount와 currentPageNo를 가지고 계산하기
	public PaginationDTO makePageInfo(long totalCount, int currPageNo) {
		
		if(totalCount < 1) {
			return null;// 데이터가 없으면 중단
		}
		
		int lastPageNo = (int)((totalCount + listPerPage - 1) / listPerPage);
//		lastPageNo : (전체 데이터 개수 + 페이지당 보여줄 데이터 개수 - 1 ) / 페이지당 보여줄 데이터 개수
//		만약 lastPageNo = (totalCount/listPerPage) + 1로 만들면 13개/5개 같은 경우 3페이지가 되지만 15개/5개 같이 나누어 떨어질 때 의미없이 페이지가 +1 된다
//		따라서 totalCount에 listPerPage-1을 추가해준 뒤 나누면 나누어 떨어지는 경우까지 정확히 원하는 값을 얻을 수 있다
		
//		현재 보고 있는 페이지가 마지막 페이지보다 크면
		if(currPageNo > lastPageNo) currPageNo = lastPageNo;
//		현재 보고 있는 페이지가 1 이하이면
		if(currPageNo < 1) currPageNo = 1;
		
//		currentPageNo가 3이면 1 ~ 5, currentPageNo가 10이면 8 ~ 12까지 이런 식으로 보여주기 위한 계산
		int startPageNo = 1;
		int midPage = currPageNo - pageCount / 2;
		if (midPage > 0) {			
			startPageNo = midPage;
		}
		
		int endPageNo = startPageNo + pageCount - 1;
		if(endPageNo > lastPageNo) endPageNo = lastPageNo;
		
		int prePageNo = 1;
		if(currPageNo > 1) prePageNo = currPageNo - 1;
		
		int nextPageNo = lastPageNo;
		if(currPageNo < lastPageNo) {
			nextPageNo = currPageNo + 1;
		}
		
		if(endPageNo - startPageNo + 1 < pageCount) {
			startPageNo = lastPageNo - pageCount + 1 > 0 ? lastPageNo - pageCount + 1 : 1; 
		}
		
		// DB에서 데이터 가져올 값 설정
		// 1페이지 선택시 offset:1,limit:10, 2페이지 선택시 offset:11,limit:20, 3페이지 선택시 offset:21,limit:30
		int offset = (currPageNo - 1) * listPerPage + 1;
		int limit = offset + listPerPage - 1;
		
		PaginationDTO paginationDTO = PaginationDTO.builder()
				.totalCount(totalCount)
				.listPerPage(listPerPage)
				.pageCount(pageCount)
				.offset(offset)
				.limit(limit)
				
				.firstPageNo(1)
				.lastPageNo(lastPageNo)
				
				.prePageNo(prePageNo)
				.nextPageNo(nextPageNo)
				
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				
				.currentPageNo(currPageNo)
				.build();
		
		return paginationDTO;
	}

}
