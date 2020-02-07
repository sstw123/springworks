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
public class NaverMovieVO {
	
	private long id;// BIGINT PRIMARY KEY AUTO_INCREMENT,
    private int m_rank;// INT UNIQUE,
    private String m_title;// VARCHAR(125),
    private String m_detail_url;// VARCHAR(255),
    private String m_image_url;// VARCHAR(255)

}
