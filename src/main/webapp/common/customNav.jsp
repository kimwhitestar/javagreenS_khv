<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<jsp:include page="/include/bs4.jsp" />
<%
	char sGradeCode = session.getAttribute("sGradeCode")==null? (char)99: ((String)session.getAttribute("sGradeCode")).charAt(0);
	String sAdmin = session.getAttribute("sAdmin")==null? "": (String) session.getAttribute("sAdmin");
%>
<script>
	'use strict';
	function checkCustomCompDelete() {
		if (confirm('정말 탈퇴하겠습니까?')) {
			if (confirm('탈퇴 후 1개월간은 같은 아이디로 재가입할 수 없습니다.\n탈퇴하겠습니까?')) 
				location.href = '${ctxPath}/customComp/customCompDeletePract';
		}
	}
	
	function checkCustomPersonDelete() {
		if (confirm('정말 탈퇴하겠습니까?')) {
			if (confirm('탈퇴 후 1개월간은 같은 아이디로 재가입할 수 없습니다.\n탈퇴하겠습니까?')) 
				location.href = '${ctxPath}/customPerson/customPersonDeletePract';
		}
	}
</script>
<body class="bg-dark navbar-dark">
<nav class="navbar navbar-expand-sm">
  <a class="navbar-brand" href="${ctxPath}/">Home</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
      <li class="nav-item font-weight-bold">
        <div class="dropdown ">
	        <a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">기업고객</a>
<%	if (99 == sGradeCode || 'O' != sGradeCode) { %>
			    <div class="dropdown-menu bg-dark">
			      <a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompLogin">기업고객 로그인</a>
			      <a class="dropdown-item  navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompEntry">기업고객 회원가입</a>
					</div>
<%	} else if (99 != sGradeCode && 'O' == sGradeCode) { %>
<%-- <%	} else if (99 != sGradeCode && 1 == sKindGrpCode) { %> --%>
			    <div class="dropdown-menu bg-dark">
		 			  <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompLogout">기업고객 로그아웃</a>
	 			  </div>
<%	} %>
				</div>
      </li>
      <li class="nav-item font-weight-bold">
        <div class="dropdown">
	        <a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">개인고객</a>
<%	if (99 == sGradeCode || 'P' != sGradeCode) { %>
			    <div class="dropdown-menu bg-dark">
			      <a class="dropdown-item  navbar-brand " target="customContent"  href="${ctxPath}/customPerson/customPersonLogin">개인고객 로그인</a>
			      <a class="dropdown-item  navbar-brand " target="customContent"  href="${ctxPath}/customPerson/customPersonEntry">개인고객 회원가입</a>
					</div>
<%	} else if (99 != sGradeCode && 'P' == sGradeCode) { %>
<%-- <%	} else if (99 != sGradeCode && 2 == sKindGrpCode) { %> --%>
			    <div class="dropdown-menu bg-dark">
		 			  <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customPerson/customPersonLogout">개인고객 로그아웃</a>
					</div>
<%	} %>
				</div>
      </li>
<%	if (99 != sGradeCode && 'O' >= sGradeCode) { %>
      <li class="nav-item font-weight-bold">
        <a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">MyPage</a>
        <div class="dropdown">
			    <div class="dropdown-menu bg-dark">
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompMain">기업고객 회원방</a>
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompUpdate">회원정보변경</a>
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customComp/customCompUpdatePwd">회원비밀번호변경</a>
			      <a class="dropdown-item navbar-brand" href="javascript:checkCustomCompDelete();">회원탈퇴</a>
			    </div>
				</div>
      </li>
<%	} %>
<%	if (99 != sGradeCode && 'P' <= sGradeCode) { %>
      <li class="nav-item font-weight-bold">
        <a href="#" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">MyPage</a>
        <div class="dropdown">
			    <div class="dropdown-menu bg-dark">
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customPerson/customPersonMain">개인고객 회원방</a>
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customPerson/customPersonUpdate">회원정보변경</a>
			      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/customPerson/customPersonUpdatePwd">회원비밀번호변경</a>
			      <a class="dropdown-item navbar-brand" href="javascript:checkCustomPersonDelete();">회원탈퇴</a>
			    </div>
				</div>
      </li>
<%	} %>
      <li class="nav-item font-weight-bold">
        <a href="${ctxPath}/admin/adminLogin" class="nav-link dropdown-toggle navbar-brand" data-toggle="dropdown">Admin Page</a>
        <div class="dropdown">
			    <div class="dropdown-menu bg-dark">
			<%	if (sAdmin.equals("adminOk")) { %>
						<a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/admin/adminLogout">관리자 로그아웃</a>
						<a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/admin/adminMain">관리자 메뉴</a>
			<%	} else { %>
				      <a class="dropdown-item navbar-brand" target="customContent"  href="${ctxPath}/admin/adminLogin">관리자 로그인(인증)</a>
			<%	} %>
			    </div>
				</div>
      </li>
    </ul>
  </div>
</nav>
</body>