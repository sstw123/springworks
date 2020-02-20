<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<section class="mt-5 row justify-content-center">
	<article class="col-xl-7 col-sm-12 bg-light dept-input">
		<c:choose>
			<c:when test="${DEPT_BODY == 'DETAIL'}">
				<%@ include file="/WEB-INF/views/admin/dept_detail.jsp" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/views/admin/dept_input.jsp" %>
			</c:otherwise>
		</c:choose>
	</article>
	
	<article class="col-xl-4 col-sm-12 bg-light list-body">
		<%@ include file="/WEB-INF/views/admin/dept_list.jsp" %>
	</article>
</section>