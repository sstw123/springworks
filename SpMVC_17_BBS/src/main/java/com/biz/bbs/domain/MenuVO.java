package com.biz.bbs.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuVO{
	
	private String menu_id;//	varchar(3)
	private String menu_p_id;//	varchar(3)
	private String menu_title;//	varchar(10)
	private String menu_href;//	varchar(10)
	
	// drop down을 구현하기 위해서 자기자신을 List로 설정
	List<MenuVO> drop_menus;

}
