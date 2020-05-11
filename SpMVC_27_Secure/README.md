## Spring MVC Security Project
### 2020-04-08

### 개요
* Spring MVC 기반 Security Coding
* Security 기능을 포함한 WebSocket Coding
* React 기반 Front-End 기능 Coding

* JDBC, MyBatis 연동
* MySQL DB 연동
* 회원가입 이메일 인증
* 비밀번호 분실 이메일 인증 후 재설정

### Spring Security Dependencies
* Spring Security Core
* Spring Security Web
* Spring Security Config
* Spring Security Taglibs

* jasypt
* jasypt-spring31

### DB Dependencies(MySQL, MyBatis 연동)
* mysql-connector-java : MySQL과 Java를 연결해주는데 사용하는 DB Driver 
* commons-dbcp2 : JDBC와 Driver 사이에서 DB Connection Pool을 만들고 Connection, Disconnection을 수행하는 보조 Class
* spring-jdbc : Java(Spring)와 DB Driver 사이에서 명령어, 데이터를 변환시켜주는 보조 Class
* mybatis : MyBatis Mapper 등으로 작성된 SQL을 변환하여 jdbc에 전달, 그 외에도 여러가지 기능을 제공(데이터를 VO에 쉽게 매핑시키는 용도 등)하는 유용한 Class
* mybatis-spring : Mybatis를 spring에서 사용할 수 있도록 해주는 Class

### Security와 관련된 용어

#### 접근주체(Principal)
* 보호된 대상에 접근하는 유저(User)

#### 인증(Authenticate)
* 접근하는 유저가 누구인가 확인(로그인 절차)

#### 인가(Authorize)
* 접근한 유저가 어떤 서비스, 어떤 페이지에 접근할 수 있는 권한 부여

#### 권한(Role)
* 인증(Authenticated)된 주체(User)가 어떤 페이지, 기능, 서비스에 접근할 수 있는 권한이 있다는 것을 보증

#### 무결성, 보안
* 무결성 파괴 : 인가된 사용자에 의해 손상될 수 있는 것들
* 보안 파괴 : 인가되지 않은 사용자에 의해 손상될 수 있는 것들

### Spring Security
* Filter를 사용하여 접근하는 사용자의 인증절차와 인가를 통해 권한이 있는지 파악하고
* 적절한 조치(되돌리기, Redirect, 사용가능)를 비교적 적은 코드 양으로 처리할 수 있도록 만든 framework
* Spring Security는 세션과 쿠키방식을 병행하여 사용한다

#### 유저가 로그인 시도 시
1. Authentication Filter에서 users table까지 접근하여 사용자 정보를 인증하는 절차
2. 인증이 되면 users table, users detail table에서 사용자 정보를 fetch(select)하여 session에 저장
3. 일반적인 HttpSession은 서버의 활동 영역 메모리에 session을 저장하지만, Spring Security는 SecurityContextHolder라는 메모리에 저장한다
4. view로 유저 정보가 담긴 session을 session ID와 함께 응답으로 전달
* JSESSIONID 라는 쿠키에 session ID를 저장해서 보낸다
5. 이후 유저가 접근하면 JSESSIONID에서 정보를 추출하여 사용자 인증을 시도한다
* ?JSESSIONID=asdasdasd 이러한 값이 URL 뒤에 따라붙기도 한다
6. JSESSIONID에서 추출한 Session ID가 유효하면 접근 Request에게 Authentication을 부착한다

### Spring Security와 form 데이터
* Web Browser에서 서버로 요청하는 것을 request라고 하며 요청할 때 사용하는 주소를 URL, URI라고 한다
* Web Browser에서 서버에 request하는 method 방식은 GET, POST, PUT, DELETE가 있고 Spring MVC에서는 GET, POST를 주로 사용한다
* GET method는 주소창에 URL을 입력하고 Enter를 누르거나, anchor tag를 마우스로 클릭하거나, form tag의 method가 없는 경우 서버로 요청하는 방식이다
* GET method는 주로 리스트를 요구하거나 입력 form 화면을 요구하는 용도로 사용된다
* POST method는 입력창의 값을 서버로 전송할때 주로 사용하며 form, input 등의 tag에 값을 저장한 후 서버로 submit을 수행한다
* POST method는 데이터의 양에 관계없이 서버로 전송할 수 있으며 file upload 등도 수행할 수 있다
* Spring Security를 적용한 프로젝트에서는 GET method 방식은 아무런 제약이 없으나, POST method 방식은 서버로부터 전달받은 csrf token을 데이터들과 함께 보내야만 정상적으로 서버로 보낼 수 있다
* 따라서 POST method로 값을 보내고 싶다면 다음과 같은 코드를 추가해주어야 한다
* `<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">`
* 매번 form을 만들면서 코드를 추가하다 보면 빠트릴 수 있고 이러한 경우 해당 form의 데이터를 전송하면 서버는 수신을 거부하고 403 오류를 보낸다
* 이러한 불편을 방지하기 위해 Spring Form Taglibs를 사용하여 `<form:form>`을 작성하면 Spring Security와 연동되어 POST 메소드의 경우 자동으로 _csrf 토큰을 form에 추가하여 보내준다

### Email 인증 회원가입
* 회원가입 시 username, password, email을 입력받고 email을 사용자에게 보낸 후 인증을 거쳐 회원가입 완료하기
* 회원가입 화면을 username, password를 입력받는 화면과 email을 입력받는 화면으로 분리하여 email 인증 기능 구현
* sessionAttributes, ModelAttribute를 활용하여 구현
* sessionAttributes는 보통 vo 객체를 서버 메모리에 저장한 후 form 화면과 연동한다. 반드시 ModelAttribute가 동반되어 구현되어야 한다.
* sessionAttributes에 등록된 ModelAttribute 객체는 서버 메모리에 데이터를 보관하고 있다가 form:form을 통해서 서버로 전달되는 param vo 객체를 받고,
form:form에서 누락된 input 항목들이 있으면 메모리에 보관된 ModelAttribute vo에서 param vo 데이터를 완성하여 사용할 수 있도록 만들어준다

## 트랜잭션 : @Transactional
* MyBatis와 common-dbcp 환경에서 context.xml에 <tx:annotation-driven/> 설정을 해주면 @Transaction을 구현할 수 있다
* MyBatis 환경에서 실제 Dao interface와 mapper.xml 등을 연동하여 DB와 query를 주고 받을 때는 SqlSessionTemplate 클래스를 통해서 사용한다
* 만약 context.xml에 DataSourceTransactionManager를 설정하게 되면 SqlSessionTemplate를 설정해주지 않아도 내부적으로 알아서 만들어주기 때문에 굳이 설정하지 않아도 된다
* 만약 context.xml에 <tx:annotation-driven/> 항목이 없고, class나 method에 @Transactional 설정이 없으면 DataSourceTransactionManager는 SqlSessionTemplate와 같은 역할을 하게된다
* <tx:annotation-driven/>을 설정했는데 @Transactional을 설정한 method에서 transaction이 적용되지 않을 때가 있다 이때는 <tx:annotation-driven/> 코드 위쪽에 <context:annotation-config/>를 설정해주어야 한다

### @Transactional의 옵션
#### isolation
* 현재 transaction이 작동되는 과정에서 다른 transaction등이 접근할 수 있는 수준 설정하기
* READ_UNCOMMITED : level 0, 트랜잭션 처리 중 또는 commit이 완료되기 전에 다른 트랜잭션이 읽기를 수행할 수 있다
* READ_COMMITED : level 1, 트랜잭션이 commit 완료된 후에만 다른 트랜잭션이 읽을 수 있다
* REPEATABLE_READ : level 2, 트랜잭션이 진행되는 동안 SELECT 문장이 사용된 TABLE에 Lock 걸기.
SELECT가 실행되거나 실행될 예정인 DB(Table)에는 CUD를 수행할 수 없도록 하며 다른 트랜잭션에서 제한적으로 SELECT가 가능하다
다수의 트랜잭션이 SELECT를 수행할 때 일관된 무결성이 보장된 데이터를 가져갈 수 있도록 보장
* SERIALIZABLE : level 3, 완벽한 일관성있는 SELECT 보장

#### propagation : 전파 옵션, 현재 트랜잭션이 시작되었음을 다른 트랜잭션에 어떻게 알릴 것인지 설정
* REQUIRED : 부모 트랜잭션이 실행되는 과정에서 또 자식(세부적인) 트랜잭션을 실행할 수 있도록 허용하며
이미 자식 트랜잭션이 실행되고 있으면 새로 생성 금지
* REQUIRED_NEW : REQUIRED와 비슷하지만, 자식 트랜잭션이 이미 실행되고 있어도 무조건 새로 생성

#### readOnly : 트랜잭션을 읽기 전용으로 설정하기, 기본값 = false
* readonly로 설정하면 SELECT만 가능하다

#### rollbackFor : rollback 조건 설정, 기본값 = Exception.class
* 특정한 예외(Exception)가 발생했을 때만 rollback 되도록 설정할 때 추가

#### noRollbackFor : rollback 조건 무시 설정, 기본값 = null
* rollbackFor와 반대되는 개념, 특정한 예외에서는 rollback 무시

#### timeout : 기본값 = -1 (timeout rollback 금지)
* DB와 연결하여 transaction이 실행되는 시간이 과도하게 지연될 경우 rollback 하도록 설정

### Transaction 사용 시 주의사항
#### List Insert 수행 시 주의사항
* 트랜잭션 걸린 서비스에서 다음과 같은 코드 절대 사용 금지
`for(DataVO vo : dataList) {
	dao.insert(vo)
}`
* mapper에서 <foreach>를 사용해서 처리해야 한다