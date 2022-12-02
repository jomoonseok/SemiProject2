<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="${free.freeNo}번 게시글 수정" name="title" />
</jsp:include>

<script>

	// contextPath를 반환하는 자바스크립트 함수
	function getContextPath() {
		var begin = location.href.indexOf(location.origin) + location.origin.length;
		var end = location.href.indexOf("/", begin + 1);
		return location.href.substring(begin, end);
	}

	$(document).ready(function(){
	
		$('#content').summernote({
			width: 800,
			height: 400,
			lang: 'ko-KR',
			toolbar: [
				['style', ['bold', 'italic', 'underline', 'clear']],
				['font', ['strikethrough', 'superscript', 'subscript']],
				['fontsize', ['fontsize']],
				['color', ['color']],
				['para', ['ul', 'ol', 'paragraph']],
				['height', ['height']]
			]
		});


		$('#btn_list').click(function(){
			location.href = getContextPath() + '/freeboard/list';
		});
		
		$('#frm_write').submit(function(event){
			if($('#title').val() == ''){
				alert('제목을 작성해주세요.');
				event.preventDefault();
				return;
			}
		});
		
	});
	
</script>

<style>
	<%-- 1. 버튼 --%>
	
	.button {
	  font-size: 13px;
	  line-height: 25px;
	  background: rgb(255, 255, 255);
	  color: rgb(127, 127, 127);
	  box-shadow: 0 2px 5px rgba(0,0,0,.25);
	  width: 50px;
	  border: 1px solid rgb(115, 104, 93);
	  border-radius: 5px;
	  overflow: hidden;
	  transition-duration: 0.3s;
	}
	
	.button:hover {
		color: rgb(115, 104, 93);
		font-weight: bold;
		background: rgb(233, 231, 228);
	}
	
	.button:active {
		color: rgb(115, 104, 93);
		font-weight: bold;
		background: rgb(255, 255, 255);
		box-shadow: none;
	}
	
	h3 {
		font-family: 'Noto Sans KR', sans-serif;
		color: rgb(127, 127, 127);
		text-align: center;
	}
</style>


	<div>
		<h3>${free.id}님의 ${free.freeNo}번 게시글 수정</h3>
		
		<form id="frm_write" action="${contextPath}/freeboard/modify" method="post">
		
			<input type="hidden" name="freeNo" value="${free.freeNo}">
		
			<div>
				<label for="title">제목</label>
				<input type="text" name="freeTitle" id="title" value="${free.freeTitle}">
			</div>
			<div>
				<label for="content">내용</label>
    			<textarea name="freeContent" id="content" >${free.freeContent}</textarea>
			</div>
			<div>
				<button class="button">수정</button>
				<input type="button" value="목록" id="btn_list" class="button">
			</div>
		</form>
	</div>
	
	


</body>
</html>