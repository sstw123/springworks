package com.biz.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * HttpServlet 클래스를 상속받아서 생성한 서블릿 클래스
 * 사용자가 Web을 통해서 request를 보내면 요청을 수신해서 처리할 App의 대문 역할을 하는 Class
 * console 화면에서 main() method가 있는 클래스와 비슷한 역할을 한다
 * 
 * 1. 사용자가 Web Browser를 통해 request를 보내면
 * 2. 서버 컴퓨터의 네트워크를 통해서 전송된 데이터를 TomCat이 수신
 * 3. URL의 context 부분을 확인하여 현재 실행되고 있는 프로젝트가 있는지 확인
 * 4. 프로젝트에 작성되어 있는 Servlet 클래스들이 있는지 확인
 * 5. Servlet의 @WebServlet 항목에 설정된 path를 scan하여
 * 6. 사용자의 요청을 doGet()이나 doPost() 메소드로 전달한다
 * 
 * WAS 서버가 작동되는 과정
 * TomCat은 App이 Server에 의해 실행되면(Run As Server)
 * 1. 프로젝트에 담겨있는 모든 *.jsp 파일을 내부적으로 다시 *_jsp.java로 언어컴파일(translation)을 수행하고
 * 2. *_jsp.java를 *_jsp.class 파일로 컴파일을 수행
 * 3. 이 *_jsp.class 들을 container에 리스트로 올려두고 request를 대기
 * 
 * -- 사용자가 생성한 servlet, tomcat이 생성한 jsp.class 모두
 * 4. servlet 클래스가 있으면 이 클래스들도 모두 컴파일을 수행하고 객체로 선언, 생성하여 container에 리스트로 올려두고 request를 대기
 * 
 * 5. web에 request가 오면 이 container 리스트에서 해당 정보를 찾아서 사용자의 요청을 처리
 * 
 * @WebServlet()
 * 사용자의 request URL(URI)중에 path라고 하는 부분의 ID값을 설정하는 부분
 * WAS(Web Application Service 또는 Web Application Server) 환경에서는
 * 클래스 이름은 외부로 직접 노출되지 않고 path에 지정된 값을 통해서 외부 접근을 허용한다
 * 
 * 대소문자를 구별하는 서버도 있고 구별하지 않는 서버도 있기 때문에 통상적으로 path는 소문자로 작성하는 것이 좋다
 * ID값을 현재 프로젝트에서 유일한 값이어야 한다
 * 경우에 따라서 /main_path/sub_path/child_path 형식으로 path를 다중으로 설정할수도 있다
 */
@WebServlet("/helloservlet")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		jsp의 input box(st_name)에 담겨온 문자열을 추출하여 st_name 변수에 담기
		String st_name = request.getParameter("st_name");
		System.out.println(st_name);
		
//		web에 문자열을 보낼 때 한글(영어이외) 문자의 encoding 설정
		response.setContentType("text/html; charset=UTF-8");
		
//		response.getWriter()
//		Server에서 Web으로 데이터를 return하는 통로
		PrintWriter pw = response.getWriter();
		pw.println(st_name);
		
//		web화면에 어떤 데이터를 표시하는 것이 아니고
//		WebContect/hello.jsp 파일을 열어서
//		Web으로 전송하라
		
		/*
		 * response.getWriter().println("<h3>~~~</h3>") 식으로
		 * print 메소드 하나하나 HTML 태그를 적어서 웹 화면에 보여줘야 한다면
		 * 굉장히 귀찮고 복잡한 작업이 될 것이다
		 * 
		 * 따라서 애초에 jsp파일을 따로 만들어놓고
		 * response.sendRedirect() 메소드를 이용해서 jsp 파일을 열도록 만드는 방식을 사용한다
		 * 거기에 덧붙여서 ("/JSP_04_Servlet/hello.jsp?st_name=" + st_name); 같은 방식으로 변수를 보낼 수도 있다
		 */
		response.sendRedirect("/JSP_04_Servlet/hello.jsp?st_name=" + st_name);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
