package com.biz.memo.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.memo.domain.UserDTO;

public interface UserDao {
	
	public List<UserDTO> selectAll();

	public int userInsert(UserDTO userDTO);
	
	public int userCount();
	
	public String userLoginCheck(UserDTO userDTO);
	
	public UserDTO selectById(@Param("u_id") String u_id);

}
