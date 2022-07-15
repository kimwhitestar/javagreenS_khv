<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="/include/bs4.jsp" />
<%
	String sLevel = session.getAttribute("sLevel")==null? "99": (String)session.getAttribute("sLevel");
	String sLevelName = session.getAttribute("sLevelName")==null? "": (String) session.getAttribute("sLevelName");
%>
<script>
	'use strict';
</script>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <a class="navbar-brand" href="${ctxPath}/admin/adminIndex">Home</a>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">

	<%	if (sLevel.equals("0") && sLevelName.equals("관리자")) { //관리자 %>
      <li class="nav-item font-weight-bold">
        <div class="dropdown ">
        	<a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">회원관리</a>
		    <div class="dropdown-menu bg-dark">
				<a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/admin/customCompDeletePracList">기업회원탈퇴목록</a>
				<a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/admin/customPersonDeletePracList">개인회원탈퇴목록</a>
				<a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/admin/customCompStats">기업회원통계</a>
				<a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/admin/customPersonStats">개인회원통계</a>
			</div>
		</div>
	  </li>
	  
      <li class="nav-item font-weight-bold">
        <div class="dropdown ">
        	<a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">Pds관리</a>
		    <div class="dropdown-menu bg-dark">
				<a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/admin/">Pds설정</a>
				<a class="dropdown-item  navbar-brand" target="customContent"   href="${ctxPath}/admin/">회원게시판통계설정</a>
			</div>
		</div>
	  </li>

	<%	} %>
	<%	if (sLevel.equals("0") && sLevelName.equals("관리자")) { //관리자 %>
      <li class="nav-item font-weight-bold">
        <div class="dropdown ">
        	<a href="${ctxPath}/admin/adminLogout" class="nav-link dropdown-toggle navbar-brand text-right" data-toggle="dropdown">관리자 로그아웃</a>
		</div>
	  </li>    
	<%	} else { %>
      <li class="nav-item font-weight-bold">
        <div class="dropdown ">
        	<a href="${ctxPath}/admin/adminLogin" class="nav-link dropdown-toggle navbar-brand text-right" data-toggle="dropdown">관리자 로그인</a>
		</div>
	  </li>
	<%	} %>
    </ul>
  </div>  
</nav>