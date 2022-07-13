<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
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
			<h3 class="p-2 text-left">기업고객 최근접속회원</h3>
			<h6 class="text-left">최근에 접속한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${compRecentlyLoginVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customNameShort}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.loginDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<hr class="d-sm-none">
			<h3 class="text-left p-2">개인고객 최근접속회원</h3>
			<h6 class="text-left">최근에 접속한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${personRecentlyLoginVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customName}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.loginDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<hr class="d-sm-none">
		</div>
	    <div class="col-sm-8">
			<h3 class="p-2 text-left">기업고객 신규가입회원</h3>
			<h6 class="text-left">최근에 가입한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${compRecentlyEntryVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customNameShort}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.createDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<hr class="d-sm-none">
			<h3 class="text-left p-2">개인고객 신규가입회원</h3>
			<h6 class="text-left">최근에 가입한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${personRecentlyEntryVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customName}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.createDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	    <div class="col-sm-12">
			<h3 class="p-2 text-left">기업고객 탈퇴회원</h3>
			<h6 class="text-left">최근에 탈퇴한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${compPracDeleteVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customNameShort}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.deleteDate}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
			<hr class="d-sm-none">
			<h3 class="text-left p-2">개인고객 탈퇴회원</h3>
			<h6 class="text-left">최근에 탈퇴한 회원 명단</h6>
			<div class="dashimg">
				<table class="table table-bordered text-center m-0">
					<tr class="text-white w3-blue-grey">
						<th>아이디</th>
						<th>고객명(축)</th>
						<th>고객구분</th>
						<th>날짜</th>
					</tr>
 					<c:forEach var="vo" items="${personPracDeleteVoList}">
						<tr>
							<td>${vo.loginId}</td>
							<td>${vo.customName}</td>
							<td>${vo.customKindName}</td>
							<td>${vo.deleteDate}</td>
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