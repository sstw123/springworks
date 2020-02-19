<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<script>
	$(function() {
		var toolbar = [ [ 'style', [ 'bold', 'italic', 'underline' ] ],
			[ 'fontsize', [ 'fontsize' ] ], [ 'font Style', [ 'fontname' ] ],
			[ 'color', [ 'color' ] ], [ 'para', [ 'ul', 'ol', 'paragraph' ] ],
			[ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
			[ 'insert', [ 'link', 'hr', 'picture' ] ],
			[ 'view', [ 'fullscreen', 'codeview' ] ] ]

		$("#p_detail").summernote({
			lang : "ko-KR",
			height : "100%",
			width : "100%",
			toolbar : toolbar
		})
	})
</script>
<form:form modelAttribute="productVO" action="${rootPath}/admin/product/input">
	<div class="form-group">
		<form:textarea class="form-control" rows="" cols="" placeholder="상세정보" path="p_detail"/>
	</div>
	
	<div class="form-group">
		<button>저장</button>
	</div>
</form:form>