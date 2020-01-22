package com.biz.product.domain;

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
public class PageDTO {
	
	private long totalCount;
	
	/* pagination 시작레코드와 끝레코드 */
	private int offset;
	private int limit;
	
	private int listPerPage;// 한 페이지에 보여질 데이터 개수
	private int pageCount;// 화면 하단에 보여질 페이지 리스트 개수
	
	private int firstPageNo;// 전체 데이터의 첫번째 페이지
	private int finalPageNo;// 전체 데이터의 마지막 페이지
	
	private int prePageNo;// 이전 페이지 번호
	private int nextPageNo;// 다음 페이지 번호
	
	private int startPageNo;// 화면에 보여질 시작페이지 번호
	private int endPageNo;// 화면에 보여질 끝페이지 번호
	
	private int currentPageNo;// 현재 선택된 페이지
	
}
