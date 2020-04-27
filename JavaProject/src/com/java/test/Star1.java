package com.java.test;

import java.util.Scanner;

public class Star1 {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int n = scan.nextInt();
		for(int i = 0; i < n * 2 - 1; i++) {
			String str = "";
			
			if(i < n) {
				for(int j = 0; j < i + 1; j++) {
					str += "*";
				}
			} else {
				for(int j = i; j < n * 2 - 1; j++) {
					str += "*";
				}
			}
			
			System.out.println(str);
		}
		
		scan.close();
	}
}