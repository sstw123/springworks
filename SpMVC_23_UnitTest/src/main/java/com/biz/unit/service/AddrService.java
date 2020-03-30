package com.biz.unit.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AddrService {
	
	public Map<String, String> getAddr() {
		Map<String, String> addrMap = new HashMap<String, String>();
		addrMap.put("name", "홍길동");
		addrMap.put("addr", "서울특별시");
		addrMap.put("tel", "010");
		
		return addrMap; 
	}
	
	public String getName(String name) {
		return "저는 " + name + " 입니다";
	}

}
