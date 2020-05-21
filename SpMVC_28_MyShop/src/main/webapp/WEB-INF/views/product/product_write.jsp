<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function () {
			$(document).on("blur", "#p_code", function() {
				let p_code = $("#p_code").val()
				if(p_code == "") {
					$("#p_code_error").html("<b>* 상품코드를 입력하세요.</b>")
					$("#p_code").focus()
					return false
				}
				
				$.ajax({
					url: "${rootPath}/product/code_check",
					data: {
						"${_csrf.parameterName}" : "${_csrf.token}",	
						p_code : p_code
					},
					type: "POST",
					success: function(result) {
						if(result) {
							$("#p_code_error").html("* 이미 등록된 코드입니다.")
							$("#p_code").focus()
							return false
						} else {
							$("#p_code_error").html("<font color='blue'>* 사용할 수 있는 코드입니다.</font>")
						}
					},
					error: function(error) {
						$("#p_code_error").html(error)
					}
				})
			})
			
			$(document).on("click", "button.save", function() {
				let p_code = $("#p_code").val()
				if(p_code == "") {
					$("#p_code_error").html("<b>* 상품코드를 입력하세요.</b>")
					$("#p_code").focus()
					return false
				}
				
				$("form").submit()
			})
		})		
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<section class="container body">
		<form:form modelAttribute="productVO">
			<fieldset>
				<legend>상품정보 등록</legend>
				
				<div class="form-group">
					<form:input path="p_code" class="form-control" placeholder="상품코드" />
				</div>
				
				<div id="p_code_error" class="text-danger">
				</div>
				
				<div class="form-group">
					<form:input path="p_name" class="form-control" placeholder="상품명" />
				</div>
				
				<div class="form-group">
					<form:input path="p_iprice" class="form-control" placeholder="매입가격" />
				</div>
				
				<div class="form-group">
					<form:input path="p_oprice" class="form-control" placeholder="판매가격" />
				</div>
				
				<div class="button-group">
					<button class="btn btn-primary save" type="button">저장</button>
					<button class="btn btn-success list" type="button">목록</button>
				</div>
			</fieldset>
		</form:form>
	</section>
	
	
</body>
</html>