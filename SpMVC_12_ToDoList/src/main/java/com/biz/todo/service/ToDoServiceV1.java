package com.biz.todo.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.todo.domain.ToDoList;
import com.biz.todo.repository.ToDoListDao;

@Service("todoSvcV1")
public class ToDoServiceV1 implements ToDoService {
	/*
	 * mybatis-context.xml 파일에 bean으로 MapperFactoryBean을 설정해두면
	 * bean class=MapperFactoryBean, property name=mapperInterface value=Dao, property name=sqlSessionFactory ref=SessionFactoryBean
	 * 
	 * <bean class="org.mybatis.spring.mapper.MapperFactoryBean">
	 * 	<property name="mapperInterface" value="com.biz.todo.repository.ToDoListDao"/>
	 * 	<property name="sqlSessionFactory" ref="ssfb" />
	 * </bean>
	 * 
	 * SqlSession과 Dao 생성 메소드를 만들지 않고 바로 Dao를 사용할 수 있다
	 */
	
	@Autowired
	protected ToDoListDao todoDao;

	@Override
	public List<ToDoList> selectAll() {
		// TODO Auto-generated method stub
		return todoDao.selectAll();
	}

	@Override
	public ToDoList selectBySeq(long tdSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ToDoList> selectBySub(String tdSub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ToDoList toDoList) {
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat st = new SimpleDateFormat("hh:mm:ss");
		
		String curDate = sd.format(date);
		String curTime = st.format(date);
		
		String strTdComplete = toDoList.getTdComplete();
		if(strTdComplete == null) {
			toDoList.setTdComplete("N");
		}
		
		String strTdAlarm = toDoList.getTdAlarm();
		if(strTdAlarm == null) {
			toDoList.setTdAlarm("N");
		}
		
		String strTdFlag = toDoList.getTdFlag();
		if(strTdFlag == null) {
			toDoList.setTdFlag("1");
		}
		
		toDoList.setTdDate(curDate);
		toDoList.setTdTime(curTime);
		
		int ret = todoDao.insert(toDoList);
		
		return ret;
	}

	@Override
	public int update(ToDoList toDoList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long tdSeq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int complete(String strSeq) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int alarm(String strSeq) {
		// TODO Auto-generated method stub
		return 0;
	}

}
