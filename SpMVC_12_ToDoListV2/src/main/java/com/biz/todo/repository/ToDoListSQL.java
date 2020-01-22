package com.biz.todo.repository;

import org.apache.ibatis.jdbc.SQL;

import com.biz.todo.domain.ToDoList;

public class ToDoListSQL {
	
	public final static String complete_sql =
			" update TBL_TODOLIST "
			+ " set TD_COMPLETE = DECODE(td_complete,'Y','N','Y') "
			+ " where TD_SEQ = #{td_seq} ";
	
	// 매개변수는 설정하지 않아도 상관없지만
	// 동적쿼리들을 이용하기 위해 매개변수를 설정할 경우 final로 선언
	public String insert_sql(final ToDoList toDoList) {
		// 익명(무명)클래스 builder 패턴 방식
		return new SQL() {{
			INSERT_INTO("tbl_todolist");
			
			INTO_COLUMNS("td_seq").INTO_VALUES("SEQ_TODO.NEXTVAL");
			INTO_COLUMNS("TD_DATE").INTO_VALUES("#{td_date,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_TIME").INTO_VALUES("#{td_time,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_SUB").INTO_VALUES("#{td_sub,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_TEXT").INTO_VALUES("#{td_text,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_FLAG").INTO_VALUES("#{td_flag,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_COMPLETE").INTO_VALUES("#{td_complete,jdbcType=VARCHAR}");
			INTO_COLUMNS("TD_ALARM").INTO_VALUES("#{td_alarm,jdbcType=VARCHAR}");
			
		}}.toString();
		
	}
	
	/*
	 * SQL class
	 * mybatis 3.4(3.5) 이상에서 사용되는 동적 쿼리를 지원하는 class
	 */
	public String alarm_sql() {
		SQL sql = new SQL();
		sql.UPDATE("tbl_todolist");
		sql.SET("td_alarm = DECODE(td_alarm,'Y','N','Y')");
		sql.WHERE("td_seq = #{td_seq,jdbcType=VARCHAR}");
		
		return sql.toString();
	}
	
	public String update_sql() {
		return new SQL() {{
			
			UPDATE("tbl_todolist");
			
			SET("td_date = #{td_date,jdbcType=VARCHAR}");
			SET("td_time = #{td_time,jdbcType=VARCHAR}");
			SET("td_sub = #{td_sub,jdbcType=VARCHAR}");
			SET("td_text = #{td_text,jdbcType=VARCHAR}");
			SET("td_flag = #{td_flag,jdbcType=VARCHAR}");
			SET("td_complete = #{td_complete,jdbcType=VARCHAR}");
			SET("td_alarm = #{td_alarm,jdbcType=VARCHAR}");
			
			WHERE("td_seq = #{td_seq,jdbcType=VARCHAR}");
			
		}}.toString();
	}

}
