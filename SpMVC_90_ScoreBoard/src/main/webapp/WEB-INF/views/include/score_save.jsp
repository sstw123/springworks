<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<form action="${rootPath}/save" method="POST">
	<article>
		<h4>필수입력</h4>
		<input id="id" name="id" value="${SC_VO.id}" type="hidden">
		<div>
			<label for="st_num">학번</label><br/>
			<input id="st_num" name="st_num" value="${SC_VO.st_num}"/>
		</div>
		
		<div>
			<label for="st_name">이름</label><br/>
			<input id="st_name" name="st_name" value="${SC_VO.st_name}"/>
		</div>
		
		<div>
			<label for="st_grade">학년</label><br/>
			<input id="st_grade" name="st_grade" value="${SC_VO.st_grade}"/>
		</div>
		
		<div>
			<label for="st_class">반</label><br/>
			<input id="st_class" name="st_class" value="${SC_VO.st_class}"/>
		</div>
	</article>
	
	<article>
		<h4>선택입력</h4>
		<div>
			<label for="s_kor">국어점수</label><br/>
			<input id="s_kor" name="s_kor" value="${SC_VO.s_kor}"/>
		</div>
		
		<div>
			<label for="s_eng">영어점수</label><br/>
			<input id="s_eng" name="s_eng" value="${SC_VO.s_eng}"/>
		</div>
		
		<div>
			<label for="s_math">수학점수</label><br/>
			<input id="s_math" name="s_math" value="${SC_VO.s_math}"/>
		</div>
	</article>
	<button>저장</button>
</form>