package com.biz.shop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * @Email : email 형식
 * @NotBlank, @NotNull, @NotEmpty : 공백형식
 * @Null : null인 경우만 통과
 * @Size(min, max)
 * @Max()
 * @Min()
 * @DecimalMax(x) : x값 이하의 실수
 * @DecimalMin(x) : x값 이상의 실수
 * @Digits(정수) : 정수 자릿수 검사
 * @Digits(숫자, fraction=x) : 숫자 자릿수 이하이면서 소수점 y자릿수 이하
 * @Pattern(regexp = "\\d[1-15]") : 1부터 15까지의 숫자만 가능
 * @Pattern(regexp = "\\d[30-55]") : 30 ~ 55 숫자만 가능
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "tbl_product", schema = "emsDB")
public class ProductVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="p_id")
	private long id;
	
	// 입력값이 공백일 경우 error
	@NotBlank(message = "* 상품코드는 공백이 될수 없습니다")
	@NotEmpty
	@NotNull
	// 문자열의 길이가 맞지 않을 경우
	// Size(min, max)
	// Min(), Max()
	// @Max(13)
	@Size(max = 13, message = "* 상품코드는 13자리 이하만 가능합니다")
	@Column(name="p_code",
		length = 13, // 최대 13자리
		unique = true, // UNIQUE
		nullable = false) // NOT NULL
	private String p_code;
	
	// @Pattern(regexp = "/(?=.*\d{1,50})(?=.*[~`!@#$%\^&*()-+=]{1,50})(?=.*[a-zA-Z]{2,50}).{8,50}$/")
	// 정규식 문법, 비밀번호 확인 정규식
	// @Email : 문자열이 email 형식과 같은지 검사
	
	@NotBlank(message = "* 상품명은 공백이 될수 없습니다")
	@Column(name="p_name")
	private String p_name;
	
	@Column(name="p_bcode", length = 5)
	private String p_bcode;
	
	@Column(name="p_dcode", length = 5)
	private String p_dcode;
	
	@Column(name="p_iprice")
	private int p_iprice;
	
	@Column(name="p_oprice")
	private int p_oprice;
	
	@Column(name="p_detail")
	@Type(type = "text")
	private String p_detail;

}
