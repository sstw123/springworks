package com.biz.crawl.domain;

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
public class CrawlSubDTO {
	
	private String c_id;//int auto_increment primary key
	private String c_site;// varchar(125)
	private String c_board;// varchar(125)
	private String c_bbsNo;// varchar(30)
    private String c_date;// varchar(20)
    private int c_hit;// int

}
