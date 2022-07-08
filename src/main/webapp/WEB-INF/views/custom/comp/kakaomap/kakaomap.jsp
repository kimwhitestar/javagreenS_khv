<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
	<jsp:include page="/include/bs4.jsp"/>
    <title>KakaoMap API(기본형)</title>
    
    <style></style>
    <script>
    	'use strict';
    	
    </script>
</head>
<body>
    <p><br></p>
    <div class="container">
	

<div id="map" style="width:100%;height:350px;"></div>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=d8f3f7e2264be9f8d127deb69568488d"></script>
<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
    mapOption = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption); 
</script>
<hr/>
<jsp:include page="${ctxPath}/custom/comp/kakaomap/kakaomenu.jsp"/>
		
    </div>
	<jsp:include page="/common/footer.jsp"/>
</body>
</html>