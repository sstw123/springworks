package com.app.money.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PaginationDTO {
	
	private long totalCount;// 전체 데이터 개수
	
	private long listPerPage;// 한 페이지에 보일 데이터 개수
	private long pageCount;// 현재 페이지 하단에 보일 페이지 개수
	
	private long firstPageNo;// 전체 데이터의 첫 페이지 번호(일반적으로 1)
	private long lastPageNo;// 전체 데이터의 끝 페이지 번호
	
	private long prePageNo;// 이전 페이지 번호
	private long nextPageNo;// 다음 페이지 번호
	
	private long startPageNo;// 아래에서 5~10페이지를 보여준다고 할 때 5
	private long endPageNo;// 아래에서 5~10페이지를 보여준다고 할 때 10
	
	private long currentPageNo;// 현재 페이지 번호
	
	// 오라클 DB 사용시
	// private long offset;// 시작 레코드
	// private long limit;// 끝 레코드

}
