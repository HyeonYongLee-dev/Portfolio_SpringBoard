package com.spring.portfolio.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.portfolio.member.service.MemberServiceImpl;
import com.spring.portfolio.member.vo.MemberVO;

@Controller("memberController")
public class MemberControllerImpl implements MemberController{
	//@Autowired를 이용해 id가 memberService인 빈 자동 주입
	@Autowired
	private MemberServiceImpl memberService;
	//@Autowired를 이용해 id가 memberVO인 빈 자동 주입
	@Autowired
	private MemberVO memberVO;
	
	@RequestMapping(value = { "/*","/main.do"}, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	
	@Override
	@RequestMapping(value="/member/listMembers.do", method=RequestMethod.GET)
	//두 단계로 매핑 설정하여 다른 기능들과 구분, 요청시 해당 메서드 호출한다
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//인터셉터에서 바인딩된 뷰 이름(객체)를 가져온다
		//객체인 viewName을 String 형태로 캐스팅하여 viewName에 저장
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String) request.getAttribute("viewName");
		//memberService의 listMembers()메서드가 dao에서 받아온 리스트를 memberList에 저장
		List<MemberVO> membersList = memberService.listMembers();
		//modelAndView 객체 생성(모델(data),view를 둘 다 저장 가능)
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		return mav;
	}
	
	@Override
	@RequestMapping(value="/member/addMember.do", method=RequestMethod.POST)
	//modelAttribute("member) MemberVO member: 회원 가입창에서 전송된 회원 정보를 바로 MemberVO에 객체로 설정한다
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		//result값 초기화
		int result = 0;
		//설정된 memberVO객체를 SQL문으로 전달해 회원 등록을 한다
		result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value="/member/removeMember.do", method=RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}

	@Override
	@RequestMapping(value = "/member/login.do", method=RequestMethod.POST)
	//로그인창에서 전송된 id와 비밀번호를 MemberVO객체인 member에 저장한다
	//RedirectAttributes 클래스를 이용해서 로그인 실패시 다시 로그인창으로 리다이렉트하여 실패 메시지(매개변수) 전달한다
	//id와 비밀번호로 회원 정보를 조회하여 해당 회원 정보가 존재시 로그인 상태와 회원 정보를 세션에 저장한다 그리고 로그아웃 요청시 세션 정보 삭제
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		//login() 메서드를 호출하면서 로그인 정보를 전달한다
		memberVO = memberService.login(member);
		
		//만약 memberVO로 반환된 값이 있다면(not null), 세션을 이용하여 로그인 상태를 true로 한다
		if(memberVO !=null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", memberVO); //세션에 회원 정보를 저장한다
			session.setAttribute("isLogOn", true); //세션에 로그인 상태를 true로 설정한다
			String action = (String) session.getAttribute("action");
			session.removeAttribute("action");
			
			if(action!=null) {
				mav.setViewName("redirect:"+action);
			}else {
				mav.setViewName("redirect:/member/listMembers.do");
			}
		} else {
			rttr.addAttribute("result", "loginFailed"); //로그인 실패시 실패 메시지를 로그인창으로 전달한다
			mav.setViewName("redirect:/member/loginForm.do"); //로그인 실패시 다시 로그인창으로 리다이렉트
		}
		return mav;
	}

	@Override
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//로그아웃 요청시 세션에 저장된 로그인 정보와, 회원 정보를 삭제한다
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
	@RequestMapping(value="/member/*Form.do", method=RequestMethod.GET)
	//@RequestParam(value="result", required=false): 로그인창 요청시 매개변수 result가 전송되면 변수 result에 값을 저장한다
	//최초로 로그인창 요청시 매개변수 result가 전송되지 않으므로 무시한다
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
			  					@RequestParam(value= "action", required=false) String action,
								HttpServletRequest request,
								HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("html/text;charset=utf-8");
		String viewName = (String) request.getAttribute("viewName");		
			HttpSession session = request.getSession();
			//글쓰기 창 요청명을 aciton 속성으로 세션에 저장한다
			session.setAttribute("action", action);
			ModelAndView mav = new ModelAndView();
			mav.addObject("result", result);
			mav.setViewName(viewName);
			return mav;
		
	}
	
	
	//요청 url 요청명에서 뷰 리졸버 설정 없이 응답 jsp 파일을 얻는다
	private String getViewName(HttpServletRequest request) throws Exception {
		
		String contextPath = request.getContextPath();
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
			viewName = viewName.substring(viewName.lastIndexOf("/", 1), viewName.length());
		}
		return viewName;
	}	
}
