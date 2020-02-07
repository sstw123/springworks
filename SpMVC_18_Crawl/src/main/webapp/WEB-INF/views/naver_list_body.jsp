<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!--
	col-lg : >= 992px 
	col-md : >= 768px
	col-sm : >= 576px
	
	col-lg-2 : width가 992px 이상이면 2/12만큼의 크기를 1개의 box로 설정
	col-md-4 : width가 992px 미만이고 768px 이상이면 4/12만큼의 크기를 1개의 box로 설정
	col-sm-12 : width가 768px 미만이고 576px 이상이면 12/12만큼의 크기를 1개의 box로 설정
	이 경우 576 미만이 되면 따로 지정해주지 않았기에 sm 형식의 설정값이 유지된다
-->
<article class="col-lg-2 col-md-4 col-sm-12">
	<section class="card">
		<article class="card-header">
			${NAVER.m_title}
		</article>
		
		<article class="card-body">
			<img src="${NAVER.m_image_url}" width="100%">
		</article>
		
		<article class="card-footer">
			<a href="${NAVER.m_detail_url}">자세히 보기</a>
		</article>
	</section>
</article>