package com.biz.ems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class MakeNaverSec {
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		// jasypt 선언 및 초기화
		StandardPBEStringEncryptor pbEnc = new StandardPBEStringEncryptor();
		
		// 환경변수 가져오기
		Map<String, String> envList = System.getenv();
		
		String saltPass = envList.get("NAVER");
		System.out.println("SaltPass : " + saltPass);
		
		// 암호화 설정
		pbEnc.setAlgorithm("PBEWithMD5AndDES");
		pbEnc.setPassword(saltPass);
		
		// 암호화하기
		//encrypt(scan, pbEnc);
		
		// 복호화하기
		decrypt(scan, pbEnc);
	}
	
	private static void encrypt(Scanner scan, StandardPBEStringEncryptor pbEnc) {
		// 평문 입력받기
		System.out.print("Naver ID : ");
		String naverId = scan.nextLine();
		
		System.out.print("Naver PW : ");
		String naverPW = scan.nextLine();
		
		System.out.print("DB User : ");
		String dbUserId = scan.nextLine();
		
		System.out.print("DB PW : ");
		String dbUserPW = scan.nextLine();
		
		// 평문 -> 암호화
		String encNaverId = pbEnc.encrypt(naverId);
		String encNaverPW = pbEnc.encrypt(naverPW);
		String encDBUserId = pbEnc.encrypt(dbUserId);
		String encDBUserPW = pbEnc.encrypt(dbUserPW);
		
		System.out.println("encNaverId : " + encNaverId);
		System.out.println("encNaverPW : " + encNaverPW);
		
		String saveNaverId = String.format("naver.username=ENC(%s)", encNaverId);
		String saveNaverPW = String.format("naver.password=ENC(%s)", encNaverPW);
		String saveDBUserId = String.format("mysql.username=ENC(%s)", encDBUserId);
		String saveDBUserPW = String.format("mysql.password=ENC(%s)", encDBUserPW);
		/*
		String profileName = "./src/main/webapp/WEB-INF/spring/naver.email.secret.properties";
		
		String WEB_INF = "./src/main/webapp/WEB-INF/spring";
		String naver_properties = "naver.email.secret.properties";
		
		File propertiesFile = new File(WEB_INF,naver_properties);
		// 앞쪽은 path, 뒤쪽은 파일명
		// 원래 path는 다른 OS는 /를 사용하고 윈도우에선 \를 사용하는데
		// File 클래스가 자동으로 실행되는 운영체제에 맞춰서 변환시켜준다
		
		try {
			PrintWriter pw = new PrintWriter(propertiesFile);
			pw.println(saveNaverId);
			pw.println(saveNaverPW);
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scan.close();
		*/
		
		System.out.println(saveDBUserId);
		System.out.println(saveDBUserPW);
	}
	
	private static void decrypt(Scanner scan, StandardPBEStringEncryptor pbEnc) {
		// 암호문 입력받기
		System.out.print("암호문 : ");
		String encString = scan.nextLine();
		
		// 평문 -> 암호화
		String originalStr = pbEnc.decrypt(encString);
		
		System.out.println("복호화 값 : " + originalStr);
	}

}
