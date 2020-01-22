# Java Config 방식 SpringMVC Project
### 2020-01-17

1. web.xml을 대신할 ProjectInit.java 클래스를 생성
- extends AbstractAnnotationConfigDispatcherServletInitializer 상속받기

2. root-context.xml을 대신할 RootConfig.java 클래스를 생성
- @Configuration
- method는 없는 상태

3. servlet-context.xml을 대신할 WebServletConfig.java 클래스를 생성
- implements WebMvcConfigurer
- @Configuration 설정
- @EnableWebMvc 설정
- @ComponentScan(basePackages = {"com.biz.rbooks.controller","com.biz.rbooks.service"}) 설정
- Override/Implement Method 기능을 이용하여 addResourceHandlers() method를 구현하는 코드 추가
- InternalResourceViewResolver 생성하고 @bean 어노테이션 및 mapping을 설정하는 코드 추가