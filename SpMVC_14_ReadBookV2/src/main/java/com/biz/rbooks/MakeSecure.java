package com.biz.rbooks;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.core.env.Environment;

public class MakeSecure {
	
	private static final String saltPass = "";
	private static Environment env;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		StandardPBEStringEncryptor pbEnc = new StandardPBEStringEncryptor();
		
		
		System.out.print("UserID : ");
		String pUserId = scan.nextLine();
		
		System.out.print("Password : ");
		String pPassword = scan.nextLine();
		
		// 운영체제 환경변수 가져오기
		String envString = System.getenv("ORACLE_PASS");
		if(envString == null) envString = saltPass;
		
		pbEnc.setAlgorithm("PBEWithMD5AndDES");
		pbEnc.setPassword(envString);
		
		String encUserId = pbEnc.encrypt(pUserId);
		String encPassword = pbEnc.encrypt(pPassword);
		
		System.out.printf("UserID : %s, %s\n", pUserId, encUserId);
		System.out.printf("Password : %s, %s\n", pPassword, encPassword);
		
		
		String strUserId = String.format("oracle.username=ENC(%s)", encUserId);
		String strPassword = String.format("oracle.password=ENC(%s)", encPassword);
		
		String proFileName = "./src/main/webapp/WEB-INF/spring/oracle.sec.properties";
		
		try {
			PrintWriter pw = new PrintWriter(proFileName);
			pw.println(strUserId);
			pw.println(strPassword);
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.close();
		
	}

}
