package com.biz.ems.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class NaverTokenVO {
	
	private String access_token;//네이버로부터 발급받은 token
	private String refresh_token;//(선택) 네이버에 token을 재발급 요청했을 때 받는 token
	private String error;//(선택) 오류가 발생했을 때
	private String error_description;//(선택) 오류가 발생했을 때
	private String token_type;//토큰타입
	private int expires_in;//유효기간
	
	private String grant_type;
	private String client_id;
	private String client_secret;
	private String code;
	private String state;
	
	private String service_provider;

}
