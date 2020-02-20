package com.biz.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.app.AddrVO;

@Service
public class AddrService {
	
	public List<AddrVO> getAllAddrList() {
		
		List<AddrVO> addrList = new ArrayList<AddrVO>();
		
		AddrVO addrVO = new AddrVO();
		
		addrVO.setName("홍길동");
		addrVO.setTel("010-1111-1111");
		addrVO.setPost("12345");
		addrVO.setCity("서울특별시");
		addrVO.setAddr("강남구 도곡동 타워팰리스");
		
		addrList.add(addrVO);
		
		
		addrVO = new AddrVO();
		
		addrVO.setName("이몽룡");
		addrVO.setTel("010-2222-2222");
		addrVO.setPost("67890");
		addrVO.setCity("B시");
		addrVO.setAddr("B-1구 B-2동");
		
		addrList.add(addrVO);
		
		
		addrVO = new AddrVO();
		
		addrVO.setName("이순신");
		addrVO.setTel("010-3333-3333");
		addrVO.setPost("13579");
		addrVO.setCity("C시");
		addrVO.setAddr("C-1구 C-2동");
		
		addrList.add(addrVO);
		
		return addrList;
	}

}
