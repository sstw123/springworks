package com.biz.todo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.biz.todo.domain.ToDoList;

/*
 * 1. Request(요청)를 보내면 Dispatcher가 수신하고
 * 2. 적절한 Path를 찾아서 Controller에게 전달하는데
 * 2 전에 Request에 담겨오는 parameter를 가로채서(intercept) 뭔가 처리하고자 할 때 사용하는 Resolver 선언
 */
public class ToDoHandlerResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		// TODO Auto-generated method stub
		// ToDoList toDoList = (ToDoList) webRequest.getAttribute(ToDoList.class.getName(), WebRequest.SCOPE_REQUEST);
		
		String td_date = webRequest.getParameter("td_date");
		String td_time = webRequest.getParameter("td_time");
		String td_seq = webRequest.getParameter("td_seq");
		String td_sub = webRequest.getParameter("td_sub");
		String td_complete = webRequest.getParameter("td_complete");
		String td_alarm = webRequest.getParameter("td_alarm");
		String td_flag = webRequest.getParameter("td_flag");
		
		if(td_alarm == null) td_alarm = "N";
		if(td_complete == null) td_complete = "N";
		if(td_flag == null) td_flag = "N";
		
		Date date = new Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat st = new SimpleDateFormat("hh:mm:ss");
		
		if(td_date == null) td_date = sd.format(date);
		if(td_time == null) td_time = st.format(date);
		
		return ToDoList.builder()
				.td_alarm(td_alarm)
				.td_complete(td_complete)
				.td_date(td_date)
				.td_time(td_time)
				.td_flag(td_flag)
				.td_seq(Long.valueOf(td_seq))
				.td_sub(td_sub).build();
	}

}
