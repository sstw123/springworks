package com.biz.ems.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NaverMemberResponse {
	
	private String id;
	private String email;
	private String name;
	private String nickname;
	private String gender;
	private String age;
	private String birthday;
	private String profile_image;

}
