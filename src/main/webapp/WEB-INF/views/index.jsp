<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <title>기업고객로그인</title>
  <meta charset="utf-8">
  <jsp:include page="/include/bs4.jsp" />
  <style></style>
  <script>
  	'use strict';
  </script>
</head>
	<frameset cols="32%, 68%">
		<frame src="${ctxPath}/common/customNav.jsp" name="customLeft" id="customLeft" frameborder="0" />
		<frame src="${ctxPath}/customComp/customCompLogin" name="customContent" id="customContent" frameborder="0" />
	</frameset>
	
</html>