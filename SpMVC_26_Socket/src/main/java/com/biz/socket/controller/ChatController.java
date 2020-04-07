package com.biz.socket.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.biz.socket.domain.MessageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatController extends TextWebSocketHandler {
	
	public List<WebSocketSession> sessionList;
	public Map<String, MessageVO> messageMap;
	
	public ChatController() {
		sessionList = new ArrayList<>();
		messageMap = new HashMap<String, MessageVO>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionEstablished(session);
		
		// 사용자가 접속하면 WebSocketSession 추가하기
		sessionList.add(session);
		
		// messageVO 맵에 id를 키값으로 messageVO 추가하기
		// 세션의 id, messageVO(json구조)로 되어있다
		messageMap.put(session.getId(), new MessageVO());
	}	

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// TODO Auto-generated method stub
		super.handleTextMessage(session, message);
		
		ObjectMapper objMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<>();
		
		// json 형태를 String.split을 이용하여 분해하기
		String user[] = message.getPayload().split(":");
		
		if( user[0].equalsIgnoreCase("userName") ) {
			MessageVO messageVO = messageMap.get(session.getId());
			
			messageVO.setUserName(user[1]); // 객체는 주소이기 떄문에 map에 들어있는 값이 변경된다
			// json 형태의 string 문자열 만들기
			// String sendUserName = String.format("{ \"message\" : \"userName\", \"userName\" : \"%s\" }", messageVO.getUserName());
			// session.sendMessage(new TextMessage(sendUserName) );
			// json 형태의 문자열은 큰따옴표로 묶어주어야 하기 때문에 \"를 사용한다
			
			// 아래는 map + objectMapper를 이용한 json 형태의 string 문자열 만들기 
			map.put("what", "userName");
			map.put("userName", messageVO.getUserName());
			
			// 1. session.sendMessage로 jsp에 전달하고
			// 2. jsp에서 SockJS가 json 문자열을 받게된다
			session.sendMessage(new TextMessage( objMapper.writeValueAsString(map) ) );
			
			return;
		}
		
		
		if(message.getPayload().equalsIgnoreCase("getUserList")) {
			String userList = objMapper.writeValueAsString(messageMap);
			map.put("what", "userList");
			map.put("userList", userList);
			
			String strUserList = objMapper.writeValueAsString(map);
			session.sendMessage(new TextMessage( strUserList ));
			
			return;
		}
		
		Gson gson = new Gson();
		MessageVO messageVO = gson.fromJson(message.getPayload(), MessageVO.class);
		
		// 1. Gson을 사용하여 문자열형 데이터를 VO로 변환시키기
		String strGsonMessage = String.format("[%s] : %s", messageVO.getUserName(), messageVO.getMessage());
		
		// 2. ObjectMapper(jackson databind의 클래스)를 사용하여 변환시키기
		// VO 형태로 받은 데이터를 json형 string문자열로 변환시키고 TextMessage형으로 변환시켜서 보내기
		String strJacksonMessage = objMapper.writeValueAsString(messageVO);
		
		this.sendMessage(session, strJacksonMessage);
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessionList.remove(session);
	}
	
	private void sendMessage(WebSocketSession session, String strMessage) throws IOException {
		TextMessage sendMessage = new TextMessage(strMessage);
		for(WebSocketSession wss : sessionList) {
			// 자신이 보낸 메시지는 다시 자신에게 보낼 필요가 없기 때문에(보내면 화면에 두개씩 입력된다) 자신이 보낸 메시지를 제외하고 전송
			if(!wss.getId().equals(session.getId())) {
				//wss.sendMessage(message);
				//wss.sendMessage(sendTextMessage1);
				wss.sendMessage(sendMessage);
			}
		}
	}

}
