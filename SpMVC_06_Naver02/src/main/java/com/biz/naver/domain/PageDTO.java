package com.biz.naver.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * pagenation의 여러가지 설정 값을 저장,변경하여
 * 쉽게 페이지 리스트를 그릴 수 있는 정보를 저장할 칼럼
 * 
 * 전체 데이터 개수 : 페이지 수 등을 계산
 * 
 * 현재 페이지에 보여질 레코드의 개수 
 * 현재 페이지 하단에 보여질 페이지의 개수
 * 
 * 보여지는 페이지의 끝 번호 : 계산 후
 * 보여지는 페이지의 첫 번호 : 계산 후
 * 
 * 처음 페이지 : "처음"으로 가기를 클릭했을 때 페이지 번호(1페이지)
 * 끝 페이지 : "끝"으로 가기를 클릭했을 때(전체 데이터 개수 등으로 계산)
 * 
 * 이전 페이지
 * 이후 페이지
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class PageDTO {
	long totalCount;// 전체 데이터 개수
	
	long listPerPage;// 한 페이지에 보일 레코드의 개수
	long pageCount;// 현재 페이지 하단에 보여질 페이지 번호 개수
	
	long firstPageNo;// 전체 데이터의 첫 페이지 번호(일반적으로 1)
	long lastPageNo;// 전체 데이터의 끝 페이지 번호(계산결과)
	
	long startPageNo;// 보여지는 리스트의 시작페이지 번호
	long prePageNo;// 이전 페이지
	long nextPageNo;// 다음 페이지
	long endPageNo ;// 보여지는 리스트의 끝 번호
	
	long currentPageNo;// 현재 페이지 번호
	
}
