<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사이드 메뉴</title>

</head>
<body>

	<ul class="nav nav-pills nav-stacked">
		<a href="${contextPath}/portfolio/member/listMembers.do"  class="no-underline">회원관리</a><br>
		<li><a href="${contextPath}/portfolio/board/listArticles.do" class="no-underline">게시판 관리</a></li>
	</ul>
</body>
</html>