package com.biz.memo.persistence;

import java.util.List;

import com.biz.memo.domain.HobbyListDTO;
import com.biz.memo.domain.UserDTO;
import com.biz.memo.domain.UserHobbyDTO;

public interface UserDao {
	
	public List<HobbyListDTO> selectAllHobby();
	
	public int userCount();

	public int insertUser(UserDTO userDTO);

	public void insertUHobby(UserHobbyDTO uhDTO);

}
