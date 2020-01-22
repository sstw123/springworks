package com.biz.todo.service;

import org.springframework.stereotype.Service;

@Service("todoSvcV2")
public class ToDoServiceV2 extends ToDoServiceV1 {

	@Override
	public int complete(String strSeq) {
		// TODO Auto-generated method stub
		long tdSeq = Long.valueOf(strSeq);
		
		return todoDao.complete(tdSeq);
	}

	@Override
	public int alarm(String strSeq) {
		// TODO Auto-generated method stub
		long tdSeq = Long.valueOf(strSeq);
		
		return todoDao.alarm(tdSeq);
	}
	
	
	
}
