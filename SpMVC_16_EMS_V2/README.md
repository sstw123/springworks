# EMS Project
* Email Management System Project V1
* 2020-01-20

### JPA-Hibernate, MySQL 연동 프로젝트
	<bean class="org.apache.commons.dbcp2.BasicDataSource" id="emsHiber">
		<property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://127.0.0.1:3306/emsDB?serverTimezone=Asia/Seoul" />
		<property name="username" value="ems" />
		<property name="password" value="ems" />
	</bean>
* driverClassName : com.mysql.cj.jdbc.Driver를 사용한다
* url : 기본 연결 주소에 ?serverTimezone=Asia/Seoul 쿼리를 추가
* 5.x 버전에서는 SSL 연결을 하지 않는 &userSSL=false 옵션을 추가해야 한다. 8.x 버전에서는 오류가 발생하니 추가하지 않는다

### Naver 아이디로 로그인 구현
1. 네이버에게 login창을 보내달라고 요청
- 서버에서 state라는 특별한 key를 하나 생성하고 그 값을 같이 보내야 한다
2. 네이버는 login창을 다시 redirect 해주고
3. 네이버가 보내는 login 창에 로그인을 수행하고 정상으로 로그인이 되면
4. OAuth 2.0 규격의 token을 보내주는데 이 토큰을 이용해서 네이버에 어떤 정보(회원정보)들을 요청할 수 있다