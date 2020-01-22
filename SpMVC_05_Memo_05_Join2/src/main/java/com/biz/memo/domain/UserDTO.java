package com.biz.memo.domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.ScriptAssert;

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
@ScriptAssert(lang="javascript", script="(_this.u_pw == _this.pw_check)", reportOn = "pw_check", message = "비밀번호가 다릅니다")
public class UserDTO {
	/*
	 * @Email : email 형식 검사
	 * @NotBlank : 공백 검사
	 * @NotNull : null이 아닐 경우만 정상
	 * @Null : null일 경우만 정상
	 * @Max(x), @Min(x) : 숫자의 최대값, 최소값 제한 검사
	 * @Size(min=x,max=x) : 문자열일 경우
	 * @DecimalMax(x) : x값 이하의 실수
	 * @DecimalMin(x) : x값 이상의 실수
	 * @Digits(integer=x) : x 자릿수 이상의 정수
	 * @Digits(integer=x, fraction=y) : x 자릿수 이하의 정수이면서 y 자릿수 이하의 소수점 자리수
	 */
	@NotNull @NotBlank(message="공백이면 안됨")
	@Email(message="(임의메세지)이메일 형식으로만 기입!")
	@Size(min=5, max=100)
	private String u_id;
	private String u_pw;
	@NotBlank(message="* 닉네임은 비어있으면 안 됨")
	private String u_nick;
	private String u_name;
	// 정규형 표현식
	@Pattern(regexp = "\\d{1,15}", message="1~15자리 까지의 숫자만 가능")
	private String u_tel;
	private String u_grade;
	private String pw_check;

}
