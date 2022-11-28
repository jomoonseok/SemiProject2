<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="게시글 목록" name="title" />
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


	<div>
		<h1>작성 화면</h1>
		
		<form id="frm_write" action="${contextPath}/freeboard/add" method="post">
		
			<div>
				<label for="title">제목</label>
				<input type="text" name="title" id="title">
			</div>
			<div>
				<label for="content">내용</label>
    			<textarea name="content" id="content" ></textarea>
			</div>
			<div>
				<button>작성완료</button>
				<input type="reset" value="입력초기화">
				<input type="button" value="목록" id="btn_list">
			</div>
		</form>
	</div>
	
	


</body>
</html>