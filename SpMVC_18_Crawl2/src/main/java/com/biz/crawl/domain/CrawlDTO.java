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
	
	private String crawlSiteURL;
	private String nextPageSiteURL;
	private String bbsNoTag;
	private String dateTag;
	private String hitTag;
    private String srchStartDate;
    private String srchLastDate;
    
    private long sumOfHit;
    private long avgOfHit;
    private int numOfTuples;
    private String c_site;
    private String c_board;
    private List<CrawlSubDTO> crawlSubList;

}
