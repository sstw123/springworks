<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	section {
		width: 70%;
		margin: 10px auto;
	}
	form {
		width: 70%;
		margin: 10px auto;
	}
	
	.mypage_form_item, .new_auth_item {
		display: flex;
		align-items: center;
		padding: 5px 0;
	}
	
	.label {
		display: inline-block;
		text-align: right;
		width: 20%;
		margin-right: 20px;
	}
	
	.data {
		padding: 0.5rem 1rem;
		width: 60%;
	}
	
	form div.password {
		display: none;
	}
	
	.btn_box {
		display: flex;
	}
	
	.btn_box button {
		display: block;
		width: 100px;
		padding: 10px;
		margin-top: 20px;
	}
	#btn_edit {
		margin-left: auto;
	}
</style>
<form:form class="mypage_form" action="${rootPath}/user/mypage">
	<div class="mypage_form_item">
		<span class="label">아이디</span>
		<span class="data">${USER_VO.username}</span>
	</div>
	
	<div class="mypage_form_item">
		<span class="label">활성여부</span>
		<select class="data" name="enabled">
			<option value="true" <c:if test="${USER_VO.enabled == true}">selected="selected"</c:if>>활성화</option>
			<option value="false" <c:if test="${USER_VO.enabled == false}">selected="selected"</c:if>>비활성화</option>
		</select>
	</div>
	
	<div class="mypage_form_item">
		<label class="label" for="email">이메일</label>
		<input class="data" id="email" name="email" value="${USER_VO.email}" />
	</div>
	
	<div class="mypage_form_item">
		<label class="label" for="phone">핸드폰</label>
		<input class="data" id="phone" name="phone" value="${USER_VO.phone}" />
	</div>
	
	<div class="mypage_form_item">
		<label class="label" for="address">주소</label>
		<input class="data" id="address" name="address" value="${USER_VO.address}" />
	</div>
	
	<c:forEach items="${USER_VO.authorities}" var="auth" varStatus="s">
		<div class="mypage_form_item">
			<label class="label">권한${s.count}</label>
			<input class="data" name="auth" value="${auth.authority}" >
		</div>
	</c:forEach>
	
	<div id="auth_box">
	</div>
	
	<div class="btn_box">
		<button id="btn_add_auth" type="button">권한 추가</button>
		<button id="btn_edit" type="button" data-id="${USER_VO.username}">수정</button>
	</div>
	
</form:form>