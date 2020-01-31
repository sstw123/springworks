package com.biz.ems;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Dec {
	
	public static void main(String[] args) {
		
		StandardPBEStringEncryptor pbEnc = new StandardPBEStringEncryptor();
		
		String saltPass = System.getenv().get("NAVER");
		
		pbEnc.setAlgorithm("PBEWithMD5AndDES");
		pbEnc.setPassword(saltPass);
		
		String enc = "";
		
		System.out.println("dec : " + pbEnc.decrypt(enc));
	}

}
