package com.biz.todo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.biz.todo.domain.ToDoList;

public interface ToDoListDao {
	
	public List<ToDoList> selectAll();
	public ToDoList selectBySeq(long tdSeq);
	
	public int insert(ToDoList toDoList);
	public int update(ToDoList toDoList);
	public int delete(long tdSeq);
	public int complete(@Param("tdSeq")long tdSeq);
	public int alarm(@Param("tdSeq")long tdSeq);
	
}
