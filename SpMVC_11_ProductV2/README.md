# 상품정보관리 프로젝트

## ajax 기능을 활용하여 상품정보 보기 실행

## 파일 업로드 기능 추가
* 파일업로드에서는 파일의 내용(파일 자체)를 직접 DB에 저장하기도 하지만
* 그렇게 할 경우 DBMS에 많은 무리가 생겨 성능상 문제가 생긴다
* 따라서 파일은 public(resource)으로 공개된 폴더에 저장하고 DB에는 파일의 경로, 이름만 저장한 뒤
* 보여줄 때 그 경로를 a, img 태그 등을 사용하여 보여준다

### 파일 업로드 form
* 파일 업로드를 위해서 input type="file" 태그를 사용하고
* 파일을 선택할 수 있도록 수행한다

#### !중요!
* 파일 업로드 form 태그에는 반드시 enctype 속성을 지정한다
* enctype="multipart/form-data" 으로 지정한다
* 파일 업로드 기본 메소드 설정은 "POST"

### 파일 Size
* commonsMultipartResolver 클래스에 파일 최대 업로드 크기를 지정해주어야 한다
* 1KB = 1024Byte, 1MB = 1024KB = 1024 * 1024Byte (Byte 단위로 설정한다)

### 파일을 저장할 폴더
* servlet-context.xml의 resources로 외부에 개방할 폴더 설정하기
* mapping으로 설정한 값 : 가상 디렉토리
* location으로 설정한 값 : 실제 폴더
* web에서 server/context/mapping으로 접근을 시도하면 실제로는 location 폴더의 내용에 접근할 수 있도록 설정하는 것이다

* 로컬프로젝트에 있는 파일들을 프로젝트를 서버로 배포(deploy)하면 파일들이 모두 자동으로 복사되어 문제를 일으키지 않는다
* 만약 서버에 실행된 상태에서 파일 업로드를 통해서 서버에 복사한 파일은
* 실제 로컬에 없는 파일이기 때문에 만약 서버를 clean 하거나 프로젝트를 다시 deploy하면 모든 내용이 삭제되어 버린다
* 이런 현상을 방지하기 위해서 files 폴더를 tomcat 서버와 별개의 장소에 만들어두고 이 폴더를 외부에 개방한다
* <resources mapping="/files/**" location="file:///c:/bizwork/uploadedfiles/"
* resources mapping을 위와 같이 설정하면 서버를 통해서 /files/ 디렉토리에 접근하면 실제로는 c:/bizwork/uploadedfiles 폴더에 접근을 허락하는 것이다

* 이 설정을 수행했으나 만약 c:/bizwork/uploadedfiles 폴더에 접근이 되지 않을 경우
* BeanNameUrlHandlerMapping을 servlet-context.xml에 beans:bean의 class로 등록해주기
* BeanNameUrlHandlerMapping : Dispatcher가 기본으로 사용하는 Mapping인데 간혹 특정 버전에서 참조하지 못하는 경우 bean으로 설정한다