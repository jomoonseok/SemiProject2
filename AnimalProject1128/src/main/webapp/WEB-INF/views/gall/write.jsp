<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="갤러리게시판목록" name="title"/>
</jsp:include>

<script>
	
	// contextPath를 반환하는 자바스크립트 함수
	// taglib방식 대신 사용 
	function getContextPath() {
		var begin = location.href.indexOf(location.origin) + location.origin.length;
		var end = location.href.indexOf("/", begin + 1);
		return location.href.substring(begin, end);
	}
	
	$(document).ready(function(){
		
		$('#gallContent').summernote({
			width: 800,
			height: 400,
			lang: 'ko-KR',
			toolbar: [
			    // [groupName, [list of button]]
			    ['style', ['bold', 'italic', 'underline', 'clear']],
			    ['font', ['strikethrough', 'superscript', 'subscript']],
			    ['fontsize', ['fontsize']],
			    ['color', ['color']],
			    ['para', ['ul', 'ol', 'paragraph']],
			    ['height', ['height']],
			    ['insert', ['link', 'picture', 'video']]
			],
			callbacks: {
				
				onImageUpload: function(files) {

					for(let i = 0; i < files.length; i++) {
						
						var formData = new FormData();
						
						formData.append('file', files[i]); 
						
						$.ajax({
							type: 'post',
							url: getContextPath() + '/gall/uploadImage',
							data: formData,
							contentType: false,  
							processData: false,  
							dataType: 'json',    
							success: function(resData) {
								$('#gallContent').summernote('insertImage', resData.src);  
								$('#summernote_image_list').append($('<input type="hidden" name="summernoteImageNames" value="' + resData.filesystem + '">'));
							}
						});  // ajax
					}  // for
				}  // onImageUpload
			}  // callbacks
		});
		
		// 목록
		$('#btn_list').click(function(){
			location.href = getContextPath() + '/gall/list';
		});
		
		// 서브밋
		$('#frm_write').submit(function(event){
			if($('#gallTitle').val() == ''){
				alert('제목은 필수입니다.');
				event.preventDefault();  // 서브밋 취소
				return;  // 더 이상 코드 실행할 필요 없음
			}
		});
		
	});
	
</script>

<div>
	
	<h1>작성 화면</h1>
	
	<form id="frm_write" action="${contextPath}/gall/add" method="post">
	
		<div>
			<label for="gallTitle">제목</label>
			<input type="text" name="gallTitle" id="gallTitle">
		</div>
		
		<!-- 여기에 작성자의 이름을 받아와야함 -->
		
		<div>
			<label for="gallContent">내용</label>
			<textarea name="gallContent" name="filesystemList" id="gallContent"></textarea>				
		</div>
		
		<div id="summernote_image_list"></div>
		
		<div>
			<button>작성완료</button>
			<input type="reset" value="입력초기화">
			<input type="button" value="목록" id="btn_list">
		</div>
	</form>
</div>

</body>
</html>