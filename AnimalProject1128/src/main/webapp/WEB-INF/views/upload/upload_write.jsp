<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="파일 업로드 게시판" name="title" />
</jsp:include>
<script>
	$(function() {
		fn_summernote();
		fn_prevent();
		fn_fileCheck();
		
	});
	
	function fn_summernote() {
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
		})
	}
	
	function fn_prevent() {
		$('#frm_add').submit(function(event) {
			if($('#title').val() == '') {
				alert('제목과 내용은 필수입니다.');
				event.preventDefault();
				return;
			}
			
			if($('#content').summernote('isEmpty')) {
				alert('제목과 내용은 필수입니다.');
				event.preventDefault();
				return;
			}
		});
	}
	
	function fn_fileCheck(){
		$('#file').change(function(){
			let maxSize = 1024 * 1024 * 10;
			let files = this.files;
			
			for(let i = 0; i < files.length; i++){
				if(files[i].size > maxSize){
					alert('10MB 이하의 파일만 첨부할 수 있습니다.');
					$(this).val('');  // 첨부된 파일을 모두 없애줌
					return;
				}
			}
		});
	}
	
	
</script>
		


	<form id='frm_add' method='post' enctype="multipart/form-data" action="${contextPath}/upload/add" >
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title">
	 	</div>
	 	<div>
			<textarea id="content" name="content"></textarea>
		</div>
		<div>
			<label for="file">첨부</label>
			<input type="file" id="file" name="files" value="파일선택" multiple="multiple">
		</div>
		<div>	
			<button>작성완료</button>
			<input type="button" value="목록" onclick="location.href='${contextPath}/upload'">
		</div>
	</form>
</body>
</html>