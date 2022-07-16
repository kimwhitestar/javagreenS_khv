<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <title>물류정보시스템</title>
  <meta charset="utf-8">
  <jsp:include page="/include/bs4.jsp" />
  <style></style>
  <script>
  	'use strict';
  </script>
</head>
		<frame src="${ctxPath}/common/customNav.jsp" name="customLeft" id="customLeft" frameborder="0" />
</html>