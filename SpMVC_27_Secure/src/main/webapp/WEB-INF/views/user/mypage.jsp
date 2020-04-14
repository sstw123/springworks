<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<script>
	$(function() {
		$("#btn_edit").click(function() {
			if(confirm("이대로 수정하시겠습니까?")) {
				$(".mypage_form").submit()
			}
		})
	})
</script>

<form:form class="mypage_form">
	<div>
		<label for="username">아이디</label>
		<input id="username" name="username" value="${prcp.principal.username}" />
	</div>
	
	<div>
		<span>활성여부</span>
		<span>${prcp.principal.enabled}</span>
	</div>
	
	<c:forEach items="${prcp.principal.authorities}" var="enabled" varStatus="s">
		<div>
			<span>권한${s.count}</span>
			<span>${enabled.authority}</span>
		</div>
	</c:forEach>
	
	<div>
		<label for="email">이메일</label>
		<input id="email" name="email" value="${prcp.principal.email}" />
	</div>
	
	<div>
		<label for="phone">핸드폰</label>
		<input id="phone" name="phone" value="${prcp.principal.phone}" />
	</div>
	
	<div>
		<label for="address">주소</label>
		<input id="address" name="address" value="${prcp.principal.address}" />
	</div>
	
	<button id="btn_edit" type="button">수정</button>
</form:form>

<button id="btn_exit" type="button">회원탈퇴</button>