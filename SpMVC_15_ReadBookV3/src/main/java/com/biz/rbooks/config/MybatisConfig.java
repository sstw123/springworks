package com.biz.rbooks.config;


import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("com.biz.rbooks.repository")
public class MybatisConfig {
	
	@Bean
	public DataSource oracleDS() {
		
		BasicDataSource bds = new BasicDataSource();
		bds.setDriverClassName("oracle.jdbc.OracleDriver");
		bds.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
		bds.setUsername("bookuser");
		bds.setPassword("bookuser");
		
		return bds;
	}
	
	@Bean
	public SqlSessionFactoryBean ssfb() {
		SqlSessionFactoryBean sb = new SqlSessionFactoryBean();
		sb.setDataSource(this.oracleDS());
		sb.setTypeAliasesPackage("com.biz.rbooks.domain");
		
		return sb;
	}
	
	
	@Bean
	public DataSourceTransactionManager tm() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager(this.oracleDS());
		// 생성자에 바로 등록 : <constructor-arg />로 등록한 것
		
		return tm;
	}

}
