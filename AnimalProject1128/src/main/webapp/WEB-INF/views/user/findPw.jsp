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
		
		<div>
			<h1>비밀번호 찾기</h1>
		</div>
		
		<form id="frm_findPw" action="${contextPath}/user/findPw" method="post">  
			
			<div>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
			</div>
			
			<div>
				<label for="name">이름</label>
				<input type="text" name="name" id="name">
			</div>
			
			<div>
				<label for="mobile">휴대전화</label>
				<input type="password" name="mobile" id="mobile">
				<span id="msg_mobile"></span>
			</div>
			
			<div>			
				<button>비밀번호찾기</button>
				<input type="button" value="취소하기" onclick="history.back(-1)">
			</div>
			
		
		</form>

	</div>
	
</body>
</html>