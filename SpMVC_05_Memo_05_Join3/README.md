# Spring MVC 메모장 프로젝트

### 한 개의 테이블(tbl_memo)을 기준으로 CRUD를 구현하기
### list.jsp : 메모 리스트를 보여주기
* 메모 추가 버튼을 클릭하여 메모 작성

### 메모 리스트를 클릭하여 메모 세부내용을 보여주기(view.jsp)
### 메모 세부내용 보기에서 수정, 삭제 버튼을 클릭하여
* 메모 수정
* 메모 삭제를 구현

* web >> controller : RequestMapping >> Service >> Dao + mapper 합성 >> DBMS 적용
* 그 결과를 controller에서 views/*.jsp 파일과 합성(렌더링) >> HTML 변환 >> web에 보여주기

### SessionAttributes Annotation, spring core의 form 태그를 같이 사용하여
* insert와 update의 코드를 다소 간결하게 작성

### Context
* 서버 프로젝트를 생성하면 base-package를 설정하게 된다 : com.biz.memo
* 서버를 시작하면 http://localhost:8080/memo/ 라는 URL로 시작된다
* 이 때 /memo 를 context라고 부르며 project의 root라고 표현한다(= webapp 폴더)
* 이 프로젝트의 모든 URL은 /memo/~~~ 방식으로 시작한다
* pageContext.request.contextPath 값을 사용하여야 하는데
* 변수명이 너무 길기 때문에 jstl core의 set 태그를 사용하여, 각 jsp 파일의 상단에 rootPath 변수를 만들고
* EL 태그를 이용하여 URL을 지정한다
* a href="${rootPath}/memo/list" 라고 작성하면
* a href="/memo/memo/list" 형식으로 컴파일되어 웹페이지에 노출된다

### BCryptPasswordEncoder를 사용한 비밀번호 암호화
* Spring Security Project에서 제공하는 암호화 클래스
* 단방향 암호화를 지원하는 클래스로 회원가입을 할 때
* 비밀번호나 ID, 주민번호와 같은 민감한 정보를 암호화하여 저장하는 용도로 사용된다

* 평문을 암호화하는 encode()는 지원하나 반대로 암호문을 평문으로 복호화하는 decode()는 지원하지 않는다
* 단방향 암호화에서는 암호문 비교를 할 때 평문을 encode()하고 저장된 암호문과 equal() 비교를 수행한다
* 하지만 BCrypt는 매번 encode()를 수행할 때 자체적으로 생성한 salt를 사용하는데
* 매번 salt가 달라져서 암호문이 바뀌기 때문에 이러한 방식을 사용할 수 없다
* if(encode(평문) == 저장된암호문) 방식 처리 불가
* (MD5 암호화 방식처럼 같은 입력값에서 항상 같은 출력값이 나올 경우 사용 가능)

* BCrypt에서는 별도로 matches()라는 method를 통해서 암호문 비교를 수행하도록 만들어져 있다