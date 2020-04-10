package com.biz.sec.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Spring Security와 연동하여 회원정보를 관리하기 위한 UserDetailsVO는 일반적으로 단독으로 작성하지 않고
 UserDetails라는 인터페이스를 implements하여 작성한다
 
 @update 2020-04-10
 User 클래스를 extends 하여 만들었던 UserVO를
 UserDetails 인터페이스를 implements 한 UserDetailsVO로 변경
 
 UserDetails를 implements하면 method를 Override해야한다
 하지만 method를 Override하지 않고 필드변수들을 선언하고 lombok을 이용하여 getter를 선언해준다
   
 여기서 만든 UserDetailsVO는 Spring Security와 연동하여 사용자 정보를 관리할 클래스가 되고
 필요에 따라서 UserVO와 연동하여 데이터를 주고받기를 수행한다
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsVO implements UserDetails {

	/**
	 VO 객체를 map에 담아서 req, res에 담아서 보낼 때 객체를 문자열형으로 변환하는 과정이 있다
	 이 과정을 serialize라고 하는데, 각 변환된 문자열이 서로 흐트러지지 않도록 설정하는 키 값이 serialVersionUID 이다
	 */
	private static final long serialVersionUID = 1L;
	
	// 이하 add unimplemented method를 하는 대신
	// lombok을 사용하여 getter를 생성하여 사용하는 방법
	private String username;
	private String password;
	private boolean enabled;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	private Collection<? extends GrantedAuthority> authorities;

}
