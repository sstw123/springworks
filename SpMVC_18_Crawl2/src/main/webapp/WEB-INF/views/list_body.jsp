<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<article>
	<form:form modelAttribute="CRAWL_DTO" method="GET">
		<label for="srchStartDate">시작날짜</label>
		<form:input path="srchStartDate" placeholder="날짜입력"/>
		
		<label for="srchLastDate">마지막날짜</label>
		<form:input path="srchLastDate" placeholder="날짜입력"/>
		
		<form:button>검색</form:button>
	</form:form>
</article>

<h3>${HEADER}</h3>

<form:form modelAttribute="CRAWL_DTO" method="POST" action="${URL}/save">
	<form:hidden path="crawlSiteURL" value="${SITE_URL}"/>
	<form:button type="submit">크롤링 DB 저장</form:button>
</form:form>

<form:form modelAttribute="CRAWL_DTO" method="POST" action="${URL}/delete">
	<form:button type="submit">사이트/게시판 별 DB 삭제</form:button>
</form:form>

<p>검색구간 : ${CRAWL_DTO.srchStartDate} ~ ${CRAWL_DTO.srchLastDate}</p>
<p>총 조회수 : ${CRAWL_DTO.sumOfHit} / 평균 조회수 : ${CRAWL_DTO.avgOfHit} / 총 게시물 수 : ${CRAWL_DTO.numOfTuples}</p>
<c:forEach var="CRAWL" items="${CRAWL_DTO.crawlSubList}">
	<p>글번호:${CRAWL.c_bbsNo} 날짜/시간:${CRAWL.c_date} 조회수:${CRAWL.c_hit}</p>
</c:forEach>
<%@ include file="/WEB-INF/views/pagination.jsp" %>