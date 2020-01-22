package com.biz.todo.service;

import org.springframework.stereotype.Service;

import com.biz.todo.domain.ToDoList;

@Service("todoSvcV2")
public class ToDoServiceV2 extends ToDoServiceV1 {

	@Override
	public int complete(String strSeq) {
		long td_seq = Long.valueOf(strSeq);
		
		return todoDao.complete(td_seq);
	}

	@Override
	public int alarm(String strSeq) {
		long td_seq = Long.valueOf(strSeq);
		
		return todoDao.alarm(td_seq);
	}

	@Override
	public int update(ToDoList toDoList) {
		return todoDao.update(toDoList);
	}

	@Override
	public int delete(String strSeq) {
		long td_seq = Long.valueOf(strSeq);
		
		return todoDao.delete(td_seq);
	}
	
	
	
}
