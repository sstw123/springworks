<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<script>
	$(function() {
		$("#btn_add").on("click", function() {
			document.location.href = "${rootPath}/frd/input"
		})
		$("tbody tr").on("click", function() {
			document.location.href = "${rootPath}/frd/view?frd_id=" + $(this).data("id")
		})
	})
</script>
<style>
	.search_form {
		display:flex;
		margin-top: 10px;
	}
	#search_opt {
		margin-left: auto;
	}
	#search_btn {
		cursor: pointer;
	}
	
	table {
		width: 80%;
		margin: 10px auto;
		border-collapse: collapse;
	}
	td,th {
		text-align: center;
		padding: 10px 0;
	}
	thead tr {
		background-color: #7AEBFF;
	}
	tbody tr:hover {
		background-color: black;
		color: white;
		cursor: pointer;
	}
	
	.btn_box {
		width: 80%;
		margin: 0 auto;
		text-align: right;
	}
	
</style>
<article>
	<form class="search_form" action="${rootPath}/frd/search">
		<select id="search_opt" name="search_opt">
			<option value="all">전체</option>
			<option value="name">이름</option>
			<option value="tel">전화번호</option>
		</select>
		<input name="search_text" type="text">
		<button id="search_btn">검색</button>
	</form>
</article>

<article>
	<table>
		<thead>
			<tr>
				<td>이름</td>
				<td>전화번호</td>
				<td>주소</td>
				<td>취미</td>
				<td>관계</td>
			</tr>
		</thead>
		<tbody>
			<c:choose>
				<c:when test="${empty FRD_LIST}">
					<tr>
						<td colspan="5">데이터가 없습니다</td>
					</tr>
				</c:when>
				<c:when test="${not empty FRD_LIST}">
					<c:forEach items="${FRD_LIST}" var="FRD">
						<tr data-id="${FRD.frd_id}">
							<td>${FRD.frd_name}</td>
							<td>${FRD.frd_tel}</td>
							<td>${FRD.frd_addr}</td>
							<td>${FRD.frd_hobby}</td>
							<td>${FRD.frd_rel}</td>
						</tr>
					</c:forEach>
				</c:when>
			</c:choose>
		</tbody>
	</table>
</article>

<article class="btn_box">
	<button id="btn_add">추가</button>
</article>