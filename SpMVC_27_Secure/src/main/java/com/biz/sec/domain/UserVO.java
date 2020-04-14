package com.biz.sec.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// DB와 연동해서 순수한 CRUD를 수행하는 클래스
// 이 프로젝트에선 사용하지 않는다
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserVO {
	
	// 테이블의 칼럼명과 같도록
	// spring security에서 사용할 기본 칼럼
	private long id;
	private String username;
	private String password;
	private boolean enabled;
	
	private String email;
	private String phone;
	private String address;
	
}
