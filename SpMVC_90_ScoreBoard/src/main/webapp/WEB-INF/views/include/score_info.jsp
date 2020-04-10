<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	$(function() {
		
		$("#btn-edit").click(function() {
			document.location.href = "${rootPath}/save?id=" + ${SC_VO.id}
		})
		
		$("#btn-delete").click(function() {
			if(confirm("정말로 삭제하시겠습니까?")) {
				document.location.href = "${rootPath}/delete/" + ${SC_VO.id}
			}
		})
	})
</script>
<table>
	<tr>
		<th>학번</th>
		<td>${SC_VO.st_num}</td>
	</tr>
	<tr>
		<th>이름</th>
		<td>${SC_VO.st_name}</td>
	</tr>
	<tr>
		<th>학년</th>
		<td>${SC_VO.st_grade}</td>
	</tr>
	<tr>
		<th>반</th>
		<td>${SC_VO.st_class}</td>
	</tr>
	
	<tr>
		<th>국어</th>
		<td>${SC_VO.s_kor}</td>
	</tr>
	
	<tr>
		<th>영어</th>
		<td>${SC_VO.s_eng}</td>
	</tr>
	
	<tr>
		<th>수학</th>
		<td>${SC_VO.s_math}</td>
	</tr>
</table>
<button id="btn-edit" type="button" data-id="${SC_VO.id}">수정</button>
<button id="btn-delete" type="button" data-id="${SC_VO.id}">삭제</button>