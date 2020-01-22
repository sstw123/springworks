<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<link rel="stylesheet" type="text/css" href="">
	<style>
		table {
			width: 70%;
			margin: 20px auto;
			border-top: 2px solid #252525;
			border-bottom: 1px solid #ccc;
		}
		
		table caption {
			font-size: 25px;
			padding: 10px;
			margin: 10px;
			font-weight: bold;
			color: blue;
		}
		
		table th {
			text-align: left;
			background-color: #f7f7f7;
			color: #3b3b3b;
		}
		
		table th, table td {
			padding: 15px 0 16px 16px;
			border-bottom: 1px solid #ccc;
		}
		
		div.btn-box {
			width: 100%;
			margin: 5px auto;
			display:flex;
			justify-content:center;
			align-items: center;
		}
		
		a.btn {
			border-radius: 3px;
			padding: 5px 11px;
			color: #fff;
			display: inline-block;
			background-color: #6b9ab8;
			border: 1px solid #56819d;
			text-decoration: none;
			margin: 10px;
		}
		
		a.btn:hover {
			box-shadow: 5px 5px 8px rgba(80, 80, 80, 0.8);
		}
		
		a#btn-update, a#btn-delete {
			vertical-align: middle;
		}
		
		a#btn-list {
			vertical-align: left;
		}
	</style>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="${rootPath}/js/jquery-3.4.1.js"></script>
	<script>
	$(function() {
		$("a#btn-update").click(function() {
			document.location.href = "${rootPath}/memo/update?id=${memoDTO.m_seq}"
		})
		
		$("a#btn-delete").on("click",function() {
			if(confirm("메모를 삭제할까요?")) {
				document.location.replace("${rootPath}/memo/delete?m_seq=${memoDTO.m_seq}")
			}
			
		})
		
		$("a#btn-list").click(function() {
			document.location.href = "${rootPath}/memo/list"
		})
		/*
			document.location.href = 는 새로운 페이지로 넘겨주는 것이고
			document.location.replace()는 현재 페이지를 다른 파일로 바꾸는 것이다
			따라서 href는 새 페이지에서 뒤로가기를 하면 이전 창이 다시 뜨지만
			replace는 뒤로가기를 하면 이이전의 창이 뜨게 된다
		*/
	})
	</script>
</head>
<body>
	<table>
		<caption>매입매출 상세</caption>
		<tr>
			<th>SEQ</th><td>${memoDTO.m_seq}</td>
			<th>작성자</th><td>${memoDTO.m_auth}</td>
		</tr>
		
		<tr>
			<th>작성일자</th><td>${memoDTO.m_date}</td>
			<th>작성시각</th><td>${memoDTO.m_time}</td>
		</tr>
		
		<tr>
			<th>제목</th><td colspan="3">${memoDTO.m_subject}</td>
		</tr>
		
		<tr>
			<th>내용</th><td colspan="3">${memoDTO.m_text}</td>
		</tr>
	</table>
	<br/><br/>
	<div class="btn-box">
		<a href="javascript:void(0)" class="btn" id="btn-list">목록</a>
		<a href="javascript:void(0)" class="btn" id="btn-update">수정</a>
		<a href="javascript:void(0)" class="btn" id="btn-delete">삭제</a>
	</div>
</body>
</html>