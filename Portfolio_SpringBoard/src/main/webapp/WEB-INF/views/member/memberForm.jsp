<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입창</title>

</head>
<body>
<form method="post" action="${contextPath }/member/addMember.do">
<h1 class = "text_center">회원 가입창</h1>
<table align="center">
	<tr>
		<td width="200"><p align="right">사용자 아이디</p></td>
		<td width="400"><input type="text" name="id">
	</tr>
	
	<tr>
		<td width="200"><p align="right">사용자 비번</p></td>
		<td width="400"><input type="text" name="pwd">
	</tr>
	
	<tr>
		<td width="200"><p align="right">사용자 이름</p></td>
		<td width="400"><input type="text" name="name">
	</tr>
	
	<tr>
		<td width="200"><p align="right">사용자 이메일</p></td>
		<td width="400"><input type="text" name="email">
	</tr>
	
	<tr>
		<td width="200"><p>&nbsp;</p></td>
		<td width="400"><input type="submit" value="가입하기">
		<td width="400"><input type="reset" value="다시 입력">
	</tr>
</table>
</form>
</body>
</html>