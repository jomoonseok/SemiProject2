<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" integrity="sha512-3j3VU6WC5rPQB4Ld1jnLV7Kd5xr+cq9avvhwqzbH/taCRNURoeEpoPBK9pDyeukwSxwRPJ8fDgvYXd6SkaZ2TA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
	
	$(function(){
		
		fn_login();
		fn_displayRememberId();
		
	});
	
	function fn_login(){
		
		$('#frm_login').submit(function(event){
			
			if($('#id').val() == '' || $('#pw').val() == ''){
				alert('아이디와 패스워드를 모두 입력하세요.');
				event.preventDefault();
				return;
			}
			
			if($('#rememberId').is(':checked')){
				$.cookie('rememberId', $('#id').val());
			} else {
				$.cookie('rememberId', '');
			}
			
		});
		
	}
	
	function fn_displayRememberId(){
		
		let rememberId = $.cookie('rememberId');
		if(rememberId == ''){
			$('#id').val('');
			$('#rememberId').prop('checked', false);
		} else {
			$('#id').val(rememberId);
			$('#rememberId').prop('checked', true);
		}
		
	}
	
</script>
</head>
<body>

	<div>

		<!-- post는 URL파라미터로 데이터 전송x, 보안 유지할 때 사용, 속도가 느림 -->
		<!-- get은 URL파라미터로 데이터 전송, 서버상의 데이터 값이나 상태를 바꾸지 않음. -->
		<!-- 활용: 글의 목록이나 내용을 보는 경우에는 get방식, 글의 내용을 저장하고 수정할 때는 post방식 사용 -->
		
		<form id="frm_login" action="${contextPath}/user/login" method="post">  
			
			<input type="hidden" name="url" value="${url}">
			
			<div>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
			</div>
			
			<div>
				<label for="pw">비밀번호</label>
				<input type="password" name="pw" id="pw">
			</div>
			
			<div>
				<label for="rememberId">
					<input type="checkbox" id="rememberId">
					아이디 저장
				</label>
				<label for="keepLogin">
					<input type="checkbox" name="keepLogin" id="keepLogin">
					로그인 유지
				</label>
			</div>
			
			<div>			
				<button>로그인</button>
			</div>
			
		
		</form>
			
		<div>
			<a href="${contextPath}/user/agree">회원가입페이지</a> |
			<a href="${contextPath}/member/findIdPw">아이디/비밀번호 찾기</a>  
		</div>
		
		<hr>
		
		<div>		
			<a href="${apiURL}"><img height="50" src="http://static.nid.naver.com/oauth/small_g_in.PNG"/></a>
		</div>	
	</div>
	
</body>
</html>