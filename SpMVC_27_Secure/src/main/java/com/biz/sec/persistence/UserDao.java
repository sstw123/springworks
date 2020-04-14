package com.biz.sec.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.sec.domain.UserDetailsVO;
import com.biz.sec.domain.UserVO;

public interface UserDao {
	
	public List<UserVO> selectAll();
	
	public UserDetailsVO findById(long id);
	
	public void create_table(String create_table);
	
	// VO는 username으로 설계해놓았기 때문에 AS를 이용해서 DB 테이블의 user_name과 매핑될 수 있도록 설정해준다 
	@Select("SELECT user_name AS username, "
			+ " user_pass AS password, "
			+ " enabled, "
			+ " email, phone, address "
			+ " FROM tbl_users WHERE user_name = #{username}")
	public UserDetailsVO findByUserName(String username);
	
	public UserDetailsVO findByUsernameAuthorities(String username);
	
	public int insert(UserDetailsVO userVO);
	
	public int update(UserDetailsVO userVO);

}
