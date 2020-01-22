package com.biz.product.domain;

import org.apache.ibatis.type.Alias;

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
@Alias("ProductDTO")
public class ProductDTO {
	
	private String p_code;//	varchar2(5 byte)
	private String p_name;//	nvarchar2(50 char)
	private int p_iprice;//	number
	private int p_oprice;//	number
	private String p_vat;//	varchar2(1 byte)
	private String p_file;// nvarchar2(255 char)

}
