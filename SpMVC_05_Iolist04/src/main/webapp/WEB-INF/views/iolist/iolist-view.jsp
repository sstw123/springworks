<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	
	<link rel="stylesheet" type="text/css" href="">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>

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
		vertical-align: middle;
		text-decoration: none;
		margin: 10px;
	}
	
	a.btn:hover {
		box-shadow: 5px 5px 8px rgba(80, 80, 80, 0.8);
	}
	
</style>
<script type="text/javascript">
	$(function() {
		$("a#btn-update").click(function() {
			document.location.href = "${rootPath}/iolist/update?id=${IO_VO.io_seq}"
		})
		
		$("a#btn-delete").on("click",function() {
			document.location.replace("${rootPath}/iolist/delete?id=${IO_VO.io_seq}")
		})
		/*
			document.location.href = 는 새로운 페이지로 넘겨주는 것이고
			document.location.replace()는 현재 페이지를 다른 파일로 바꾸는 것이다
			따라서 href는 새 페이지에서 뒤로가기를 하면 이전 창이 다시 뜨지만
			replace는 뒤로가기를 하면 이이전의 창이 뜨게 된다
		*/
	})
</script>
<body>
	<table>
		<caption>매입매출 상세</caption>
		<tr>
			<th>SEQ</th><td>${IO_VO.io_seq}</td>
			<th>거래구분</th><td>${IO_VO.io_inout}</td>
		</tr>
		
		<tr>
			<th>거래일자</th><td colspan="3">${IO_VO.io_date}</td>
		</tr>
		
		<tr>
			<th>거래처코드</th><td>${IO_VO.io_dcode}</td>
			<th>거래처명</th><td>${IO_VO.io_dname}( ${IO_VO.io_dceo} )</td>
		</tr>
		
		<tr>
			<th>상품코드</th><td>${IO_VO.io_pcode}</td>
			<th>상품명</th><td>${IO_VO.io_pname}</td>
		</tr>
		
		<tr>
			<th>수량</th><td>${IO_VO.io_qty}</td>
			<th>단가</th><td>${IO_VO.io_price}</td>
		</tr>
		
		<tr>
			<th>합계</th><td colspan="3">${IO_VO.io_total}</td>
		</tr>
	</table>
	<br/><br/>
	<div class="btn-box">
		<a href="javascript:void(0)" class="btn" id="btn-update">수정</a>
		<a href="javascript:void(0)" class="btn" id="btn-delete">삭제</a>
	</div>
</body>
</html>