<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<script>
	$(function() {
		$(".info_btn").on("click", function() {
			let text = $(this).text()
			
			if(text == "수정") {
				document.location.href = "${rootPath}/frd/edit?frd_id=" + ${FRD_INFO.frd_id}
			} else if(text == "삭제") {
				if(confirm("정말로 삭제하시겠습니까?")) {
					document.location.href = "${rootPath}/frd/delete?frd_id=" + ${FRD_INFO.frd_id}
				}
			} else if(text == "목록") {
				document.location.href = "${rootPath}/frd/list"
			}
		})
	})
</script>
이름 : ${FRD_INFO.frd_name}
전화번호 : ${FRD_INFO.frd_tel}
주소 : ${FRD_INFO.frd_addr}
취미 : ${FRD_INFO.frd_hobby}
관계 : ${FRD_INFO.frd_rel}
<button class="info_btn" type="button">수정</button>
<button class="info_btn" type="button">삭제</button>
<button class="info_btn" type="button">목록</button>