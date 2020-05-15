package com.biz.shop.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class EncMySQL {
	
	public static void main(String[] args) throws IOException {
		
		StandardPBEStringEncryptor pbe = new StandardPBEStringEncryptor();
		Map<String, String> envList = System.getenv();
		
		pbe.setAlgorithm("PBEWithMD5AndDES");
		pbe.setPassword(envList.get("ENV_PASS"));
		
		System.out.println(envList.get("ENV_PASS"));
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("MySQL Username >> ");
		String username = scanner.nextLine();
		
		System.out.print("MySQL Password >> ");
		String password= scanner.nextLine();
		
		String encUserName = pbe.encrypt(username);
		String encPassword = pbe.encrypt(password);
		
		ResourceLoader resLoader = new DefaultResourceLoader();
		Resource res = resLoader.getResource("file:src/main/resources/db.connection2.properties");
		
		String saveUserName = String.format("mysql.username=ENC%s", encUserName);
		String savePassword = String.format("mysql.password=ENC%s", encPassword);
		
		try {
			PrintWriter pw = new PrintWriter(res.getFile());
			pw.println(saveUserName);
			pw.println(savePassword);
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scanner.close();
		System.out.println("db.connection.properties 저장 완료!");
	}

}
