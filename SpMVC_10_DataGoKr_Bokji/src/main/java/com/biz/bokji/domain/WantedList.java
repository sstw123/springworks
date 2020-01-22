package com.biz.bokji.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

@XmlRootElement(name = "wantedList")
@ToString
public class WantedList {
	
	public String numOfRows;// 페이지 결과수
	public String pageNum;// 페이지번호
	public String totalCount;// 전체 데이터수
	
	/*
	 * <wantedList>
	 * 		<servList>
	 * 			...
	 * 		</servList>
	 * 
	 * 		<servList>
	 * 			...
	 * 		</servList>
	 * </wantedList>
	 */
	public List<BokListVO> servList;// 데이터 목록(서비스ID, 서비스명, ...)

}
