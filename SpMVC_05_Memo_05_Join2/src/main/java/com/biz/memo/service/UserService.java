package com.biz.memo.service;

import com.biz.memo.domain.UserDTO;

public interface UserService {
	
	/*
	 * 회원가입 처리
	 * 회원가입처리를 할 때
	 * 최초의 가입을 할 때는 그 사용자는 관리자로 만들기(=테이블에 회원 정보가 아무 것도 없는 상태)
	 * 회원가입하면 해당 사용자의 u_grade 칼럼을 "A"로 설정, 그 이후 일반사용자는 등급을 "U"로 설정
	 */
	public int userJoin(UserDTO userDTO);
	
	/*
	 * id 중복검사
	 * 새로 회원가입할 때 입력한 id가 기존 table에 저장되어 있는지 검사
	 * u_id를 매개변수로 받아서 table에서 회원 id를 검사한 후
	 * 이미 해당 id가 테이블에 있으면 true, 없으면 false 리턴
	 */
	public boolean userIdCheck(String u_id);
	
	/*
	 * 로그인 처리
	 * 회원 id와 pw를 전달받아서 정상적인 정보인지 검사
	 * 회원 id와 비밀번호를 매개변수로 받아서 해당 정보와 일치하는(id and 비밀번호) 값이 있으면 true 리턴
	 * 그렇지 않으면 id가 없거나 비밀번호가 틀리면 false 리턴
	 */
	public boolean userLoginCheck(UserDTO userDTO);

	public UserDTO getUser(String u_id);

}
