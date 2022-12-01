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


	<div>
		<h1>작성 화면</h1>
		
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
				<button>수정완료</button>
				<input type="button" value="목록" id="btn_list">
			</div>
		</form>
	</div>
	
	


</body>
</html>