package com.biz.shop.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
// src/main/resources 폴더의 *.properties 파일을 읽어서 사용하도록 설정하기
@PropertySource(value = "classpath:db.connection2.properties")
public class DBSetupConfig {
	
	// @PropertySource로 불러온 파일 내용 변수명에 저장되어있는 값 불러오기
	@Value(value = "${mysql.username}")
	private String mysqlUsername;
	
	@Value(value = "${mysql.password}")
	private String mysqlPassword;
	
	@Autowired
	private StandardPBEStringEncryptor stringEncryptor;
	
	@Bean
	public BasicDataSource ds() {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/myshop?serverTimezone=Asia/Seoul");
		ds.setUsername(stringEncryptor.decrypt(mysqlUsername));
		ds.setPassword(stringEncryptor.decrypt(mysqlPassword));
		
		log.debug("USER NAME : " + stringEncryptor.decrypt(mysqlUsername));
		
		return ds;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		SqlSessionFactoryBean ssfb = new SqlSessionFactoryBean();
		ssfb.setDataSource(this.ds());
		return ssfb;
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dstm = new DataSourceTransactionManager();
		dstm.setDataSource(this.ds());
		return dstm;
	}

}
