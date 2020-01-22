package com.biz.gallery.repository;

import org.apache.ibatis.jdbc.SQL;

public class ImageSQL {
	
	public String insert_sql() {
		
		return new SQL() {{
			INSERT_INTO("tbl_gallery");
			
			INTO_COLUMNS("img_seq");
			INTO_COLUMNS("img_title");
			INTO_COLUMNS("img_text");
			INTO_COLUMNS("img_file");
			
			// INTO_VALUES("seq_gallery.nextval");
			INTO_VALUES("#{img_seq,jdbcType=VARCHAR}");
			INTO_VALUES("#{img_title,jdbcType=VARCHAR}");
			INTO_VALUES("#{img_text,jdbcType=VARCHAR}");
			INTO_VALUES("#{img_file,jdbcType=VARCHAR}");
			
		}}.toString();
		
	}
	
	public String update_sql() {
		
		SQL sql = new SQL() {{
			
			UPDATE("tbl_gallery");
			WHERE("img_seq = #{img_seq,jdbcType=VARCHAR}"); // 참고로 SQL 클래스는 WHERE의 위치가 상관없다
			SET("img_title = #{img_title,jdbcType=VARCHAR}");
			SET("img_text = #{img_text,jdbcType=VARCHAR}");
			SET("img_file = #{img_file,jdbcType=VARCHAR}");
			
		}};
		
		return sql.toString();
		
	}

}
