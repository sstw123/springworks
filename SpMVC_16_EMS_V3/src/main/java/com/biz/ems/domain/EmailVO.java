package com.biz.ems.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Nationalized;

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
	//length 지정하지 않으면 기본값은 255가 된다
	//@Nationalized 지정하지 않으면 기본값은 varchar가 된다
	
	@Column(name="from_email", nullable = false, length = 20)
	private String from_email;
	
	@Column(nullable = false, name="to_email", length = 125)
	private String to_email;
	
	@Column(nullable = true, name="from_name", length = 125)
	@Nationalized
	private String from_name;
	
	@Column(nullable = true, length = 125)
	@Nationalized
	private String to_name;
	
	@Column(nullable = false, length = 255)
	@Nationalized
	private String subject;
	
	@Column(nullable = true, length = 1000)
	@Nationalized
	private String content;
	
	@Column(nullable = true, name="send_date")
	private String send_date;
	
	@Column(nullable = true, name="send_time")
	private String send_time;

}
