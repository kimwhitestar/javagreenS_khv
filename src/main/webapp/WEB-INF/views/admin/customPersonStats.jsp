<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>개인고객 통계</title>
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
  	<h3>개인고객 통계</h3>
  	<br>
  </div>
   
<jsp:include page="/common/admin/footer.jsp"/>
</body>
</html>