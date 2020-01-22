<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="img_panel img_card" data-file="${imageVO.img_file}" data-seq="${imageVO.img_seq}">

	<c:if test="${empty imageVO.img_file}">
		<img src="${rootPath}/images/noimage.png" height="80%" width="100%" alt="noimage">
	</c:if>
	<c:if test="${!empty imageVO.img_file}">
		<img src="${rootPath}/images/${imageVO.img_file}" height="80%" width="100%" alt="main-img">
	</c:if>
	<div class="img_title">
		<h4>${imageVO.img_title}</h4>
	</div>
</div>