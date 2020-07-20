<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>국가지원금 안내</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script src="${rootPath}/js/include-search.js"></script>
	<link rel="stylesheet" type="text/css" href="${rootPath}/css/include-search.css">
</head>
<body>
	<div class="tips">
		<p class="tip">입력한 정보를 기준으로 받을 수 있는 지원금을 계산합니다</p>
		<p class="tip">입력한 정보는 어디에도 저장되지 않고 계산을 위한 조건값으로만 사용되며</p>
		<p class="tip">조건값이 많을수록 검색 결과의 정확도가 올라갑니다</p>
	</div>

	<form:form action="${rootPath}/link" modelAttribute="moneySearchDTO">
		<fieldset>
			<h3 id="necessary">- 필수입력 -</h3>
			<p class="q-text"><span style="color:blue">주민등록 기준 생년월일과 성별을 입력하세요</span><span class="star"> *</span></p>
			<div class="birth">
				<input type="text" name="birth" placeholder="예) 19890601" maxlength="8" id="birth">
				<input type="hidden" name="gender" id="gender">
				<button type="button" class="gender" id="male" data-gender="0">남성</button>
				<button type="button" class="gender" id="female" data-gender="0">여성</button>
		    </div>
			
			<h3>- 선택입력 -</h3>
			<p class="tip">해당하는 사항이 있다면 입력해주세요</p>
			
			<div>
				<label for="charTrgterArray">유형</label>
				<form:select path="charTrgterArray" label="유형" >
					<form:options items="${SelectApiMap.charTrgterArray}" />
				</form:select>
			</div>
			
			<div class="disorder blind">
				<label for="obstKiArray">장애유형</label>
				<form:select path="obstKiArray" label="장애유형" >
					<form:options items="${SelectApiMap.obstKiArray}" />
				</form:select>
			</div>
			
			<div class="disorder blind">
				<label for="obstAbtArray">장애정도</label>
				<form:select path="obstAbtArray" label="장애정도" >
					<form:options items="${SelectApiMap.obstAbtArray}" />
				</form:select>
			</div>
			
			<div>
				<label for="trgterIndvdlArray">가구유형</label>
				<form:select path="trgterIndvdlArray" label="가구유형" >
					<form:options items="${SelectApiMap.trgterIndvdlArray}" />
				</form:select>
			</div>
			
			<div>
				<label for="desireArray">관심주제</label>
				<form:select path="desireArray" label="관심주제" >
					<form:options items="${SelectApiMap.desireArray}" />
				</form:select>
			</div>
			
			
			<br/><p class="more close">찾는 검색어가 있으신가요? <span id="open">(열기)</span></p>
			<div class="searchWord blind">
				<form:select path="srchKeyCode" label="검색분류" >
					<form:options items="${SelectApiMap.srchKeyCode}" />
				</form:select>
				<form:input path="searchWrd" label="검색어" />
			</div>
			
			<div>
				<button id="btn-search" type="button">조회</button>
			</div>
		</fieldset>
	</form:form>	
</body>
</html>