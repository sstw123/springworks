package com.biz.shop.persistence.sql;

import org.apache.ibatis.jdbc.SQL;

// SQL : MyBatis 3.5 이상에서 사용하는 동적 쿼리 작성 클래스
public class UserSQL {
	
	public String selectAll() {
		SQL sql = new SQL();
		
		sql
		.SELECT("*")
		.FROM("tbl_users");
		
		return sql.toString();
	}
	
	public String findByUsername() {
		SQL sql = new SQL();
		
		sql
		.SELECT("*")
		.FROM("tbl_users")
		.WHERE("username = #{username}");
		
		return sql.toString();
	}

}
