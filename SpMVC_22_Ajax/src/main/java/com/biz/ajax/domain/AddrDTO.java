package com.biz.ajax.domain;

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
public class AddrDTO {

	private String a_name;
	private String a_addr;
	private String a_tel;
	private int a_age;
	
}
