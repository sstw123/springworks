package com.biz.memo.domain;

import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class HobbyListDTO {
	
	private String h_code;//	VARCHAR2(5)		Primary Key,
	private String h_hobby;//	VARCHAR2(125)	NOT NULL,
	private String h_rem;//	VARCHAR2(125)

}
