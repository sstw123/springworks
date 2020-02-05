package com.biz.bbs.domain;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Alias("cmtVO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentVO {
	
	private long cmt_id;//	bigint	not null	primary key
	private long cmt_p_id;//	bigint	not null	
	private String cmt_writer;//	varchar(20)	not null	
	private String cmt_date;//	varchar(10)		
	private String cmt_time;//	varchar(10)		
	private String cmt_text;//	varchar(400)		


}
