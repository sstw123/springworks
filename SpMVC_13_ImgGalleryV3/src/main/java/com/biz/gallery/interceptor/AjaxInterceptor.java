package com.biz.gallery.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// Request 가로채기
public class AjaxInterceptor extends HandlerInterceptorAdapter {
	
	/*
	 * HandlerInterceptorAdapter의 preHandle 메소드
	 * Dispatcher에서 Controller 가기 전 가로채기를 수행할 메소드
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// login 정보 확인
		// 1. request로부터 session ID 추출
		HttpSession httpSession = request.getSession();
		
		// 2. MEMBER Session을 확인하기 위해서 Attribute를 추출해서 Object 객체(sessionObj)에 담기
		Object memberObj = httpSession.getAttribute("MEMBER");
		
		// 3. MEMBER라는 Object 객체가 없다면(== null) 로그인이 안되어있다는 뜻
		if(memberObj == null) {
			/*
			 * context/image/upload에서 로그인 path로 redirect를 수행하는데 경로 지정이 애매하게 작동한다
			 * 현재 경로가 /image/update이기 때문에
			 * 스프링에선 ../ 을 지정하면 context부터 경로를 시작한다
			 * ../member/login = gallery/image/member/login
			 */
			// 4. 로그인화면(form)으로 redirect를 수행하여 login을 하도록 유도
			//response.sendRedirect(request.getContextPath() + "/member/login");
			response.setStatus(403);//403 Forbidden 접근 권한 없음, HttpServletResponse.SC_FORBIDDEN = 403
			// 5. 현재 로그인이 안되어 있으므로 Dispatcher에게 더이상 다른 일(컨트롤러로 보내는 것)을 진행하지 말라는 의미 
			return false;
		}
		
		//return true;//컨트롤러에게 전달하기		
		return super.preHandle(request, response, handler);
	}
	
	
	
}
