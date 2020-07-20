<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
	
	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<style>
		* {
			padding: 0;
			margin: 0;
		}
		
		#message_list {
			border: 1px solid black;
			height: 25vh;
			margin: 0 auto;
			overflow: auto;
		}
		form {
			margin: 50px auto;
		}
		
		.userName {
			color: blue;
		}
		
		.from, .to {
			padding: 5px;
		}
		
		.to .userName {
			font-weight: bold;
		}
	</style>
	<script>
		//var sock = new SockJS('http://localhost:8080/socket/chat');
		var sock = new SockJS('http://192.168.4.12:8080/socket/chat');
		
		// 소켓 서버에 접속 시작
		sock.onopen = function() {
			console.log('open');
			
			let userName = prompt("채팅방에서 사용할 이름을 입력하세요")
			
			if(userName && userName != "") {
				sock.send("userName:" + userName)
			}
			
			sock.send("getUserList:" + userName)
		};
		 
		 // 서버로부터 수신되는 이벤트
		 sock.onmessage = function(e) {
		     //console.log('message : ', e.data);
		     
		     //jackson의 ObjectMapper
		     //json 문자열을 JSON.parse로 만들기(stringify <-> parse)
		     let jsonMessage = JSON.parse(e.data)
		     
		     //console.log('message2 : ', jsonMessage);
		     
		     if(jsonMessage.what && jsonMessage.what == 'userName') {
		    	 $("#userName").val(jsonMessage.userName)
		    	 $("#userName").prop("readonly", true)
		    	 
		    	 return false
		     }
		     
		     if(jsonMessage.what && jsonMessage.what == "userList") {
		    	 let userList = JSON.parse(jsonMessage.userList)
		    	 
		    	 console.log("userList : ", userList)
		    	 
		    	 // Object.keys() : js의 기본 method
		    	 // userList 객체 그룹중에서 각 요소의 키값만 추출하여 배열로 만들기
		    	 let userListKeys = Object.keys(userList)
		    	 
		    	 $("#toList").empty()
		    	 $("#toList").append($("<option/>", {
			    					value: "all",
			    					text: "전체"
		    					}))
		    	 
		    	 for(let i = 0; i < userListKeys.length ; i++) {
		    		//console.log(userListKeys[i])
		    		// JSON 데이터에서 key로 값을 추출할 때는 json[key] 방식으로 추출한다
		    		 console.log("사용자 정보 : " + userList[userListKeys[i]].userName)
		    		$("#toList").append($("<option/>", {
			    					value: userListKeys[i],
			    					text: userList[userListKeys[i]].userName
		    					}))
		    	 	
		    	 }
		    	 
		    	 return false
		     }
		     
		     let html = "<div class='from text-right'>"
		    	 		+ "<span class='userName'>"
		     			+ jsonMessage.userName
		     			+ "</span> : "
		     			+ jsonMessage.message
		     			+ "</div>"
		     			
		     $("#message_list").append(html)
		     $("#message_list").scrollTop( $("#message_list").prop("scrollHeight") )
		     
		     //sock.close();
		 };

		 /*
		 // 소켓 서버와 접속 종료
		 sock.onclose = function() {
		     console.log('close');
		 };
		 */
	</script>
	<script>
		
		$(function() {
			
			$(document).on("submit", "form", function() {
				if($("#userName").val() == "") {
					alert("이름을 입력하세요")
					$("#userName").focus()
					return false
				}
				
				let toUserId = $("#toList option:checked").val()
				let toUserName = $("#toList option:checked").text()
				
				let message = {
					toUserId : toUserId,
					userName : $("#userName").val(),
					message : $("#message").val()
				}
				
				let html = "<div class='to text-left'>"
						+ "<span class='userName'>"
						+ message.userName
						+ "</span> : "
						+ message.message
						+ "<span>("
						+ toUserName
						+ ")</span>"
						+ "</div>"
				
				$("#message_list").append(html)
				$("#message_list").scrollTop( $("#message_list").prop("scrollHeight") )
				
				sock.send(JSON.stringify(message))
				
				$("#message").val("")
				$("#message").focus()
				
				return false
			})
			
			/*
			$(document).on("click", "#btn_send", function() {
				let message = $("#message").val()
				$("#message_list").append("<p>나 >> " + message + "</p>")
				sock.send(message)
				
				$("#message").val("")
				$("#message").focus()
			})
			*/
		})
	</script>
</head>
<body>
	<header class="jumbotron bg-success">
		<h2 class="text-center text-white">My Chat Service</h2>
	</header>
	
	<section class="container-fluid">
		<div id="message_list">
			
		</div>
	</section>
	
	<section class="container-fluid">
		<form class="col-5">
			<div class="form-group">
				<input id="userName" class="form-control" placeholder="이름" autocomplete="off"/>
			</div>
			
			<div class="form-group">
				<select id="toList" class="form-control">
					<option value="all">전체</option>
				</select>
			</div>
			
			<div class="form-group">
				<input id="message" class="form-control" placeholder="내용" autocomplete="off"/>
			</div>
			
			<button id="btn_send" class="btn btn-primary">전송</button>
		</form>
	</section>
</body>
</html>