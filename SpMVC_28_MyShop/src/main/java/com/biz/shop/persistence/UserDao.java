package com.biz.shop.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.biz.shop.domain.UserDetailsVO;
import com.biz.shop.persistence.sql.UserSQL;

public interface UserDao {
	
	// @SelectProvider : SQL 클래스를 사용하여 작성된 동적 쿼리를 mapping
	// value : sql문을 작성한 클래스명.class
	// method : sql문을 작성한 메소드명
	@SelectProvider(value = UserSQL.class, method = "selectAll")
	public List<UserDetailsVO> selectAll();
	
	@SelectProvider(value = UserSQL.class, method = "findByUsername")
	public UserDetailsVO findByUsername(String username);
}
