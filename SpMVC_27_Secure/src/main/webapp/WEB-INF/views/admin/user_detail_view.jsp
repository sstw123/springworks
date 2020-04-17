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
	
	form label {
		display: inline-block;
		width: 150px;
		text-align: right;
	}
	
	form div.password {
		display: none;
	}
</style>
<form:form class="mypage_form" action="${rootPath}/user/mypage">
	<div>
		<span>아이디</span>
		<span>${USER_VO.username}</span>
	</div>
	
	<div>
		<span>활성여부</span>
		<select name="enabled">
			<option value="true" <c:if test="${USER_VO.enabled == true}">selected="selected"</c:if>>활성화</option>
			<option value="false" <c:if test="${USER_VO.enabled == false}">selected="selected"</c:if>>비활성화</option>
		</select>
	</div>
	
	<div>
		<label for="email">이메일</label>
		<input id="email" name="email" value="${USER_VO.email}" />
	</div>
	
	<div>
		<label for="phone">핸드폰</label>
		<input id="phone" name="phone" value="${USER_VO.phone}" />
	</div>
	
	<div>
		<label for="address">주소</label>
		<input id="address" name="address" value="${USER_VO.address}" />
	</div>
	
	<c:forEach items="${USER_VO.authorities}" var="auth" varStatus="s">
		<div>
			<label for="auth">권한${s.count}</label>
			<input class="auth" name="auth" value="${auth.authority}" >
		</div>
	</c:forEach>
	
	<div id="auth_box">
		<button id="auth_append" type="button">권한 추가</button>
	</div>
	
	<button id="btn_edit" type="button" data-id="${USER_VO.username}">수정</button>
</form:form>