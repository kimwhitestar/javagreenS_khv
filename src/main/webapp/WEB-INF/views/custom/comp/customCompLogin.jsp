<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />
<c:set var="security" value="<%= new com.spring.javagreenS_khv.common.SecurityUtil() %>" />
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>customCompLogin.jsp</title>
  	<jsp:include page="/include/bs4.jsp" />
    <style></style>
    <script>
   	'use strict';
   	//윈도우 onload
   	$(document).ready(function(){
   		parent.customLeft.location.reload(true);
   	});
   	
   	//로그인폼 체크 후 로그인 요청 전송
   	function formCheck() {
   		if ( ! regexCheck() ) return false;
		setEncryptPwd($("#password").val());
   		loginForm.submit();
   	}
   	//REGEX PATTERN 유효성 체크 : 로그인폼 입력필드의 '영문자, 한글, 숫자, 특수문자, 길이' 체크
    function regexCheck() {
   		const regexId = /^[a-zA-Z]+[0-9_+-.]*[a-zA-Z_+-.]*[0-9]([a-zA-Z0-9_+-.]*){20}$/g; //아이디체크(영문자1자리이상, 숫자나 특수기호 조합 2~20자리)
       	const regexPassword = /([a-zA-Z][0-9][@#$%&!?^~*+-_.]|[0-9][a-zA-Z][@#$%&!?^~*+-_.]|[@#$%&!?^~*+-_.][a-zA-Z][0-9]|[@#$%&!?^~*+-_.][0-9][a-zA-Z])/g;//비밀번호체크(영문자,숫자,특수기호 @#$%&!?^~*+-_. 조합 3~20자리)
		let regexFlag = true;

       //회원아이디 정규식 체크
     	if ( ! $("#id").val().match(regexId) ) {
   			$("#id").addClass("is-invalid");
   			$("#idInvalid").addClass("is-invalid");
   			$("#idInvalid").text("영문자로 시작하여 숫자1자리 이상 포함하는 영문,숫자,특수기호(_+-.)의 조합 2~20자리로 입력하세요");
   			$("#id").focus();
   			regexFlag = false;
   		} else {
   			$("#id").addClass("is-valid");
   			$("#idInvalid").addClass("is-valid");
   			$("#idInvalid").text("");
   			$("#password").focus();
   		}
    	//비밀번호 정규식 체크
  		if ( ! $("#password").val().match(regexPassword) ) {
  			$("#password").addClass("is-invalid");
  			$("#passwordInvalid").addClass("is-invalid");
  			$("#passwordInvalid").text("영문자, 숫자, 특수기호(~!?@#$%^&*_+-.) 조합 3~20자리로 입력하세요");
   			$("#password").focus();
   			regexFlag = false;
  		} else {
  			$("#password").addClass("is-valid");
  			$("#passwordInvalid").addClass("is-valid");
   			$("#passwordInvalid").text("");
  		}
    		
   		return regexFlag;
   	}
   	//비밀번호 암호화
   	function setEncryptPwd(password) {
		$("#encryptPwd").val('${security.encryptSHA256(password)}');
   	}
    //아이디 찾기
   	function findoutId() {
   		let url = '${ctxPath}/customComp/customCompIdFindout';
   		window.open(url,"idFindout","width=580px,height=410px");
   	}
   	//비밀번호 찾기
   	function changePassword() {
   		let url = '${ctxPath}/customComp/customCompPwdChange';
   		window.open(url,"pwdFindout","width=580px,height=600px");
   	}
     </script>
</head>
<body class="jumbotron" <%--background="${ctxPath}/images/bgimg.gif" --%> >
<p><br></p>
<div class="container">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="container p-3 border">
				<form name="loginForm" method="post" action="${ctxPath}/customComp/customCompLogin" class="was-validated">
					<h2 class="text-center">기 업 고 객 로 그 인</h2>
					<p>아이디와 비밀번호를 입력해 주세요</p>
					<div class="form-group">
						<label for="id">아이디 : </label>
						<input type="text" class="form-control" name="id" id="id" value="${loginId}" placeholder="아이디를 입력하세요." required autofocus />
						<div id="idInvalid" class="invalid-feedback">아이디는 필수 입력사항입니다.</div>
					</div>
					<div class="form-group">
						<label for="password">비밀번호 : </label>
						<input type="text" class="form-control" name="password" id="password" placeholder="비밀번호를 입력하세요." required />
						<!-- <div id="passwordInvalid" class="invalid-feedback">비밀번호는 필수 입력사항입니다.</div> -->
						<input type="hidden" class="form-control" name="encryptPwd" id="encryptPwd" />
					</div>
					<div class="form-group text-center">
						<button id="btnLogin" type="button" class="btn btn-info btn-sm" onclick="formCheck()">로그인</button> &nbsp;
						<button type="reset" class="btn btn-info btn-sm">다시작성</button> &nbsp;
						<button type="button" class="btn btn-info btn-sm" onclick="location.href='${ctxPath}/';">돌아가기</button> &nbsp;
						<button type="button" class="btn btn-info btn-sm" onclick="location.href='${ctxPath}/customComp/customCompEntry';">회원가입</button>
					</div>
					<div class="row" style="font-size:12px">
						<span class="col text-left"><input type="checkbox" name="idSave" checked />아이디 저장</span>
						<span class="col text-right"><a href="javascript:findoutId();">아이디 찾기</a> / <a href="javascript:changePassword();">비밀번호 찾기</a></span>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<br><br><br><br><br><br><br><br>
<jsp:include page="/common/footer.jsp" />
</body>
</html>