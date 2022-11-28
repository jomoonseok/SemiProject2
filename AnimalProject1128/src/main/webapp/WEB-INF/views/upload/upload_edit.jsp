<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="파일 업로드 게시판" name="title" />
</jsp:include>
<style>
	#edit_info div {
		display: inline;
		padding: 0 10px;
	}
</style>
<script>
	$(function() {
		
		fn_modify();
		
	});
	
	function fn_modify() {
		$('#btn_modify').click(function() {
			$('#frm_modify').attr('action', "${contextPath}/upload/modify");
			$('#frm_modify').submit();
			
		});
	}
	
	function fn_remove() {
				
				$.ajax({
					type: 'POST',
					url: '${contextPath}/upload/attach/remove',
					data: {attachNo:$('#attachNo').val(), uploadNo:${upload.uploadNo}},
					success: function() {
						alert('첨부파일이 제거되었습니다.');
						location.reload();
					}
					
				});
				
	}
	
	
	
	
</script>

	<form id="frm_modify" method='post' enctype="multipart/form-data" >
		<input type="hidden" name="uploadNo" value="${upload.uploadNo}">
		<div id="edit_info">
			<div>
				작성자 : ${upload.id}
			</div>
			<div>
				조회수 : ${upload.uploadHit}
			</div>
			<br>
			<div>
				작성일자 : ${upload.uploadCreateDate}
			</div>
			<div>
				최종 수정일자 : ${upload.uploadModifyDate}
			</div>
			<div>
				작성자 IP : ${upload.uploadIp}
			</div>
		</div>
		<hr>
		<div>
			제목
			<input type="text" name="title" value="${upload.uploadTitle}">
	 	</div>
	 	<div>
	 		내용
			<textarea name="content">${upload.uploadContent}</textarea>
		</div>
		<div><br>
		<div>
			<label for="files">첨부</label>
			<input type="file" name="files" value="파일선택" multiple="multiple">
		</div>
		<c:if test="${attachCnt gt 0}">
			<c:forEach items="${attachList}" var="attach" varStatus="vs">
			<div id="attachList">
				${attach.origin}
				 / <input type="button" value="삭제" id="btn_remove" name="${vs.current.attachNo}" onclick="fn_remove()">
				 <input type="hidden" name="attachNo" value="${vs.current.attachNo}" id="attachNo">
			</div>
			</c:forEach>
		</c:if>
		</div>
		<div>
			<input type="button" value="수정완료" id="btn_modify">
			<input type="button" value="목록" onclick="location.href='${contextPath}/upload'">
		</div>
	</form>
</body>
</html>