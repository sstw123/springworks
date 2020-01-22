package com.app.money.domain;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.ToString;

@XmlRootElement(name = "wantedList")
@ToString
public class ResultWantedListVO {
	
	public String numOfRows;//	한페이지 결과수
	public String pageNum;//	페이지번호
	public String totalCount;//	전체 데이터수
	public List<ResultServListVO> servList;//	데이터 목록

}
