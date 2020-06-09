package com.biz.shop.domain;

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
public class ProductColorVO {
	
	private long c_seq;
	private long s_seq;
	private String c_color;
	private int c_qty;
	
	// tbl_options 테이블과 조인하여 size 이름을 가져오기 위한 서버 변수
	private String o_name;

}
