package com.biz.bbs;

import java.util.ArrayList;
import java.util.List;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.service.FileReaderService;

public class Main2 {
	
	public static void main(String[] args) {
		
		FileReaderService fSvc = new FileReaderService();
		
		List<BBsVO> bbsList = fSvc.getBBsData();
		List<BBsVO> pList = fSvc.getMain(bbsList);
		
//		pList.forEach(System.out::println);
		
		List<BBsVO> replyList = new ArrayList<>();
		
		pList.forEach(vo->{
			replyList.addAll(fSvc.getReply(bbsList, vo, 0));
		});
		
		replyList.forEach(System.out::println);
		
		
	}

}
