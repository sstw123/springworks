package com.biz.shop.domain;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

/*
 * spring security에서 DB를 연동하여 login 시
 * 사용자 정보를 저장할 table
 * 이 테이블의 enabled 칼럼이 false이면 해당 계정이 사용정지된 것으로 인식(회원탈퇴 등)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name="users", schema = "emsDB")
public class Users {
	
	@Id
	private String username;
	private String password;
	private boolean enabled;
	
}
