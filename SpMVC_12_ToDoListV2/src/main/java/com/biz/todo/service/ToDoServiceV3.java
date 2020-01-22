package com.biz.todo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.todo.domain.ToDoList;

@Service("todoSvcV3")
public class ToDoServiceV3 extends ToDoServiceV2 {

	@Override
	public ToDoList selectBySeq(String strSeq) {
		long td_seq = Long.valueOf(strSeq);
		
		return todoDao.selectBySeq(td_seq);
	}

	@Override
	public List<ToDoList> selectBySub(String td_sub) {
		return todoDao.selectBySub(td_sub);
	}
	
	@Override
	public int update(ToDoList toDoList) {
		toDoList = this.setToDoList(toDoList);
		
		return todoDao.update(toDoList);
	}

	@Override
	public int insert(ToDoList toDoList) {
		toDoList = this.setToDoList(toDoList);
		
		return todoDao.insert(toDoList);
	}
	
	protected ToDoList setToDoList (ToDoList toDoList) {
		String td_alarm = toDoList.getTd_alarm();
		String td_complete = toDoList.getTd_complete();
		String td_date = toDoList.getTd_date();
		String td_flag = toDoList.getTd_flag();
		String td_time = toDoList.getTd_time();
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat st = new SimpleDateFormat("hh:mm:ss");
		
		if(td_alarm == null || td_alarm.isEmpty()) toDoList.setTd_alarm("N"); ;
		if(td_complete == null || td_complete.isEmpty()) toDoList.setTd_complete("N");
		if(td_date == null || td_date.isEmpty()) toDoList.setTd_date(sd.format(date));
		if(td_time == null || td_time.isEmpty()) toDoList.setTd_time(st.format(date));
		if(td_flag == null || td_flag.isEmpty()) toDoList.setTd_flag("1");
		
		return toDoList;
	}

}
