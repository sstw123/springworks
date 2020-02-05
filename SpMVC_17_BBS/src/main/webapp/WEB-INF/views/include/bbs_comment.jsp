<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:if test="${not empty CMT_LIST}">
	<article class="row">
		<c:forEach var="CMT" items="${CMT_LIST}">
			<section class="card col-md-12">
					${CMT.cmt_writer} ${CMT.cmt_text} ${CMT.cmt_date}
			</section>
		</c:forEach>
	</article>
</c:if>