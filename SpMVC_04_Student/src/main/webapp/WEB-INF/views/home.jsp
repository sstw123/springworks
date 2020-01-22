<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta charset="UTF-8" name="viewport" content="width=device-width, initian-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
<style>
/*
	header : tag 이름
	header {} : 해당 tag의 모양에 대한 여러가지 속성 지정 
*/
*,html,body {
	margin: 0;
	padding: 0;
}
header {
	background-color: green; /* box의 바탕색 지정 */
	color: white; /* header box 내의 문자열 글자색상 지정 */
	width: 50%; /* box의 크기이며 브라우저 창 크기에서 지정한 %만큼을 사용한다 */
	margin: 50px; /* box 바깥쪽 여백 */
	padding: 1rem; /* box 안쪽 여백 */
	/* px, pt, dt, st, em, rem */
}

ul {
	/*
	- display: flex -
	box model tag의 속성을
	inline-box 속성으로 변경하는 것
	*/
	display: flex;
	list-style: none; /* list의 머릿글 제거 */
}

li {
	width: 100px;
	margin-right: 10px;
	background-color: green;
}

/* 태그만 시작하면 본문에 있는 모든 tag에 적용*/
a {
	text-decoration: none; /* a 태그에 적용되어 밑줄을 없애는 용도 */
	padding: 10px;
}

p {
	background-color: cyan;
}

nav a:hover { /* 밑줄을 없앴기 때문에 마우스를 올려도 누를지 말지 직관적으로 알기 힘들어지기 때문에 a:hover(마우스오버) 태그를 지정해준다 */
/*
a:hover 액션을 지정하는데 nav tag 내에 있는 a tag에만 액션을 지정한다
앞에 띄어쓰기가 있다면 해당 태그 내의 a태그에만 style을 적용한다는 뜻이다
*/
	font-weight: bold; /* bold는 font-weight에 들어있다 */
	font-style: italic; /* italic은 font-style에 들어있다 */
}

/* 이 문서에 p1이라는 id가 하나밖에 없을 경우, p#p1 대신 #p1으로 지정해도 된다 */
p#p1 {
	font-size: 50pt;
	background-color: blue;
	color: white;
}

/*
이 문서에 class가 pc로 지정된 tag의 스타일 변경하기
p.pc 또는 .pc로 지정해도 된다 */
p.pc {
	background-color: red;
	color: white;
}



</style>
</head>
<body>

<header>
	<h3>나의 홈페이지</h3>
</header>

<nav>
	<ul>
		<li><a href="#">학생리스트</a></li>
		<li><a href="#">학생검색</a></li>
		<li><a href="#">로그인</a></li>
		<li><a href="#">회원가입</a></li>
	</ul>
</nav>

<section>
	<article>
		<p><font size="30pt" color="blue" face="궁서">여기는 본문 부분입니다</font></p>
		<p style="font-size: 50pt; color=red; background-color: yellow">여기는 또다른 본문입니다</p>
		<p>여기는 나의 이야기 입니다</p>
		<p id="p1">여기는 p1 입니다</p>
		<p id="p1">여기는 p1 입니다</p>
		<p id="p2">여기는 p2 입니다</p>
		<p id="p3" class="pc">여기는 p3 입니다</p>
		<p id="p4" class="pc">여기는 p4 입니다</p>
		<p id="p5" class="pc">여기는 p5 입니다</p>
		<p><a href="https://www.google.com/">구글 바로가기</a>
			<a href=htts://www.daum.net/">다음 바로가기</a>
			</p>
	</article>
</section>

<footer>
	<address>CopyRight &copy; sianblone@gmail.com</address>
</footer>

</body>
</html>
