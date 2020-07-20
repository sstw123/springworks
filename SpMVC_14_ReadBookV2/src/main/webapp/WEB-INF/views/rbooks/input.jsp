<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.js"></script>
	<script src="${rootPath}/js/summernote-ko-KR.js"></script>

	<script>var rootPath = "${rootPath}"</script>
	
	<script>
		$(function() {
			
			var toolbar = [ [ 'style', [ 'bold', 'italic', 'underline' ] ],
			[ 'fontsize', [ 'fontsize' ] ], [ 'font Style', [ 'fontname' ] ],
			[ 'color', [ 'color' ] ], [ 'para', [ 'ul', 'ol', 'paragraph' ] ],
			[ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
			[ 'insert', [ 'link', 'hr', 'picture' ] ],
			[ 'view', [ 'fullscreen', 'codeview' ] ] ]
			
			let rb_star = "${rBookVO.rb_star}"
			
			if(rb_star >= 1) {
				$("span.star_red").css("width", rb_star*10 + "%")
				$("span#star_v").text(rb_star)
			} else {
				$("span.star_red").css("width", "10%")
				$("span#star_v").text("1")
				
			}
			
			
			$("#rb_text").summernote({
				disableDragAndDrop : true,
				lang : 'ko-KR',
				placeholder : '본문을 입력하세요',
				width : '100%',
				height : '200px',
				toolbar : toolbar
			})
			
			$("#rb_star").on("change", function() {
				let star = $(this).val()
				star = star * 10
				
				$("span.star_red").css("width", star + "%")
				$("span#star_v").text($(this).val())
			})
			
			$("#btn_write").click(function () {
				if($("#rb_bcode").val() == "") {
					alert("도서코드는 반드시 입력해야 합니다")
					$("#rb_bcode").focus()
					return false
				}
				
				// parseInt() = 문자열을 숫자로 바꿔주는 함수
				if(parseInt($("#rb_rtime").val()) < 1) {
					alert("독서시간은 반드시 입력해야 합니다")
					$("#rb_rtime").focus()
					return false
				}
				
				if($("#rb_subject").val() == "") {
					alert("한줄평은 반드시 입력해야 합니다")
					$("#rb_subject").focus()
					return false
				}
				
				$("form").submit()
			})
			
			// input box에 내용이 있을때 focus()가 위치하면 내용을 전체 블럭 설정하기(다른 글자를 입력하면 내용이 삭제되도록)
			$("#rb_bname,#rb_rtime,#rb_bcode,#rb_subject").on("focus", function() {
				$(this).select()
			})
			
			$("#b_name").on("keypress", function (event) {
				//엔터키를 눌렀을때
				if(event.keyCode == 13) {
					
					let strText = $(this).val()
					
					if(strText == "") {
						alert("도서이름을 입력한 후 Enter를 눌러주세요")
						return false
					}
					
					$("#modal_box").css('display', 'block')
					
					$.post("${rootPath}/book/search", {strText : strText}, function(result) {
						$("#modal_content").html(result)
					})
				}
			})
			
			$(".modal_header span").on("click", function() {
				$("#modal_box").css("display", "none")
			})
				
		})
	</script>
	<style>
		.input_box {
			display: flex;
			width: 80%;
			margin: 5px auto;
		}
		
		.input_box input{
			display: block;
			border: none;
			border-bottom: 1px solid rgb(52, 152, 219);
			font-size: 0.9rem;
			width: 100%;
			padding: 8px;
		}
		
		#rb_bcode {
			width: 50%;
			margin-right: 5px;
		}
		
		#rb_star {
			width: 60%;
		}
		
		span.star_box {
			width: 100px;
			margin-left: 20px;
		}
		
		span.star_box, span.star_red {
			display: inline-block;
			height: 19px;
			overflow: hidden;
			
			background: url("${rootPath}/image/star.png") no-repeat;
			
			/*배경이미지 width: 자동, height: 40px*/
			background-size: 100px 38px;
		}
		
		span.star_red {
			/* 배경이미지를 채울 때, 왼쪽 아래 꼭지점을 기준으로 배치*/
			background-position: left bottom;
			line-height: 0;
			vertical-align: top;
			width: 50%;
		}
	</style>
</head>
<body>
	<header>
		<h2>My Read Book</h2>
	</header>
	
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<%/*
		글쓰기를 시작할 때 Controller에서 GET으로 path를 받았을 때 현재 view를 보여주고
		input box에 데이터를 입력한 후 submit 버튼을 클릭하면
		controller post로 데이터가 전송된다
		
		path는 view를 열 때 사용했던 path가 그대로 적용된다
		그렇게 사용하지 않을 경우에는 별도로 action을 지정해야 한다
		
		/rbooks/book/insert path를 실행하면
		controller의 GET method가 이를 수신하여 현재 input.jsp를 보여주고
		
		데이터를 입력한 후 저장 button을 클릭하면
		/rbooks/book/insert path의 POST method로 데이터가 전송된다
		
		특별히 action을 지정하지 않으면 위와 같은 메커니즘으로 사용된다
		기본값으로 사용하지 않을 경우 action이나 method 등을 지정하여 사용할 수 있다
		
		spring form tag는 method의 기본값이 POST이고
		html form tag는 method의 기본값이 GET이다
		
		button은 type을 지정하지 않으면 type="submit"이 기본값이고
		버튼을 클릭하면 form에 입력한 데이터를 서버로 전송하는 기능을 한다
		
		button에 특별히 이벤트를 적용하고 싶으면 type="button"으로 지정한 뒤 event를 이용해 사용할 수 있다
		
		button에는 3가지 type이 있다
		submit : 기본값이고 form 안에 있는 경우 form에 담긴 데이터를 서버로 전송하는 기능
				 input box가 1개만 있을 경우 Enter키에 반응하여 버튼을 클릭한 것처럼 작동한다
		button : 모든 기능을 제거하고 별도의 이벤트를 설정해서 사용하는 용도
		reset : form 안에 있는 경우 form의 input box에 임의로 작성한 데이터를 모두 초기화하는 기능
	*/%>
	<section id="main_writer">
		<article>
			<form:form modelAttribute="rBookVO">
				<div class="input_box">
					<form:input type="text" path="rb_bcode" id="rb_bcode" placeholder="도서코드" />
					<form:input type="text" path="b_name" placeholder="도서명을 입력한 후 Enter..."/>
				</div>
				
				<div class="input_box">
					<form:input type="date" path="rb_date" id="rb_date" placeholder="독서일자" />
				</div>
				
				<div class="input_box">
					<form:input type="time" path="rb_stime" id="rb_stime" placeholder="독서시작시간" />
				</div>
				
				<div class="input_box">
					<form:input type="text" path="rb_rtime" id="rb_rtime" placeholder="독서시간" />
				</div>
				
				<div class="input_box">
					<form:input type="text" path="rb_subject" id="rb_subject" placeholder="한줄평" />
				</div>
				
				<div class="input_box">
					<form:input type="range" path="rb_star" id="rb_star" min="1" max="10" list="starOption" placeholder="별점" />
					<span class="star_box">
						<span class="star_red"></span>
					</span>
					
					<span id="star_v"></span>
				</div>
				
				<datalist id="starOption">
					<option value="1" label="1" />
					<option value="2" label="2" />
					<option value="3" label="3" />
					<option value="4" label="4" />
					<option value="5" label="5" />
					<option value="6" label="6" />
					<option value="7" label="7" />
					<option value="8" label="8" />
					<option value="9" label="9" />
					<option value="10" label="10" />
				</datalist>
				
				<div class="input_box">
					<form:textarea path="rb_text" id="rb_text" placeholder="소감" />
				</div>
				
				<div id="main_button">
					<button id="btn_write" class="biz_orange flex_right" type="button">독서록 저장</button>
					
					<button id="btn_clear" class="biz_red" type="reset">새로작성</button>
					
					<button id="btn_list" class="biz_blue" type="button">리스트로 가기</button>
				</div>
			</form:form>
		</article>
	</section>
	
	<div id="modal_box">
		<div class="modal_header">
			<span>&times;</span>
		</div>
		
		<div id="modal_content">
			
		</div>
	</div>
</body>
</html>