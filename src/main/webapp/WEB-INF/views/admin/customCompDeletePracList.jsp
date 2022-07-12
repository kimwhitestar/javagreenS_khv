<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>기업고객 회원탈퇴신청목록</title>
  <jsp:include page="/include/bs4.jsp" />
  <style>
  </style>
  <script>
  'use strict';
  </script>
</head>
<body>
  <jsp:include page="/common/admin/header_home.jsp" />
  <jsp:include page="/common/admin/adminNav.jsp" />

  <p><br></p>
  <div class="container">
  	<h3>기업고객 회원탈퇴신청목록</h3>
  	<table>
  		<tr>
  			<th>로그인ID</th>
  			<th>고객ID</th>
  			<th>고객명</th>
  			<th>사업자등록번호</th>
  			<th>로그인 삭제 경과일</th>
  			<th>회원삭제 경과</th>
  			<th>로그인 삭제일</th>
  			<th>로그인 삭제자</th>
  		</tr>
  	</table>
	<c:forEach var="vo" items="${vos}" >
		<tr>
			<td>${vo.loginId}</td>
			<td>${vo.customId}</td>
			<td>${vo.customName}</td>
			<td>${vo.companyNo}</td>
			<td>${vo.overDaysUserDel}</td>
			<td>${vo.overFlg}</td>
			<td>${vo.deleteDate}</td>
			<td>${vo.deleteUser}</td>
		</tr>
	</c:forEach>
	
  </div>
   
<jsp:include page="/common/admin/footer.jsp"/>
</body>
</html>