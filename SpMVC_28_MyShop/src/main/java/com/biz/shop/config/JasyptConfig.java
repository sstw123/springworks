package com.biz.shop.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasyptConfig {
	
	public static StandardPBEStringEncryptor pbe = new StandardPBEStringEncryptor();
	
	@Bean
	public EnvironmentStringPBEConfig envConfig() {
		EnvironmentStringPBEConfig envConfig = new EnvironmentStringPBEConfig();
		envConfig.setAlgorithm("PBEWithMD5AndDES");
		envConfig.setPasswordEnvName("ENV_PASS");
		return envConfig;
	}
	
	@Bean
	public StandardPBEStringEncryptor stringEncryptor() {
		StandardPBEStringEncryptor stringEnc = new StandardPBEStringEncryptor();
		stringEnc.setConfig(this.envConfig());
		return stringEnc;
	}
}
