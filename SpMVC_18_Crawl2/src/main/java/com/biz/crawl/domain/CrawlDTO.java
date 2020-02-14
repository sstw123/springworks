package com.biz.crawl.domain;

import java.util.List;

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
public class CrawlDTO {
	
	// Read, Insert, Update, Delete
	private String c_site;
    private String c_board;
	
	// Insert, Update
	private String crawlSiteURL;
	private String nextPageSiteURL;
	private String bbsNoTag;
	private String dateTag;
	private String hitTag;
	private String crawlStartDate;
	
	// Read
    private String srchStartDate;
    private String srchLastDate;
    private int currPage;//pagination
    private PaginationDTO paginationDTO;//pagination
    private List<CrawlSubDTO> crawlSubList;
    
    // Read, View
    private long sumOfHit;//총 조회수
    private long avgOfHit;// 평균 조회수
    private int numOfTuples;// 게시물 수
    
}
