<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<form:form modelAttribute="imageVO">
	<fieldset>
		<legend>이미지 갤러리 작성</legend>
		<div class="input_box">
			<form:input type="text" path="img_title" placeholder="제목을 입력하세요" />
		</div>
		
		<div class="input_box">
			<form:textarea path="img_text" placeholder="제목을 입력하세요" />
		</div>
		
		<div class="input_box">
			<form:hidden path="img_file"/>
			<div id="DragAndDrop">
				<h3>이미지를 올려놓으세요</h3>
				<img id="img_view" height="95%" alt="img_view">
			</div>
		</div>
		
		<c:if test="${!empty imageVO.img_file}">
			<div class="input_box">
				<img src="${rootPath}/images/${imageVO.img_file}" width="200px" alt="img_file">
			</div>
		</c:if>
		
		<c:if test="${!empty imageVO.img_files}">
			<div class="input_box">
				<div class="img_list">
					<c:forEach items="${imageVO.img_files}" var="images">
						<div class="img_view" data-id="${images.img_file_seq}" data-seq="${imageVO.img_seq}">
							<img src="${rootPath}/images/${images.img_file_upload_name}" alt="img">
						</div>
					</c:forEach>
				</div>
			</div>
		</c:if>
		
		<div class="input_box" id="btn_save">
			<button class="bz_button">저장</button>
			<button class="bz_button" id="btn_list" type="button" onclick="location.href='${rootPath}/'">목록</button>
		</div>
	</fieldset>
</form:form>