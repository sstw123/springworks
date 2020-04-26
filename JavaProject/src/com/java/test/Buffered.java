package com.java.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Buffered {
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		try {
			String str = br.readLine();
			int testNum = Integer.parseInt(str);
			
			for(int i = 0; i < testNum; i++) {
				str = br.readLine();
				String[] arrStr = str.split(" ");
				
				int a = Integer.parseInt(arrStr[0]);
				int b = Integer.parseInt(arrStr[1]);
				
				bw.write(a+b + "\n");
			}
			bw.flush();
			
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}