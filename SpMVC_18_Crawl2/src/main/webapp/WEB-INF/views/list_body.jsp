<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p>검색구간 : ${CRAWL_DTO.srchStartDate} ~ ${CRAWL_DTO.srchLastDate}</p>
<p>총 조회수 : ${CRAWL_DTO.sumOfHit} / 평균 조회수 : ${CRAWL_DTO.avgOfHit} / 총 게시물 수 : ${CRAWL_DTO.numOfTuples}</p>
<c:forEach var="CRAWL" items="${CRAWL_DTO.crawlSubList}">
	<p>글번호:${CRAWL.c_bbsNo} 날짜/시간:${CRAWL.c_date} 조회수:${CRAWL.c_hit}</p>
</c:forEach>
<%@ include file="/WEB-INF/views/pagination.jsp" %>