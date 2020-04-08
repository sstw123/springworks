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
* 인증(Authenticated)된 주체(User)가 어떤 페이지, 기능, 서비스에 접근할 수 있는 권한이 있다는 것을 보증, 증명

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