package com.biz.ems.domain;

import javax.persistence.Column;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name="tbl_ems", schema="emsDB")
public class EmailVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ems_seq")//table의 칼럼명은 ems_seq로 지정하고 VO클래스의 필드변수는 emsSeq로 지정
	private long ems_seq;
	
	//보내는 email, 받는 email, 보내는 사람 이름, 받는 사람 이름, 제목, 내용, 작성일자, 보낸시간
	
	@Column(name="from_email", nullable = false, length = 20)
	private String from_email;
	
	@Column(nullable = false, name="to_email")
	private String to_email;
	
	@Column(nullable = true, name="from_name", columnDefinition = "nVARCHAR(20)")
	private String from_name;
	
	@Column(nullable = true, columnDefinition = "nVARCHAR(20)")
	private String to_name;
	
	@Column(nullable = false, columnDefinition = "nVARCHAR(100)")
	private String subject;
	
	@Column(nullable = true, columnDefinition = "nVARCHAR(1000)")
	private String content;
	
	@Column(nullable = true, name="send_date")
	private String send_date;
	
	@Column(nullable = true, name="send_time")
	private String send_time;

}
