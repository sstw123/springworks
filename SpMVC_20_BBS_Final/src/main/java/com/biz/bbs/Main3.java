package com.biz.bbs;

public class Main3 {
	
	public static void main(String[] args) {
		//int sum = add(10);
		//System.out.println(sum);
	}
	
	
	/*
	 * 1. main에서 add(10) 호출
	 * 2. if 조건에 해당하지 않으니 그 밑 코드로 진행
	 * 3. return 10 + add(9) 호출
	 * 4. return 10 + return 9 + add(8) 호출
	 * ...
	 * 5. return 10 + return 9 + return 8 + ... + return 0
	 * 6. 맨 안쪽 스코프부터 리턴값을 더하면 10 + 9 + ... + 1 + 0
	 */
	public static int add(int num) {
		
		if(num < 1) return 0;//재귀호출에서 가장 중요한 것 : 끝내는 부분
		
		else return num + add(num - 1); 
		
		
	}

}
