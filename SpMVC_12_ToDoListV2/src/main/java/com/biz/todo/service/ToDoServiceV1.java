package com.biz.todo.service;

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
	public ToDoList selectBySeq(String strSeq) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ToDoList> selectBySub(String td_sub) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ToDoList toDoList) {
		int ret = todoDao.insert(toDoList);
		
		return ret;
	}

	@Override
	public int update(ToDoList toDoList) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(String strSeq) {
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
