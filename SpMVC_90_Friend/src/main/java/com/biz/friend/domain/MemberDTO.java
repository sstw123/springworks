package com.biz.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	
	private String m_id;//	varchar(20)	not null	primary key
	private String m_pw;//	varchar(125)	not null	
	private String m_name;//	VARCHAR(20)
	private String m_nick;//	VARCHAR(20)

}
