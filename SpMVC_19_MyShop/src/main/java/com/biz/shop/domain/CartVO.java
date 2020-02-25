package com.biz.shop.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 장바구니
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Entity
@Table(name = "tbl_cart", schema = "emsDB")
public class CartVO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long seq;
	
	@Column
	private String username;
	
	@Column
	private String p_code;
	
	@Transient//VO에서만 사용하고 테이블에 칼럼으로 사용 X
	private String p_name;
	
	@Column
	private int p_oprice;
	
	@Column
	private int p_qty;
	
	@Column(length = 10)
	private String p_status;
	
	
	@Transient
	private List<CartVO> cartVOList;

}
