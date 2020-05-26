package com.biz.shop.service;

import org.springframework.stereotype.Service;

import com.biz.shop.persistence.DDL_Dao;
import com.biz.shop.persistence.sql.CreateTableSQL;

@Service
public class DdlService {
	
	private final DDL_Dao ddlDao;
	
	public DdlService(DDL_Dao ddlDao) {
		this.ddlDao = ddlDao;
		
		ddlDao.create_table(CreateTableSQL.create_tbl_product);
		ddlDao.create_table(CreateTableSQL.create_tbl_file);
		ddlDao.create_table(CreateTableSQL.create_tbl_product_size);
		ddlDao.create_table(CreateTableSQL.create_tbl_product_color);
		ddlDao.create_table(CreateTableSQL.create_tbl_options);
		ddlDao.create_table(CreateTableSQL.delete_tbl_options);
		ddlDao.create_table(CreateTableSQL.insert_tbl_options);
	}

}
