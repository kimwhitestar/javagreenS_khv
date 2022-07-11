<%-- <%@ page import="guest.database.GuestVO"%>
<%@ page import="guest.database.GuestDAO"%>
<%@ page import="board.database.BoardVO"%>
<%@ page import="board.database.BoardDAO"%>
<%@ page import="member.database.MemberVO"%>
<%@ page import="member.database.MemberDAO"%>
<%@ page import="study.database.LoginVO"%>
 --%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<%-- <%
//전체회원목록 대시보드 10건
  MemberDAO memberDao = new MemberDAO();
  List<MemberVO> mVos = memberDao.searchMemberList((char)0, 0, 0, 10);//검색조건 없이 전체목록조회 10건
  pageContext.setAttribute("mVos", mVos);
  
  //게시판목록 대시보드 10건
  BoardDAO boardDao = new BoardDAO();
  List<BoardVO> nVos = boardDao.searchBoardList((char)0, 0, null, null, 0, 10);//검색조건 없이 전체목록조회 10건
  pageContext.setAttribute("nVos", nVos);
  
  //방명록목록 대시보드 10건
  GuestDAO guestDao = new GuestDAO();
  List<GuestVO> gVos = guestDao.searchGuestList((char)0, 0, null, null, 0, 10);//검색조건 없이 전체목록조회 10건
  pageContext.setAttribute("gVos", gVos);
%>
 --%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>javagreenS_khv 물류정보시스템 관리자 홈화면</title>
  <jsp:include page="/include/bs4.jsp" />
  <style>
  .dashimg {
    height: 200px;
    background: #ddd;
  }
  </style>
</head>
<body>

<jsp:include page="/common/admin/header_home.jsp" />
<jsp:include page="/common/admin/adminNav.jsp" />

<div class="container" style="margin-top:30px">
	<div class="row">
		<div class="col-sm-4">
			<h3 class="p-2 text-left">신규 가입 회원</h3>
			<h6 class="text-left">최근 가입한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>닉네임</th>
					</tr>
<%-- 					<c:forEach var="i" begin="0" end="2">
					<tr>
						<td>${mVos[i].mid}</a></td>
						<td>${mVos[i].nickName}</a></td>
					</tr>
					</c:forEach>
 --%>				</table>
			</div>
			<p>1회 방문시마다 5point를 지급합니다.(단, 1일 최대 50point 까지 지급)</p>
			<h3 class="text-left">Study Links</h3>
			<p class="text-left">학습하고 있는 개발 언어</p>
			<ul class="nav nav-pills flex-column">
				<li class="nav-item">
					<a class="nav-link active bg-info" href="#">Training</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">a</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">b</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="#">c</a>
				</li>
			</ul>
			<hr class="d-sm-none">
		</div>
	    <div class="col-sm-8">
			<h3 class="p-2 text-left">탈퇴 회원</h3>
			<h6 class="text-left">최근 탈퇴한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>닉네임</th>
					</tr>
<%-- 					<c:forEach var="i" begin="0" end="2">
					<tr>
						<td>${mVos[i].mid}</a></td>
						<td>${mVos[i].nickName}</a></td>
					</tr>
					</c:forEach>
 --%>				</table>
			</div>
			<br>
			<h3 class="text-left p-2">방명록 신규자료</h3>
			<h6 class="text-left">최근에 게시된 방명록</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey" >
						<th width="68%">내용</th>
						<th width="32%">방문자</th>
					</tr>
					<c:forEach var="i" begin="0" end="2">
					<tr >
						<td>${gVos[i].content}</td>
						<td>${gVos[i].name}</td>
					</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
</div>
<br><br>
<jsp:include page="/common/admin/footer.jsp" />
</body>
</html>