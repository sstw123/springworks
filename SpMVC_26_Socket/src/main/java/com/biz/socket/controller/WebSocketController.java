package com.biz.socket.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.extern.slf4j.Slf4j;

// 이 클래스는 STOMP를 지원하는 기능을 사용할 수 있도록
// 만들어져 있는 기본 클래스(TextWebSocketHandler)를 상속받아서 사용한다
@Slf4j
@Component
public class WebSocketController extends TextWebSocketHandler {
	
	// socket으로 서버에 접속할 때, 접속하는 클라이언트들을 관리하기 위한 session
	List<WebSocketSession> sessionList;
	
	public WebSocketController() {
		sessionList = new ArrayList<>();
	}
	
	// 클라이언트가 웹소켓을 통해서 접속을 시도할 때 처음 실행될 method
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		
		// 접속된 클라이언트의 정보를 sessionList에 추가
		sessionList.add(session);
		log.debug("접속된 클라이언트 ID : {}", session.getId());
		
	}
	
	// 클라이언트가 메시지를 보내면 수신하는 컨트롤러 메소드
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		
		log.debug("{} 클라이언트가 보낸 메시지 : {}", session.getId(), message.getPayload());
		
		for(WebSocketSession wss : sessionList) {
			String msg = "abcde";
			TextMessage textMessage = new TextMessage(msg);
			// TextMessage는 문자열이나 여러 메시지를 socket을 통해 전송하기 편리하도록
			// STOMP 데이터 구조로 변경한다
			wss.sendMessage(textMessage);
			
			wss.sendMessage(message);
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessionList.remove(session);
	}

}
