package com.biz.hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Welcome
 */
@WebServlet("/come")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Welcome() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
//		지금부터 Browser로 전송되는 데이터는 !DOCTYPE이 text/html 인 문서이다
//		UTF-8 방식으로 encoding
		response.setContentType("text/html;charset=UTF-8");
		
//		response 객체는 Server에서 WebBrowser로 연결되는 통로와 정보를 모두 가지고 있다
//		response로부터 PrintWriter 객체를 요청하는데
//		PrintWriter 객체는 서버에서 어떤 데이터를 webBrowser로 보내는 통로로 사용된다
		PrintWriter pw = response.getWriter();
		pw.println("<body>");
		pw.println("<h3>우리 집에 오신 것을 환영합니다</h3>");
		pw.println("<p>나는 Servlet에서 보내는 메세지입니다</p>");
		pw.println("</body>");
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
