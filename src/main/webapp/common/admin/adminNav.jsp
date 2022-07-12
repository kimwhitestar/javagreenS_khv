<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String sLevel = session.getAttribute("sLevel")==null? "99": (String)session.getAttribute("sLevel");
	String sLevelName = session.getAttribute("sLevelName")==null? "": (String) session.getAttribute("sLevelName");
%>
<script>
	'use strict';
</script>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <a class="navbar-brand" href="<%=request.getContextPath()%>/admin/adminIndex">Home</a>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item font-weight-bold">
        <a href="${ctxPath}/" class="text-left">홈으로</a>
      </li>
	<%	if (sLevel.equals("0") && sLevelName.equals("관리자")) { //관리자 %>
      <li class="nav-item font-weight-bold">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">회원관리</a>
        <div class="dropdown">
		    <div class="dropdown-menu">
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/customCompDeletePracList">기업회원탈퇴목록</a>
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/customPersonDeletePracList">개인회원탈퇴목록</a>
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/customCompStats">기업회원통계</a>
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/customPersonStats">개인회원통계</a>
		    </div>
		</div>
      </li>
      <li class="nav-item font-weight-bold">
        <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown">게시판관리</a>
        <div class="dropdown">
		    <div class="dropdown-menu">
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/">회원게시판설정</a>
				<a class="dropdown-item" href="<%=request.getContextPath()%>/admin/">회원게시판통계</a>
		    </div>
		</div>
      </li>
	<%	} %>
	<%	if (sLevel.equals("0") && sLevelName.equals("관리자")) { //관리자 %>
      <li class="nav-item font-weight-bold">
		<a class="text-right" href="<%=request.getContextPath()%>/admin/adminLogout">관리자 로그아웃</a>
      </li>
	<%	} else { %>
      <li class="nav-item font-weight-bold">
		<a class="text-right" href="<%=request.getContextPath()%>/admin/adminLogin">관리자 로그인</a>
      </li>
	<%	} %>
    </ul>
  </div>  
</nav>