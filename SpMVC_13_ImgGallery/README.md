# 이미지 갤러리 프로젝트
* 2020-01-03

### WYSIWYG(What You See Is What You Get) Editor
* summernote : https://summernote.org/

* textarea에 id 값을 설정하고 id에 jQuery를 이용해서 속성을 설정해주면 간편하게 WYSIWYG 방식으로 문서를 작성할 수 있다

### Drag and Drop으로 파일 올리기
1 Drag and Drop을 수행할 box 만들기
2 jQuery dragover, drop event 설정
3 e.originalEvent.dataTransfer로부터 files 객체 추출
4 files 객체의 0번째 file 객체 추출

5 ajax를 사용해서 서버로 파일을 업로드 수행하고
6 파일 이름등을 다시 response로 되돌려 받아서
7 form의 img_file input box에 저장하고
8 내용을 form post로 upload하면 나머지 정보를 DB에 저장

### Drag and Drop으로 여러 파일 동시에 올리기
* 1 ~ 3 까지는 single file upload와 동일
4 files 객체에 담긴 모든 file 객체를 formData에 append()

5 ajax를 사용해서 서버로 업로드하고
6 컨트롤러는 수신된 파일들을 서버에 저장하고
7 저장된 파일이름들을 리스트로 생성하고
8 생성된 파일이름 리스트와 리스트를 표현할 jsp를 rendering하여 다시 return해주고
9 ajax의 success 코드에서 return받은 html 코드를 리스트를 표현할 box에 보인다
10 이 때 리스트를 만들 때 hidden으로 파일리스트를 담을 input box를 만들고
11 각각의 파일이름을 input box의 value에 추가한다
12 저장버튼 클릭시 다시 컨트롤러에서는 본문(text)과 함께 hidden input box에 담긴 파일명을 수신하는데
13 String[] 문자열 배열로 수신하면 된다