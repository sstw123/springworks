<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<table>
	<tr>
		<th>이름</th>
		<td id="a_name">${dto.a_name}</td>
	</tr>
	<tr>
		<th>주소</th>
		<td>${dto.a_addr}</td>
	</tr>
	<tr>
		<th>전화</th>
		<td>${dto.a_tel}</td>
	</tr>
	<tr>
		<th>나이</th>
		<td>${dto.a_age}</td>
	</tr>
</table>