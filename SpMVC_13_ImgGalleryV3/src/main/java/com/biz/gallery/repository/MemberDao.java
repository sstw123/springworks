package com.biz.gallery.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.biz.gallery.domain.MemberVO;

public interface MemberDao {
	
	@Select("select count(*) from tbl_user")
	public int memberCount();
	
	@Select("select * from tbl_user where u_id = #{u_id}")
	public MemberVO selectById(String u_id);
	
	@Insert("insert into tbl_user (u_id, u_pw, u_name, u_grade) values (#{u_id}, #{u_pw}, #{u_name,jdbcType=VARCHAR}, #{u_grade})")
	public int insert(MemberVO memberVO);

}
