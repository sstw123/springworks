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

/*
 * LifeCycle Method
 * 어떤 동작이 수행되는 과정에서 자동으로 호출되는 method
 * 복잡한 코드를 만들지 않아도 자동으로 처리해줄 수 있다
 * 
 * afterConnectionEstablished :
 * 서버와 클라이언트가 서로 Socket으로 연결되었을 때 바로 호출되는 method
 * session을 별도로 저장하거나 하는 일들을 코딩한다
 * 
 * handleTextMessage :
 * 클라이언트에서 메세지를 보내면 수신하는 method
 * 내부 코드를 실행한 후 결과를 다시 클라이언트에게 보낸다
 * node.js 등 다른 서버에서는 받은 메세지 별로 method를 독립적으로 작성하기도 한다
 * socket.on("메세지", callback)
 * 
 * afterConnectionClosed :
 * 
 */

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
		
		// 임의의 command protocol 선언
		// 전달받은 메세지에 command가 포함되어 있는지 구분하는 코드
		// json 형태를 String.split을 이용하여 분해하기
		String user[] = message.getPayload().split(":");
		
		
		// userList:값 또는 getUserList:값
		if(user.length > 1) {
				// Command가 userName이면 실행
				// 채팅 어플에 접속했을 때 최초 사용자 이름을 입력하면
				// 사용자 이름과 session을 정보에 저장한 후 다시 클라이언트에게 알려주는 부분
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
				
				// session.sendMessage(new TextMessage( objMapper.writeValueAsString(map) ) );
				// 위와 같은 코드
				String userName = objMapper.writeValueAsString(map);
				this.sendMsgMe(session, userName);
				
				return;
			} else if(user[0].equalsIgnoreCase("getUserList")) {
				// Command가 getUserList면 실행
				// 현재 접속자 정보를 모두 클라이언트로 보내기
			
				String userList = objMapper.writeValueAsString(messageMap);
				map.put("what", "userList");
				map.put("userList", userList);
				
				String strUserList = objMapper.writeValueAsString(map);
				this.sendMsgAll(session, strUserList);
				
				log.debug(strUserList);
				
				return;
			}
		}
		
		// 채팅이 진행되는 과정에서 메세지 전파
		Gson gson = new Gson();
		MessageVO messageVO = gson.fromJson(message.getPayload(), MessageVO.class);
		
		// 1. Gson을 사용하여 문자열형 데이터를 VO로 변환시키기
		// String strGsonMessage = String.format("[%s] : %s", messageVO.getUserName(), messageVO.getMessage());
		
		// 2. ObjectMapper(jackson databind의 클래스)를 사용하여 변환시키기
		// VO 형태로 받은 데이터를 json형 string문자열로 변환시키고 TextMessage형으로 변환시켜서 보내기
		String strJacksonMessage = objMapper.writeValueAsString(messageVO);
		
		if(messageVO.getToUserId().equalsIgnoreCase("ALL")) {
			// 나를 제외한 전체에게 메세지 보내기
			this.sendMsgWithoutMe(session, strJacksonMessage);
		} else {
			for(WebSocketSession wss : sessionList) {
				if(wss.getId().equals(messageVO.getToUserId())) {
					this.sendMsgMe(wss, strJacksonMessage);
					break;
				}
			}
		}
		
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// TODO Auto-generated method stub
		super.afterConnectionClosed(session, status);
		sessionList.remove(session);
		messageMap.remove(session.getId());
	}
	
	
	// 요청한 접속자에게만 메세지 보내기
	private void sendMsgMe (WebSocketSession session, String strMessage) throws IOException {
		TextMessage sendMessage = new TextMessage(strMessage);
		session.sendMessage(sendMessage);
	}
	
	// 전체 접속자(모든 세션)에게 메세지 보내기
	private void sendMsgAll(WebSocketSession session, String strMessage) throws IOException {
		TextMessage sendMessage = new TextMessage(strMessage);
		for(WebSocketSession wss : sessionList) {
			wss.sendMessage(sendMessage);
		}
	}
	
	// 나를 제외한 접속자에게 메세지 보내기
	private void sendMsgWithoutMe(WebSocketSession session, String strMessage) throws IOException {
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
