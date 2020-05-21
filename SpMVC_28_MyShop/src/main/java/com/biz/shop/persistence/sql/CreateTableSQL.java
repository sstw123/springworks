package com.biz.shop.persistence.sql;

public class CreateTableSQL {
	
	public static String create_tbl_product
		= " CREATE TABLE IF NOT EXISTS tbl_product ( "
		+ " p_code VARCHAR(6) PRIMARY KEY, "
		+ " p_name VARCHAR(125), "
		+ " p_bcode VARCHAR(6), "
		+ " p_dcode VARCHAR(6), "
		+ " p_iprice INT, "
		+ " p_oprice INT, "
		+ " p_vat BOOLEAN DEFAULT true, "
		+ " p_file VARCHAR(255) "
		+ " ) "
	;
	
	public static String create_tbl_file
		= " CREATE TABLE IF NOT EXISTS tbl_file ( "
		+ " id BIGINT PRIMARY KEY AUTO_INCREMENT, "
		+ " file_p_code VARCHAR(6), "
		+ " file_origin_name VARCHAR(255), "
		+ " file_upload_name VARCHAR(255), "
		+ " CONSTRAINT FK_PRODUCT_FILE_file_p_code "
		+ " FOREIGN KEY (file_p_code) "
		+ " REFERENCES tbl_product(p_code) "
		+ " ON DELETE CASCADE "
		+ " ) "
	;
	
	public static String create_tbl_product_size
		= " CREATE TABLE IF NOT EXISTS tbl_product_size ( "
		+ " s_seq BIGINT AUTO_INCREMENT PRIMARY KEY, "
		+ " p_code VARCHAR(6) NOT NULL, "
		+ " s_size VARCHAR(20) NOT NULL, "
		+ " CONSTRAINT FK_PRODUCT_SIZE_P_CODE "
		+ " FOREIGN KEY (p_code) "
		+ " REFERENCES tbl_product(p_code) "
		+ " ON DELETE CASCADE "
		+ " ) "
	;
	
	public static String create_tbl_product_color
		= " CREATE TABLE IF NOT EXISTS tbl_product_color ( "
		+ " c_seq BIGINT AUTO_INCREMENT PRIMARY KEY, "
		+ " s_seq BIGINT NOT NULL, "
		+ " c_color VARCHAR(20) NOT NULL, "
		+ " c_qty INT NOT NULL DEFAULT 0, "
		+ " CONSTRAINT FK_SIZE_COLOR_S_SEQ "
		+ " FOREIGN KEY (s_seq) "
		+ " REFERENCES tbl_product_size(s_seq) "
		+ " ON DELETE CASCADE "
		+ " ) "
	;

}