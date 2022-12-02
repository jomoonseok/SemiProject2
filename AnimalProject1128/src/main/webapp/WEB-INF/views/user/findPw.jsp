<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.1/jquery.cookie.min.js" integrity="sha512-3j3VU6WC5rPQB4Ld1jnLV7Kd5xr+cq9avvhwqzbH/taCRNURoeEpoPBK9pDyeukwSxwRPJ8fDgvYXd6SkaZ2TA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script>
	
	$(function(){
		
		fn_findPw();
		fn_init();
		
	});
	
	function fn_init(){
		$('#id').val('');
		$('#email').val('');
	}
	
	function fn_findPw(){
		
		$('#btn_findPw').click(function(event){
			if($('#id').val() == ''){
				alert('아이디를 입력하세요.');
				return false;
			}
			if($('#email').val() == ''){
				alert('이메일을 입력하세요.');
				return false;
			}
			
			// 중복체크
			$.ajax({
				/* 요청 */
				type: 'get',
				url: '${contextPath}/user/checkReduceIdEmail',  // 컨트롤러 경로
				data: 'id=' + $('#id').val() + '&email=' + $('#email').val(),
				/* 응답 */
				dataType: 'json',
				success: function(resData){  
					if(resData.user){
						alert('임시 비밀번호가 발급되었습니다. 메일함을 확인해 주세요.');
					}else{
						alert('아이디 또는 이메일이 회원정보에 없습니다.');
						return;
					}

				}
			});  // ajax	
		});
	}
	

	
	
	
</script>
</head>
<body>

	<div>
		
		<div>
			<h1>비밀번호 찾기</h1>
			<span>아이디, 이메일을 모두 입력하세요.</span>
		</div>
		
		<form id="frm_findPw" action="${contextPath}/user/login/form/pw" method="post">  
			
			<div>
				<label for="id">아이디</label>
				<input type="text" name="id" id="id">
				<span id="msg_id"></span>
			</div>
			
			<div>
				<label for="email">이메일</label>
				<input type="text" name="email" id="email">
				<span id="msg_email"></span>
			</div>
			
			<div>			
				<input type="button" value="비밀번호찾기" id="btn_findPw">
				<button>로그인</button>
				<input type="button" value="취소하기" onclick="history.back(-1)">
			</div>
			
		
		</form>

	</div>
	
</body>
</html>