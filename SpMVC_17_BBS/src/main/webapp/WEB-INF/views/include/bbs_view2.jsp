<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<script>
	$(function() {
		$("#btn_delete").on("click", function() {
			if(confirm("정말로 삭제하시겠습니까?")) {
				document.location.href = "${rootPath}/bbs/delete?bbs_id=" + ${BBS_VO.bbs_id}
			}
			alert("삭제되었습니다")
		})
	})
</script>
<article class="container">
	<table class="table table-bordered table-hover">
		<tr>
			<th>작성자</th>
			<td>${BBS_VO.bbs_writer}</td>
			<th>작성일자</th>
			<td>${BBS_VO.bbs_date}</td>
			<th>작성시간</th>
			<td>${BBS_VO.bbs_time}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td colspan="5">${BBS_VO.bbs_subject}</td>
		</tr>
		<tr>
			<td colspan="6">${BBS_VO.bbs_content}</td>
		</tr>
	</table>
</article>
<div class="input-group">
	<div class="input-group-btn">
		<button class="btn btn-primary" id="btn_delete" type="button">삭제</button>
	</div>
</div>