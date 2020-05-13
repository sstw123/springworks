package com.biz.shop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Algorithm {
	
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int testCase = Integer.valueOf(br.readLine());
		
		for(int i = 0 ; i < testCase ; i++) {
			
			String[] arrDayTeam = br.readLine().split(" ");
			int N = Integer.valueOf(arrDayTeam[0]);
			int L = Integer.valueOf(arrDayTeam[1]);
			String[] arrCost = br.readLine().split(" ");
			double avg = Double.MAX_VALUE;
			
			// N=6 L=3인 경우 3+0, 3+1, 3+2, 3+3
			// N=6 L=2인 경우 2+0, 2+1, 2+2, 2+3, 2+4
			for(int j = 0 ; j < N - L + 1 ; j++) {
				// L=3인 경우 1~3, 1~4, 1~5, 1~6
				int maxCount = L + j;
				// 평균 구할때 나눌 수 : 3, 4, 5, 6
				int divide = L + j;
				for(int k = 0 ; k < N - L + 1 - j ; k++) {
					double tempSum = 0;
					double tempAvg = 0;
					
					// arrCost 합 구하기
					// 0~2, 0~3, 0~4, 0~5
					for(int l = k ; l < maxCount ; l++) {
						tempSum += Integer.valueOf(arrCost[l]);
					}
					
					// arrCost 평균 구하기
					tempAvg = tempSum / divide;
					if(tempAvg < avg) avg = tempAvg;
					maxCount++;
				}
			}
			
			System.out.println("2 : " + avg);
			
		}
		
	}

}
