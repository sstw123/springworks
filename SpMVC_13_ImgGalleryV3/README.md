# 이미지 갤러리 프로젝트
* 2020-01-03

### WYSIWYG(What You See Is What You Get) Editor
* summernote : https://summernote.org/

* textarea에 id 값을 설정하고 id에 jQuery를 이용해서 속성을 설정해주면 간편하게 WYSIWYG 방식으로 문서를 작성할 수 있다

### Drag and Drop으로 파일 올리기
1. Drag and Drop을 수행할 box 만들기
2. jQuery dragover, drop event 설정
3. e.originalEvent.dataTransfer로부터 files 객체 추출
4. files 객체의 0번째 file 객체 추출

5. ajax를 사용해서 서버로 파일을 업로드 수행하고
6. 파일 이름등을 다시 response로 되돌려 받아서
7. form의 img_file input box에 저장하고
8. 내용을 form post로 upload하면 나머지 정보를 DB에 저장

### Drag and Drop으로 여러 파일 동시에 올리기
* 1 ~ 3 까지는 single file upload와 동일
4. files 객체에 담긴 모든 file 객체를 formData에 append()

5. ajax를 사용해서 서버로 업로드하고
6. 컨트롤러는 수신된 파일들을 서버에 저장하고
7. 저장된 파일이름들을 리스트로 생성하고
8. 생성된 파일이름 리스트와 리스트를 표현할 jsp를 rendering하여 다시 return해주고
9. ajax의 success 코드에서 return받은 html 코드를 리스트를 표현할 box에 보인다
10. 이 때 리스트를 만들 때 hidden으로 파일리스트를 담을 input box를 만들고
11. 각각의 파일이름을 input box의 value에 추가한다
12. 저장버튼 클릭시 다시 컨트롤러에서는 본문(text)과 함께 hidden input box에 담긴 파일명을 수신하는데
13. String[] 문자열 배열로 수신하면 된다

### form에서 같은 tag에 다중 값을 담아서 Controller에 전달하는 방법
1. List<String> 형식으로 전달하기
* form에서 같은 이름의 tag에 다중 값을 담고
* <input name="title" value="1번">
* <input name="title" value="2번">
* <input name="title" value="3번">
* Controller에서 String[] title 형식으로 매개변수를 설정하여 받기

2. VO 내부에서 List<String> title 변수를 설정해두고 받기
* List<SubVO>를 포함한 MainVO에 한꺼번에 받기
* <input name="main[0].title" value="1번">
* <input name="main[1].title" value="2번">
* <input name="main[2].title" value="3번">

### HttpSession
* statusless : HTTP(Hyper Text Transfer Protocol)는 특성상 request가 이루어지고 결과를 response 하게되면 web browser와 server간에 어떠한 정보도 남지 않는다
* 통신에서는 같은 client(web browser)에서 같은 주소로 서버에 자주 request를 수행하는 경우가 많다
* 이 때, client가 요청한 request에 대한 정보를 server가 참조하고 싶을 때가 있다. 대표적인 예) 로그인한 정보

* 과거에는 cookie라는 것을 사용해서 사용자가 로그인하면, 그 로그인한 데이터를 cookie라는 데이터 형태로 만들어서 client에게 보내고, client 어딘가에 그 정보를 저장해두었다가
* 다시 request를 보낼 때 cookie를 request 정보에 담아서 같이 전송하는 방법을 사용했다
* 서버에서는 request 정보에 cookie가 담겨있으면 그 정보를 분석하여 client로부터 보내온 정보를 분석하여 행동을 결정했다

* cookie라는 정보는 보통 암호화되지 않고 누구나 열어볼 수 있는 형태가 많다. 즉, 개인정보가 노출되기 쉽고 중간에 가로채기를 통해서 cookie 정보가 노출될 위험이 있다
* 이러한 단점을 보안하기 위해 Session이라는 객체 개념이 도입되었고 Java 기반 서버(WAS)에서는 HttpSession 클래스를 만들어 쉽게 Session을 관리할 수 있도록 하고 있다
* 서버는 필요할 때 HttpSession 객체를 Attribute를 추가하면 Java에서 사용할 수 있는 어떤 데이터라도 Session형 데이터로 만들 수 있다

* httpSession.setAttribute("MEMBER", memverVO) 형태로 코딩하게 되면 memberVO 객체에 담긴 모든 데이터가 서버의 기억장치 어딘가에 보관되고, MEMBER라는 이름으로 session ID가 생성된다
* 이 때 session ID는 자체적으로 특별한 방법으로 암호화 된 값으로 변환된다
* 서버에서 response를 수행하면(서버에서 클라이언트에게 데이터를 보내면) 자동으로 response body에 이 session ID값이 추가되어 client로 보내진다
* client는 수신된 response 정보에 session ID가 있으면 cookie 영역에 임시로 보관한다
* 이후에 client에서 request를 보낼 때 이 session ID를 첨가하여 server로 보낸다

* 서버(Spring)에서는 받은 request 정보에 session ID가 있으면 해당 session 객체를 메모리에서 찾아보고 session ID가 유효하면, 그 객체를 controller의 method에 주입한다
* controller의 method에서는 HttpSession httpSession 형식의 매개변수를 선언해두면 해당 객체에 session 객체 값이 담겨있어서 코드에서 사용할 수 있다

* SessionAttributes는 하나의 Controller 내에서만 공유하고
* HttpSession는 모든 Controller가 공유하게 된다

* HttpSession은 유효기간을 정하지 않으면 browser가 완전히 닫히는 경우나 server가 재시작되어야 무효화된다
* 유효기간을 정하면 일정시간동안 아무 액션을 취하지 않는다면 무효화된다(액션은 브라우저가 감지하고 결과를 서버가 받음)

### Interceptor
* Interceptor란 컨트롤러에 들어오는 요청 HttpRequest와 컨트롤러가 응답하는 HttpResponse를 가로채는 역할을 합니다.
* Dispatcher가 Controller로 보내기 전 공통적으로 수행할 작업
* Login 유효성 검사 등을 한다

서버에서 클라이언트를 인식하는 
무효화되면 HttpSession은 계속 남아있는지