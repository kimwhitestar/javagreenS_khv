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
  function searchCustomCompDelPracList() {
	  deletePracForm.action = '${ctxPath}/admin/customCompDeletePracList';
	  deletePracForm.submit();
  }
  
  function deleteCustomCompPrac() {
	  let listLen = ${vos.length};
	  let delHtml = '';
	  let delCustomId = '';
	  $("#delList").html('');
	  for (let i=1; i<=listLen; i++) {
		  alert($("#chk"+i).checked);
		  if (true == $("#chk"+i).checked) {
			  delCustomId = $("#customId"+i).val();
			  delHtml += '<input type="hidden" name="delCustomId" value="'+delCustomId+'"/>';
		  }
	  }
	  $("#delList").html(delHtml);
	  
	  deletePracForm.action = '${ctxPath}/admin/customCompDeletePrac';
	  deletePracForm.submit();
  }
  
  function deleteCustomCompPrac(delCustomId) {
	  $("#delList").html('');
	  let delHtml = '<input type="hidden" name="onceDelCustomId" value="'+delCustomId+'"/>';
	  $("#delList").html(delHtml);
	  
	  deletePracForm.action = '${ctxPath}/admin/customCompDeletePrac';
	  deletePracForm.submit();
  }
  
  </script>
</head>
<body>
  <jsp:include page="/common/admin/header_home.jsp" />
  <jsp:include page="/common/admin/adminNav.jsp" />

  <p><br></p>
  <div class="container">
  	<h3>기업고객 회원탈퇴신청목록</h3>
  	<br>
  	<form name="deletePracForm" method="post">
	  	<div class="form-control">
	  		<label for="overFlg" class="text-left">30일 경과</label>
	  		<select id="overFlg" name="overFlg" class="text-left">
	  			<option value="">- 전 체 -</option>
	  			<option value="OVER">경과</option>
	  			<option value="PRAC">미경과</option>
	  		</select>
	  		<input type="button" id="search" value="조회" class="text-right" onclick="searchCustomCompDelPracList()"/> &nbsp;&nbsp;
	  		<input type="button" id="delete" value="삭제" class="text-right" onclick="deleteCustomCompPrac()"/>
	  	</div>
	  	<table>
	  		<tr>
	  			<th><input type="checkbox" id="allChk" name="allChk"></th>
	  			<th>로그인ID</th>
	  			<th>고객ID</th>
	  			<th>고객명</th>
	  			<th>사업자등록번호</th>
	  			<th>로그인 삭제 경과일</th>
	  			<th>회원삭제 경과</th>
	  			<th>로그인 삭제일</th>
	  			<th>로그인 삭제자</th>
	  			<th>삭제</th>
	  		</tr>
			<c:forEach var="vo" items="${vos}" begin="1" end="${vos.length}" step="1" varStatus="st">
				<tr>
	  				<td><input type="checkbox" id="chk${st.count}" ></td>
					<td>${vo.loginId}</td>
					<td>${vo.customId}<input type="hidden" id="customId${st.count}" value="${vo.customId}"/></td>
					<td>${vo.customName}</td>
					<td>${vo.companyNo}</td>
					<td>${vo.overDaysUserDel}</td>
					<td>${vo.overFlg}</td>
					<td>${vo.deleteDate}</td>
					<td>${vo.deleteUser}</td>
					<td><input type="button" id="del${st.count}" value="삭제" onclick="javascript:deleteCustomCompPrac(${vo.customId})"/></td>
				</tr>
			</c:forEach>
	  	</table>
	  	<div id="delList">
	  		<input type="hidden" name="delCustomId" /><br>
	  		<input type="hidden" name="onceDelCustomId" />
	  	</div>
  	</form>
  </div>
   
<jsp:include page="/common/admin/footer.jsp"/>
</body>
</html>