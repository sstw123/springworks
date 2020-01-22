# 프로젝트 : 학생정보

## base-package : com.biz.student
## 패턴 : SpringMVC 패턴

* pom.xml의 dependency 수정
* java version 1.8
* spring version 5.1.11
* lombok 추가
* log를 변경 -> logback으로 설정

* web.xml에 한글 encoding filter 설정

* servlet-context.xml 수정
* component-scan의 base-package를 com.biz.student.controller로 변경
* HomeController.java 클래스 파일을 com.biz.student.controller 패키지로 이동

* home.jsp 상단 부분의 page 설정