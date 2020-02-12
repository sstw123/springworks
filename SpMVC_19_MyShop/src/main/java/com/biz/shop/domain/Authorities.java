package com.biz.shop.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * spring security에서 사용하는 권한 정보 테이블
 * username에게 권한을 부여할 수 있다
 * authority 칼럼의 문자열을 참조하여 expression을 사용한 세부적인 권한 부여 가능
 * ADMIN, USER, MANAGER 등의 문자열을 저장하고
 * hasRole('ADMIN'), hasRole('USER'), hasRole('MANAGER') 형식으로 로그인 된 사용자의 권한을 참조할 수 있다
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name="authorities", schema="emsDB")
public class Authorities {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;
	private String username;
	private String authority;

}
