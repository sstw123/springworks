package com.biz.gdata.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * Json 데이터를 변환하는 용도로 사용할 VO 클래스
 * 변수 이름은 Json 데이터의 칼럼과 같도록 해야 한다
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CityVO {
	
	private String code;
	private String name;
	private String rnum;

}
