package com.biz.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FriendDTO {
	
	private int frd_id;//	bigint(20)
	private String frd_name;//	varchar(30)
	private String frd_tel;//	varchar(13)
	private String frd_addr;//	varchar(125)
	private String frd_hobby;//	varchar(8)
	private String frd_rel;//	varchar(30)

}
