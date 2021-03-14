package com.spring.portfolio.common.interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


//사용자 정의 인터셉터는 반드시 HandlerInterceptorAdapter를 상속받아야 사용 가능하다
public class ViewNameInterceptor extends HandlerInterceptorAdapter{

	@Override
	//Controller에 접근하기 전에 브라우저의 요청을 먼저 가져간다
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		try {
			//getViewName() 메서드를 이용하여 브라우저의 요청명에서 뷰 이름을 가져온다
			String viewName = getViewName(request);
			//뷰 이름을 request에 바인딩한다
			request.setAttribute("viewName", viewName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	//request 객체에서 URL 요청명을 가져와서 응답 JSP 파일을 얻는 해당 함수를 호출하여 JSP가 저장된 폴더 이름에 해당하는 첫 번째 요청부터 가져옴
	//즉 요청명에서 뷰 이름을 반환한다
	private String getViewName(HttpServletRequest request) {
		//request.getContextPath()는 프로젝트의 context path명을 반환하는 메서드
		
		//즉 요청값이 http://localhost:8080/example/test.jsp로 요청한다면 
		//리턴값은 test.jsp가 저장된 /example이다
		String contextPath = request.getContextPath();
		//주소창의 현재 uri를 알아오는 메서드
		//request URI를 얻는 메서드이다 즉 servlet상에서 어떤 경로로 uri 요청했는지 알아보는 메서드
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			// /member/listMembers.do로 요청시 member/listMember를 파일 이름으로 가져온다
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}
}
