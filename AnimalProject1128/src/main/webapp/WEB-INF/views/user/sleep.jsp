<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴면계정</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
</head>
<body>

	<div>
	
		<h1>휴면계정안내</h1>
		
		<div>
			안녕하세요!<br>
			${sleepUser.id}님은 1년 이상 로그인하지 않아 관련 법령에 의해 휴면계정으로 어쩌구저쩌구
			<ul>
				<li>가입일 ${sleepUser.joinDate}</li>
				<li>마지막 로그인 ${sleepUser.lastLoginDate}</li>
				<li>휴면전환일 ${sleepUser.sleepDate}</li>
			</ul>
		</div>
	
		<hr>
		
		<div>
			<div>
				휴면해제를 위해 버튼을 클릭해 주세요.
			</div>
			<form action="${contextPath}/user/restore" method="get">
				<div>
					<button>휴면해제</button>
					<input type="button" value="취소" onclick="location.href='${contextPath}'">
					<input type="hidden" name="id" value="${sleepUser.id}">
				</div>
			</form>
		</div>
	
	</div>
	
</body>
</html>