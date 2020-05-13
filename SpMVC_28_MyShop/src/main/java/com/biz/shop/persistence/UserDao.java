package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.UserDetails;

import com.biz.shop.domain.UserDetailsVO;

public interface UserDao {
	
	@Select("SELECT * FROM tbl_users")
	public List<UserDetailsVO> selectAll();

	public UserDetails findByUsername(String username);
	
	@Select("${create_table_query}")
	public void create_table(String create_table_query);
}
