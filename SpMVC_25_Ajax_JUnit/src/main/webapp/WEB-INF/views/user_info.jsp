<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style>
	p {
		font-size: large;
	}

	span {
		color: blue;
		font-weight: bold;
		font-style: italic;
	}
</style>



<p>사용자 ID : <span>${userVO.userId}</span></p>
<p>비밀번호 : <span>${userVO.password}</span></p>
<p>사용자 이름 : <span>${userVO.userName}</span></p>
<p>사용자 권한 : <span>${userVO.role}</span></p>