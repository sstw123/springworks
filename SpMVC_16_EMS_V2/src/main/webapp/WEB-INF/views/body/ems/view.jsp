<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	div.flex {
		display: flex;
		border-collapse: collapse;
		padding: 15px;
	}
	
	span {
		display: inline-block;
		margin-right: 5px;
	}
	
	div.flex:nth-of-type(1) span {
		font-size: 14px;
	}
	
	div.flex:nth-of-type(1) span:nth-of-type(2) { width: 50px; }
	div.flex:nth-of-type(1) span:nth-of-type(3) { width: 50px; font-weight: bold; }
	
	div#content {
		justify-content: center;
		font-weight: bold;
		padding-bottom: 0;
	}
	
	.span1 {
		width: 100px;
		overflow: hidden;
		font-weight: bold;
	}
	.spanMail {
		color: #888;
	}
	
	.button_box {
		text-align: right;
	}
	
	button.view_btn {
		padding: 10px;
		background-color: #00E0FF;
		border: 1px solid #00E0FF;
		border-radius: 5px;
		
		cursor: pointer;
	}
	
	button.view_btn:hover {
		background-color: black;
		border: 1px solid black;
		color: white;
	}
</style>
<script>
	$(function() {
		$(".view_btn").on("click", function() {
			let text = $(this).text()
			let url = "${rootPath}/ems/"
			
			if(text == "수정") {
				url += "update/" + ${emailVO.emsSeq}
			} else if(text == "삭제") {
				if(!confirm("정말로 삭제하시겠습니까?")) {
					return false
				}
				url += "delete/" + ${emailVO.emsSeq}
			} else if(text == "목록") {
				url += "list"
			}
			
			document.location.href = url
		})
	})
</script>
<div class="flex">
	<span class="span1">메일번호</span><span>${emailVO.ems_seq}</span><span>날짜</span><span>${emailVO.send_date} ${emailVO.send_time}</span>
</div>

<div class="flex">
	<span class="span1">발신</span><span>${emailVO.from_name}</span><span class="spanMail">${emailVO.from_email}</span>
</div>

<div class="flex">
	<span class="span1">수신</span><span>${emailVO.to_name}</span><span class="spanMail">${emailVO.to_email}</span>
</div>

<div class="flex">
	<span class="span1">제목</span><span>${emailVO.subject}</span>
</div>

<div class="flex" id="content">
	내용
</div>

<div class="flex">
	<span>${emailVO.content}</span>
</div>

<div class="button_box">
	<button class="view_btn">수정</button>
	<button class="view_btn">삭제</button>
	<button class="view_btn">목록</button>
</div>