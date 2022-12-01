<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 찾기</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" integrity="sha512-3j3VU6WC5rPQB4Ld1jnLV7Kd5xr+cq9avvhwqzbH/taCRNURoeEpoPBK9pDyeukwSxwRPJ8fDgvYXd6SkaZ2TA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
	
	$(function(){
		
		fn_emailCheck();
		fn_findId();
		
	});

	
	// 이메일
	function fn_emailCheck(){
		
			
		$('#btn_findId').click(function(){
			if($('#email').val() == ''){
				alert('이메일을 입력하세요.');
				event.preventDefault();
				return;
			}
			// 중복체크
			$.ajax({
				/* 요청 */
				type: 'get',
				url: '${contextPath}/user/checkReduceEmail',
				data: 'email=' + $('#email').val(),
				/* 응답 */
				dataType: 'json',
				success: function(resData){  
					if(resData.user){
						alert('이 이메일로 가입된 ID는' + resData.user.id + '입니다.');
						return;
					}else if(resData.sleepUser){
						alert('이 이메일로 가입된 ID는' + resData.sleepUser.id + '입니다.');
					} else {
						alert('가입되지 않은 이메일 입니다.');
						return;
					}
					
/* 					if( $('#email').val() == data.user.email) {  // $('#email').val()과 data.user.email의 타입이 맞지 않음.
						alert('이 이메일로 가입된 ID는' + data.user.id + '입니다.');
					} else {
						alert('가입되지 않은 이메일 입니다.');
						console.log(data.user);
					} */
				}
			});  // ajax
			
			
			
		});

	}  // fn_emailCheck
	
	
	
</script>
</head>
<body>

	<div>
		
		<div>
			<h1>아이디 찾기</h1>
			<span>회원정보에 등록한 이메일을 입력하세요.</span>
		</div>

		<form id="frm_findId" action="${contextPath}/user/login/form" method="get">  
			
			<div>
				<label for="email">이메일</label>
				<input type="text" name="email" id="email">
				<span id="msg_email"></span>
			</div>
			
			<div>			
				<input type="button" value="아이디찾기" id="btn_findId">
				<button>로그인</button>
				<input type="button" value="취소하기" onclick="history.back(-1)">
			</div>
			
		
		</form>

	</div>
	
</body>
</html>