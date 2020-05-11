## 쇼핑몰의 로그인/회원가입 + Spring Security
### Spring Security
* Spring Security Core
* Spring Security Web
* Spring Security Config
* Spring Security Taglibs

* jasypt
* jasypt-spring31

### DB
* spring JDBC
* apache DBCP
* MyBatis
* MyBatis-Spring
* MySQL-J(ava)

### Basic Dependency
* lombok
* jackson-databind
* logback

### 시작하기
#### 기본설정
1. web.xml 설정 : 한글 인코딩 필터 추가, *-context.xml
2. servlet-context.xml 설정 : component-scan controller, service 추가

#### Spring Security 기본 설정
1. spring 폴더 아래에 [jasypt-context.xml] 생성 후 설정
2. spring 폴더 아래에 [db-context.xml] 생성 후 설정
3. appServlet 폴더 아래에 [security-context.xml] 생성 후 설정
4. spring 폴더 아래에 properties 폴더 생성 후(security-context.xml에서 설정한 폴더) [spring-security.message.ko.properties] 파일 복사
5. service 패키지에 [AuthenticationProvider]를 implements 한 클래스를 하나 만들고 security-context.xml에 설정
6. 유저정보 테이블에 저장할 [UserDetailsVO](implements UserDetails), 권한 테이블에 저장할 [AuthorityVO] 하나씩 만들고 설정
7. [AuthenticationProviderImpl](implements AuthenticationProvider) -> [UserDetailsService] -> [UserDao], [AuthoritiesDao] 만들고 설정
8. spring 폴더 아래에 mapper 폴더 생성 후 [auth-mapper.xml], [user-mapper.xml] 생성 및 설정. user-mapper는 resultMap을 이용해 authorities 테이블 조회 결과도 가져오기

#### Spring Security 설정