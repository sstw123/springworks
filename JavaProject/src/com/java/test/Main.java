package com.java.test;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		int n = scan.nextInt();
		for(int i = 1 ; i <= n * 2; i++) {
			String str = "";
			
			if (n == 1) {
				str = "*";
				System.out.println(str);
				break;
			} else if(i % 2 == 1) {
				str = "*";
				for(int j = 0 ; j < (n-1)/2 ; j++) {
					str += " *";
				}
			} else if(i % 2 == 0) {
				for(int j = 0 ; j < n/2 ; j++) {
					str += " *";
				}
			}
			
			System.out.println(str);
		}
		
		scan.close();
	}
}