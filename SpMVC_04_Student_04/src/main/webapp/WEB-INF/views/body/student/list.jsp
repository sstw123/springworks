<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<p>
<p>
<p>

<style>
        table {
            /*
                border-collapse : table을 표시할 때 여백 없애기
                border-spacing : 셀과 셀 사이의 여백 없애기
                표를 실선모양으로 외부 라인 설정
            */
            /*
            border-collapse: collapse;
            border-spacing: 0px;
            */
            border: 1px solid blue;
            width: 50%;
            
            /*
            top, bottom 여백을 20px로 하고 left와 right 여백을 자동으로
            좌우 중앙에 box 위치
            */
            margin: 20px auto;
        }

        table tr {
            border: 1px dotted red;
        }
        
        
        table td, table th {
        	padding: 8px;
        	vertical-align: top;
        }
        
        table th {
        	text-align: left;
        }
        
        table tr:nth-child(odd) {
        	background-color: #fff;
        }
        
        table tr:nth-child(even) {
        	background-color: #ccc;
        }
        
        /*
            table row에 마우스가 위치하면 바탕색을 gray로 설정하고 마우스 커서를 손가락 모양으로
        */
        table tr:hover {
            background-color: yellow;
            cursor: pointer;
        }
    </style>
<table border="1">
	<tr>
		<th>번호</th>
		<th>이름</th>
		<th>학과</th>
		<th>학년</th>
		<th>전화번호</th>
	</tr>
	<c:choose>
		<c:when test="${STDLIST == null}">
			<tr>
				<td colspan="5">
				 데이터가 없습니다
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach items="${STDLIST}" var="dto">
				<tr>
					<td>${dto.st_num}</td>
					<td>${dto.st_name}</td>
					<td>${dto.st_dept}</td>
					<td>${dto.st_grade}</td>
					<td>${dto.st_tel}</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	
</table>
