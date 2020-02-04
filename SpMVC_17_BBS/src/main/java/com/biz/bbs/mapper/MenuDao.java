package com.biz.bbs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.MenuVO;

public interface MenuDao {
	
	@Select("select * from tbl_menu where menu_p_id is null order by menu_id")//1.select
	@Results(value = {
				@Result(property = "menu_id", column="menu_id"),
				@Result(property = "drop_menus", column="menu_id", javaType=List.class, many=@Many(select="getSubMenu"))
	})//2.select 결과의 테이블 데이터로 results 연산
	public List<MenuVO> getAllMenu();
	
	@Select("select * from tbl_menu where menu_p_id = #{menu_id}")
	public List<MenuVO> getSubMenu(String menu_id);

}
