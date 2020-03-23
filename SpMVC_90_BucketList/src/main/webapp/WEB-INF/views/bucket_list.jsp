<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	/* bucket_list css */
	table {
		border-collapse: collapse;
		width: 100%;
	}
	tbody tr:hover {
		background-color: #eee;
		cursor: pointer;
	}	
	th, td {
		border-bottom: 1px solid #ddd;
		padding: 15px 0;
	}
	th, td.center {
		text-align: center;
	}
	.th_order, .th_success {
		width: 40px;
		min-width: 40px;
	}
	td.input_box {
		display: none;
	}
	input.b_content_input {
		font-size: large;
		width: 90%;
	}
	.btn_edit_done {
		width: 10%;
		padding: 10px 0;
	}
	
</style>
<table>
	<thead>
		<tr>
			<th class="th_order">번호</th>
			<th>내용</th>
			<th class="th_success">성공</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty BUCKET_LIST}">
				<tr>
					<td class="center" colSpan="3">버킷리스트를 추가하세요!</td>
				</tr>
			</c:when>
			<c:when test="${!empty BUCKET_LIST}">
				<c:forEach var="bucket" items="${BUCKET_LIST}" varStatus="status">
					<tr data-id="${bucket.b_id}">
						<td class="center">${bucket.b_order}</td>
						<td class="input_box"><input class="b_content_input"/><button class="btn_edit_done">저장</button></td>
						<td class="b_content">${bucket.b_content}</td>
						<c:choose>
							<c:when test="${bucket.b_success == true}">
								<td class="success center">&#10003;</td>
							</c:when>
							<c:when test="${bucket.b_success == false}">
								<td class="success center"></td>
							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</tbody>
</table>