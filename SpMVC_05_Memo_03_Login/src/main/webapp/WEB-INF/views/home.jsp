<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>		
		<style>
			* {
				box-sizing: border-box;
				margin: 0;
				padding: 0;
			}
			html, body {
				height: 100%;
			}
			body {
				width: 978px;
				display: flex;
				flex-flow: column wrap;
				margin: 0 auto;
			}
			header {
				background: #ccc;
				text-align: center;
				padding: 0.8rem;
			}
			
			section#main-body {
				/*
					아래의 내용을 한 줄로 표시할 때는
					flex: 1 0 auto 로 쓸 수 있다
					header와 footer 사이에서 section 부분 box를 화면 가득 채우기 위한 설정
				*/
				flex-grow: 1; /* 최대화 크기로 */
				flex-shrink: 0; /* 최소화 했을 때 */
				flex-basis: auto;
				background-color: #ddd;
				
				display: flex;
			}
			
			section#main-body article {
				flex: 3;
				border: 1px solid red;
			}

            section#main-body article article.list {
				border: 1px solid red;
                height: 80%;
                overflow: auto;
            }

            div.b-box {
                display: flex;
                justify-content: center;
                align-items: center;
                padding: 0.8rem;
            }

            div.b-box button {
                background-color: orange;
                color: blue;
                font-weight: bold;
                padding: 8px 16px;
                border: 0px;
                border-radius: 5px;
            }

            div.b-box button:hover {
                background-color: #ddd;
            }

            div.s-box {
                width: 100%;
                border: 1px solid blue;
                margin-bottom: 5px;
            }

            div.s-box input {
                width: 90%;
                display: block;
                margin: 10px auto;
            }
			
			section#main-body aside {
				background-color: white;
				flex: 1;
				border: 1px solid blue;
                padding: 16px;
			}

            section#main-body aside ul{
                list-style: none;
                margin-left: 16px;
            }

            section#main-body aside li a{
                /* a tag에 width나 height를 설정하려면 display나 inline-block으로 설정해야 한다 */
                display: inline-block;
                width: 100px;
                border-bottom: 1px solid blue;
                padding: 10px 3px;
                text-decoration: none;
            }

            section#main-body aside li a:hover{
                background-color: #ccc;
            }
			
			footer {
				flex: 0 0 auto;
				background: green;
				color: white;
				text-align: center;
				padding: 0.2rem;
			}
			
			nav ul {
				list-style: none;
				background-color: blue;
				display: flex;
				color: white;
			}
			
			nav li:nth-child(2) {
				margin-left: auto;
			}
			
			nav li:nth-child(4) {
				margin-left: auto;
			}
			
			nav ul a {
				text-decoration: none;
				display: inline-block;
				padding: 8px;
				margin: 5px;
				color: inherit;
			}
		</style>
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
		<script src="${rootPath}/js/jquery-3.4.1.js"></script>
		<script>
			$(function() {
				$("#btn-insert").click(function() {
					document.location.href = "${rootPath}/memo/insert"
				})
			})
		</script>
	</head>
	<body>
		<header>
        	<h3>심플 메모장</h3>
        </header>
        
        <nav>
        	<ul>
        		<li><a href="${rootPath}/">홈으로</a></li>
        		<li><a href="${rootPath}/">메뉴1</a></li>
        		<li><a href="${rootPath}/">메뉴2</a></li>
        		
        		<c:if test="${userDTO == null}">
	        		<li><a href="${rootPath}/member/login">로그인</a></li>
	        		<li><a href="${rootPath}/member/join">회원가입</a></li>
        		</c:if>
        		
        		<c:if test="${userDTO != null && userDTO.u_id != null}">
	        		<li><a href="${rootPath}/member/logout">로그아웃</a></li>
	        		<li><a href="${rootPath}/member/mypage">${userDTO.u_name}</a></li>
        		</c:if>
        	</ul>
        </nav>
        
	    <section id="main-body">
	        <article>
	            <div class="s-box">
	                <form>
	                    <input type="text" name="search">
	                </form>
	            </div>
	            <article class="list">
	                <%@ include file="/WEB-INF/views/memo-list.jsp" %>
	            </article>
	            <div class="b-box">
	                <button id="btn-insert">메모작성</button>
	            </div>
	        </article>
	        
	        <aside>
	            <ul>
	                <li><a href="#">오늘 할 일</a></li>
	                <li><a href="#">약속</a></li>
	                <li><a href="#">중요메모</a></li>
	            </ul>
	        </aside>
	    </section>
	    
	    <footer>
	        <address>CopyRight &copy; sianblone@gmail.com</address>
	    </footer>
	</body>
</html>
