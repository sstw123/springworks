package com.java.test;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int a = scan.nextInt();
		int b = a;
		int c = 0;
		int i = 0;
		
		do {
			c = (b / 10 + b % 10);
			b = ((b % 10) * 10) + (c % 10);
			i++;
		} while (a != b);
		
		System.out.println(i);
		
		scan.close();
	}
}