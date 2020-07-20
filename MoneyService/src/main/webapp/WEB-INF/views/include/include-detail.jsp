<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>국가지원금 안내</title>
</head>
<body>
	<section>
		<p>서비스ID : ${DETAIL_VO.servId}
		<p>서비스명 : ${DETAIL_VO.servNm}
		<p>소관부처명 : ${DETAIL_VO.jurMnofNm}
		<p>서비스 요약 : ${DETAIL_VO.servDgst}
		<p>대상자 상세내용 : ${DETAIL_VO.tgtrDtlCn}
		<p>선정기준 내용 : ${DETAIL_VO.slctCritCn}
		<p>급여서비스 내용 : ${DETAIL_VO.alwServCn}
		<p>생애주기 코드 : ${DETAIL_VO.servSeCode}
		
		<p>서비스 이용 및 신청방법 : ${DETAIL_VO.applmetList.servSeDetailNm}
		<p><img src="${DETAIL_VO.applmetList.servSeDetailLink}" alt="신청방법">
		
		<p>문의처 : ${DETAIL_VO.inqplCtadrList.servSeDetailNm} (${DETAIL_VO.inqplCtadrList.servSeDetailLink})

		<p>사이트명 : <a href="${DETAIL_VO.inqplHmpgReldList.servSeDetailLink}">${DETAIL_VO.inqplHmpgReldList.servSeDetailNm}</a>
		
		<p>서식/자료명 : <a href="${DETAIL_VO.basfrmList.servSeDetailLink}">${DETAIL_VO.basfrmList.servSeDetailNm}</a>
		
		<p>근거법령명 : <a href="${DETAIL_VO.baslawList.servSeDetailLink}">${DETAIL_VO.baslawList.servSeDetailNm}</a>
	
	</section>
</body>
</html>