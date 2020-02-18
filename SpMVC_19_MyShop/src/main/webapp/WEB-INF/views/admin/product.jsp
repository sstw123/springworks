<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<style>
	.in-errors {
		color: red;
		font-size: 8px;
	}
	/*
		col-md-7 col-12
		해상도가 768 이상이면 7칸
		그 미만이면 12칸 = 최대 width
	*/
	tr, th, td {
		white-space: nowrap;
	}
	
	.list-body {
		overflow: auto;
	}
	
	td.p_name {
		display:inline-block;
		width: 150px;
		padding: 0 5px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>
<section class="mt-5 row">
	<article class="col-xl-7 col-sm-12 bg-light pro-input">
		<c:choose>
			<c:when test="${PRO_BODY == 'DETAIL'}">
				<%@ include file="/WEB-INF/views/admin/product_detail.jsp" %>
			</c:when>
			<c:otherwise>
				<%@ include file="/WEB-INF/views/admin/product_input.jsp" %>
			</c:otherwise>
		</c:choose>
	</article>
	
	<article class="col-xl-4 col-sm-12 bg-light list-body">
		<%@ include file="/WEB-INF/views/admin/product_list.jsp" %>
	</article>
</section>