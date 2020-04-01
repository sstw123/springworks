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
public class UserVO {
	
	private String userId;
	private String password;
	private String userName;
	private String role;
	
	// get으로 시작하는 method는 jackson이 필드변수의 getter 메소드로 인식하여 mapping하여 오류가 나게 된다
	// 따라서 아래와 같은 메소드는 getSampleVO()가 아닌 makeSampleVO() 처럼 명명하며
	// get이나 set으로 시작하지 않도록 한다
	public UserVO makeSampleVO() {
		UserVO userVO = UserVO.builder()
				.userId("admin")
				.password("12345")
				.userName("홍길동")
				.role("admin")
				.build();
		
		return userVO;
	}

}
