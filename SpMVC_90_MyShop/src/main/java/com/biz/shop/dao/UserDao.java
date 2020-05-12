package com.biz.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.shop.model.UserDetailsVO;

public interface UserDao {

	public List<UserDetailsVO> selectAll();
	
	public UserDetailsVO findById(long id);
	
	public void create_table(String create_table);
	
	public UserDetailsVO findByUsername(String username);
	
	public UserDetailsVO findByUsernameFromAuthorities(String username);
	
	public int insert(UserDetailsVO userVO);
	
	public int updateInfo(UserDetailsVO userVO);

	public int updatePW(UserDetailsVO userVO);
	
	@Select("SELECT username FROM tbl_users WHERE email = #{email}")
	public List<UserDetailsVO> findByEmail(String email);

}
