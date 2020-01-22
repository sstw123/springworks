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
