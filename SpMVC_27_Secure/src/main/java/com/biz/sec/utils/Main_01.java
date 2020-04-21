package com.biz.sec.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Main_01 {
	
	public static void main(String[] args) {
		
		int[] num = new int[3];
		num[0] = 100;
		num[1] = 100;
		num[2] = 100;
		
		// Arrays.asList(배열) : 배열을 리스트로 변경하기 완전한 변경은 아니며 무늬만 List
		// 이 메소드로 변경된 List는 추가, 삭제, 내용변경이 불가능하기 때문에
		// 완전한 List로 만들기 위해 new ArrayList<>(); 생성자로 감싸주도록 한다
		List<int[]> numList = new ArrayList<>(Arrays.asList(num));
		
		System.out.println(UUID.randomUUID().toString());
		
	}

}
