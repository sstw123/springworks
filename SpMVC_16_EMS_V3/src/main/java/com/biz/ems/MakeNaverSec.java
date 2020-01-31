package com.biz.ems;

import java.io.File;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class MakeNaverSec {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		// jasypt 선언 및 초기화
		StandardPBEStringEncryptor pbEnc = new StandardPBEStringEncryptor();
		
		// (시스템의) 환경변수 가져오기
		Map<String, String> envList = System.getenv();
		String saltPass = envList.get("NAVER");
		System.out.println("SaltPass : " + saltPass);
		
		if(saltPass == null || saltPass.isEmpty()) {
			System.out.println("Salt Password Not Found");
			return;
		}
		
		// 암호화 설정
		pbEnc.setAlgorithm("PBEWithMD5AndDES");
		pbEnc.setPassword(saltPass);
		
		Map<String, String[]> secFileList = new TreeMap<>();
		
		secFileList.put("hibernate.secret.properties", new String[] {"mysql.username", "mysql.password"});
		secFileList.put("naver.email.secret.properties", new String[] {"naver.username", "naver.password", "naver.client.id","naver.client.secret"});
		
		String propFileDir = "./src/main/webapp/WEB-INF/spring/appServlet/props";
		
		Set<String> files = secFileList.keySet();
		
		try {
			for(String file : files) {
				File propFile = new File(propFileDir, file);
				System.out.println("=======================================");
				System.out.println(propFile.getAbsolutePath() + " 파일 생성");
				System.out.println("---------------------------------------");
				
				PrintWriter pw = new PrintWriter(propFile);
				
				for(String key : secFileList.get(file)) {
					System.out.print(key + " : ");
					String plainString = scan.nextLine();
					if(plainString.isEmpty()) {
						System.out.println("Exit!!!");
						pw.close();
						scan.close();
						return;
					}
					String encString = String.format("%s=ENC(%s)", key, pbEnc.encrypt(plainString));
					System.out.println(encString);
					
					pw.println(encString);
					pw.flush();
				}
				
				pw.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		scan.close();
	}

}
