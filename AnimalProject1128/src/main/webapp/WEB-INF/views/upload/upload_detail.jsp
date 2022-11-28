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
		
		$('#btn_edit').click(function() {
			
			
			/* 
			
			if(${upload.id} == sessionid?) {
				
			}
			
			*/
			$('#frm_edit').attr('action', '${contextPath}/upload/edit');
			$('#frm_edit').submit();
			
			
		});
		
			$('#btn_remove').click(function() {
			
			
			/* 
			
			if(${upload.id} == sessionid?) {
				
			}
			
			*/
			if(confirm('정말 삭제하시겠습니까?')) {
				$('#frm_edit').attr('action', '${contextPath}/upload/remove');
				$('#frm_edit').submit();
			}
			
			
		});
		
		
		
	});

	
	
</script>

	<form id="frm_edit" method='post' enctype="multipart/form-data" action="${contextPath}/upload/edit" >
		<div id="edit_info">
			<input type="hidden" name="uploadNo" value="${upload.uploadNo}">
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
				<input type="hidden" name="ip" value="${upload.uploadIp}">
			</div>
		</div>
		<hr>
		<div>
			제목
			<input type="text" value="${upload.uploadTitle}" readonly="readonly">
			<input type="hidden" name="title" value="${upload.uploadTitle}">
	 	</div>
	 	<div>
	 		내용
			<textarea readonly="readonly">${upload.uploadContent}</textarea>
			<input type="hidden" name="content" value="${upload.uploadContent}">
		</div>
		<div><br>
			<c:choose>
				<c:when test="${attachCnt == 0}">첨부파일 없음</c:when>
				<c:otherwise>첨부파일 ${attachCnt}개</c:otherwise>
			</c:choose>
			<c:forEach items="${attachList}" var="attach">
			<div>
				<a href="${contextPath}/upload/download?attachNo=${attach.attachNo}">${attach.origin}</a>
				 / 
				 다운로드 ${attach.downloadCnt} 
			</div>
			</c:forEach>
		</div>
		<div>
			<input type="button" value="게시글 수정" id="btn_edit">
			<input type="button" value="게시글 삭제" id="btn_remove">
			<input type="button" value="목록" onclick="location.href='${contextPath}/upload'">
		</div>
	</form>
</body>
</html>