package com.biz.naver.service;

import org.springframework.stereotype.Service;

import com.biz.naver.domain.PageDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PageService {
	
	private long listPerPage = 10;// 리스트 개수
	private long pageCount = 5;// 페이지 개수
	
	public void setListPerPage(long listPerPage) {
		this.listPerPage = listPerPage;
	}
	
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	
	
	/*
	 * 페이지를 계산하기 위한 method
	 * 전체 데이터 개수와 현재 선택된 페이지 번호만 매개변수로 받아서
	 * 페이지 정보 만들기
	 */
	public PageDTO makePagination(long totalCount, long currentPageNo) {
		
		// 데이터가 없으면 중단
		if(totalCount < 1) return null;
		
		long lastPageNo = (totalCount + listPerPage - 1) / listPerPage;
		// lastPageNo : (전체 데이터 개수 + 페이지당 보여줄 데이터 개수 - 1 ) / 페이지당 보여줄 데이터 개수
		// 만약 lastPageNo = (totalCount/listPerPage) + 1로 만들면 13개/5개 같은 경우 3페이지가 되지만 15개/5개 같이 나누어 떨어질 때 의미없이 페이지가 +1 된다
		// 따라서 totalCount에 listPerPage-1을 추가해준 뒤 나누면 나누어 떨어지는 경우까지 정확히 원하는 값을 얻을 수 있다
		
		// naver는 페이지를 검색할 때 데이터가 1000개 초과되면 오류를 보낸다
//		lastPageNo = lastPageNo > 1000 ? 1000 : lastPageNo; // 3항연산자
//		if(lastPageNo > 1000) lastPageNo = 1000; // if문을 이용한 연산
		
		// currentPageNo(현재 보고 있는 페이지)가 마지막 페이지보다 크면
		if(currentPageNo > lastPageNo) currentPageNo = lastPageNo;
		
		// currentPageNo가 1보다 작으면
		if(currentPageNo < 1) currentPageNo = 1;
		
		// 이전페이지, 다음페이지를 계산하기 위해서
		// 현재 페이지 번호가 첫페이지인지, 마지막페이지인지 검사하고 flag를 세팅
		// bYes = 3 == 3;
		
		//boolean isNowFirst = currentPageNo == 1;// 현재페이지가 1이면 isNowFirst가 true
		//boolean isNowLast = currentPageNo == lastPageNo;// 현재페이지가 마지막페이지면 isNowLast가 true

		// int startPageNo = ((currentPageNo - 1)/ pageCount) * pageCount + 1 ;// 5~10 페이지처럼 pageCount 단위로 보여주기
		
		// 하단에 리스트로 보여줄 페이지 계산
		// currentPageNo가 3이면 1 ~ 5, currentPageNo가 10이면 8 ~ 12까지 이런 식으로 보여주기 위한 계산
		long startPageNo = (currentPageNo - pageCount / 2) < 1 ? 1 : (currentPageNo - pageCount / 2);
		// long startPageNo = ((currentPageNo - 1)/ pageCount) * pageCount + 1 ;// 5 ~ 10 페이지처럼 pageCount 단위로 보여주기
		
		long endPageNo = startPageNo + pageCount - 1;
		if(endPageNo > lastPageNo) endPageNo = lastPageNo;
		
		// 오라클 DB에서 데이터 가져올 값 설정
		// 1페이지 선택시:1번부터, 2페이지 선택시:11번부터, 3페이지 선택시:21번부터
		// long offset = (currentPageNo - 1) * listPerPage + 1;
		// long limit = offset + listPerPage - 1;
		
		
		PageDTO pageDTO = PageDTO.builder()
				.totalCount(totalCount)
				.pageCount(pageCount)
				.listPerPage(listPerPage)
				.firstPageNo(1)
				.lastPageNo(lastPageNo)
				.startPageNo(startPageNo)
				.endPageNo(endPageNo)
				.currentPageNo(currentPageNo)
				.build();
		
		pageDTO.setPrePageNo( (currentPageNo - 1) < 1 ? 1 : (currentPageNo - 1) );
		pageDTO.setNextPageNo( currentPageNo + 1 > lastPageNo ? lastPageNo : currentPageNo + 1 );
		
		return pageDTO;
	}

}
