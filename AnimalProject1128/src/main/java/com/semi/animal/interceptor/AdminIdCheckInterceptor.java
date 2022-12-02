package com.semi.animal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminIdCheckInterceptor implements HandlerInterceptor {

	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == "admin") {
			return true;
		} else {
			try {
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('관리자만 가능한 작업입니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			return false;
		}
		*/
		return true;
	}
	
}
