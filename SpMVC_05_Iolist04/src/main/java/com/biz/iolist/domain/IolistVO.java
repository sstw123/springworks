package com.biz.iolist.domain;

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
public class IolistVO {
	
	private long io_seq;//	number
	private String io_date;//	varchar2(10)
	private String io_dcode;//	varchar2(5)
	private String io_dname;//	nvarchar2(50)
	private String io_dceo;//	nvarchar2(50)
	private String io_pcode;//	varchar2(5)
	private String io_pname;//	nvarchar2(50)
	private int io_piprice;//	number
	private int io_poprice;//	number
	private String io_pvat;//	varchar2(1)
	private String io_inout;//	nvarchar2(2)
	private int io_qty;//	number
	private int io_price;//	number
	private long io_total;//	number

}
