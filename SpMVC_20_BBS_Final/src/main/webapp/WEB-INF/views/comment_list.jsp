<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />

<c:forEach items="${COMMENT_LIST}" var="CMT">
	<div class="row p-2 bg-light cmt-item" data-id="${CMT.c_id}">
		<div class="col-2 c_writer">${CMT.c_writer}</div>
		<div class="col-6 c_subject">${CMT.c_subject}</div>
		<div class="col-2 c_date_time">${CMT.c_date_time}</div>
		<div class="col-1 reply_comment">답글</div>
		<div class="delete_comment" data-c_id="${CMT.c_id}">&times;</div>
	</div>
</c:forEach>