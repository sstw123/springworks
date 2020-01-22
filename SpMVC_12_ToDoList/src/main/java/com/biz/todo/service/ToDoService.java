package com.biz.todo.service;

import java.util.List;

import com.biz.todo.domain.ToDoList;

/*
 * 인터페이스를 왜 사용하는가
 * 
 * 1. 어떤 서비스를 구현할 것인가의 설계도
 * 2. 여러 팀원이 분담하여 프로젝트를 구현할 때
 *    꼭 필요한 method를 빼먹지 않고 구현할 수 있도록 하는 Guide 역할
 * 3. 자바의 다형성 원리를 이용하여, 버전업(Upgrade)을 했을때
 *    기존에 사용하던 코드를 최소한으로 변경하여 효과를 낼 수 있도록 하는 목적
 * 
 * 코드의 재사용
 * 객체지향 등장 이전에는 Copy&Paste 또는 라이브러리를 가져와서 사용
 * 객체지향에서는 상속을 이용하는데 코드가 난해해진다
 * 
 * 4. 클래스를 상속받을 때 코드가 난해해지는 것을 방지
 * 5. 클래스와 클래스 사이에서 서로 연관도를 낮추어 유연한 관계를 설정하기도 한다
 * 6. 인터페이스를 기획자, PM 등이 미리 설정을 해두어야 한다
 */
public interface ToDoService {
	
	public List<ToDoList> selectAll();
	public ToDoList selectBySeq(long tdSeq);
	public List<ToDoList> selectBySub(String tdSub);
	
	public int insert(ToDoList toDoList);
	public int update(ToDoList toDoList);
	public int delete(long tdSeq);
	public int complete(String strSeq);
	public int alarm(String strSeq);
}
