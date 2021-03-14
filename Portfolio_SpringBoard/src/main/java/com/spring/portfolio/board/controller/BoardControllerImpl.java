package com.spring.portfolio.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.spring.portfolio.board.service.BoardService;
import com.spring.portfolio.board.vo.ArticleVO;
import com.spring.portfolio.member.vo.MemberVO;

import javafx.scene.media.EqualizerBand;
@Controller("boardController")
public class BoardControllerImpl implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//인터셉터에서 전달된 뷰 이름을 가져온다
		String viewName = (String)request.getAttribute("viewName");
		//모든 글 정보를 조회한다
		List articlesList = boardService.listArticles();
		ModelAndView mav = new ModelAndView(viewName);
		//조회한 글 정보를 바인딩 한 후 jsp로 articlesList를 전달한다
		mav.addObject("articlesList", articlesList);
		return mav;
	}

	@Override
	@RequestMapping(value="/board/addNewArticle.do", method = RequestMethod.POST)
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		//글 정보를 저장하기 위한 articleMap을 생성한다
		Map<String, Object> articleMap = new HashMap<String, Object>();
		//Enumeration 인터페이스: 객체들의 집합에서 각각의 객체들을 한 순간에 하나씩 처리할 수 있는 메서드를 제공하는 컬렉션
		Enumeration enu = multipartRequest.getParameterNames();
		
		//글쓰기창에서 전송된 글 정보를 Map에 key/value로 저장한다
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		//업로드된 이미지 파일 이름을 가져온다
		String imageFileName = upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		//세션에 저장된 회원 정보로부터 회원 id를 가져온다
		String id = memberVO.getId();
		//회원 id, 이미지 파일 이름, 부모 글 번호를 articleMap에 저장한다
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			//글 정보가 저장된 articleMap을 Service 클래스의 addNewArticle()메서드로 전달한다
			int articleNO = boardService.addNewArticle(articleMap);
			
			//글 정보를 추가한 후 업로드한 이미지 파일을 글 번호로 명명한 폴더로 이동시킨다
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
			}
			message = "<script>";
			message += " alert('새 글을 추가했다');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			
		} catch (Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = "<script>";
			message += "alert('오류가 발생했다')";
			message += "location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do';";
			message +="</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		return resEnt;
	}
	//글쓰기창
	@RequestMapping(value="/board/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
		
	}

	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		articleVO=boardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		return mav;
	}
		
	
	
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		String imageFileName = null;
		Iterator<String> fileNames=multipartRequest.getFileNames();
		while(fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName=mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO+"\\"+fileName);
			if(mFile.getSize()!=0) {
				if(! file.exists()) {
					if(file.getParentFile().mkdir()) {
						file.createNewFile();
					}
				}
				mFile.transferTo(new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName));
 			}
		}
		return imageFileName;
	}
	@RequestMapping(value="board/modArticle.do", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		//글 정보를 저장하기 위한 articleMap을 생성한다
		Map<String, Object> articleMap = new HashMap<String, Object>();
		//Enumeration 인터페이스: 객체들의 집합에서 각각의 객체들을 한 순간에 하나씩 처리할 수 있는 메서드를 제공하는 컬렉션
		Enumeration enu = multipartRequest.getParameterNames();
		
		//글쓰기창에서 전송된 글 정보를 Map에 key/value로 저장한다
		while(enu.hasMoreElements()) {
			String name = (String) enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name, value);
		}
		
		//업로드된 이미지 파일 이름을 가져온다
		String imageFileName = upload(multipartRequest);
		articleMap.put("imageFileName", imageFileName);
		
		String articleNO=(String) articleMap.get("articleNO");
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			boardService.modArticle(articleMap);
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir, true);
				
				String originalFileName = (String) articleMap.get("originalFileName");
				File oldFile= new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
				oldFile.delete();
			}
			
			message = "<script>";
			message += " alert('글을 수정했다');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = "<script>";
			message += "alert('오류가 발생했다')";
			message += "location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do';";
			message +="</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}

	@Override
	@RequestMapping(value="/board/removeArticle.do" , method = RequestMethod.POST)
	public ResponseEntity removeArticle(int articleNO, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html; charset=utf-8");
		String message;
		ResponseEntity resEnt = null;
		HttpHeaders responseHeaders= new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			boardService.removeArticle(articleNO);
			File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.deleteDirectory(destDir);
			
			message = "<script>";
			message += " alert('글을 삭제했다');";
			message += " location.href='"+request.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		} catch (Exception e) {
			File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
			FileUtils.deleteDirectory(destDir);
			
			message = "<script>";
			message += "alert('오류가 발생했다')";
			message += "location.href='"+request.getContextPath()+"/board/listArticles.do';";
			message +="</script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		
		
		return resEnt;
	}
	

}
