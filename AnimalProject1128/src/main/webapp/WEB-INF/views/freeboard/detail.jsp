<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="자유게시판 ${free.freeNo}번 게시글" name="title" />
</jsp:include>

	<div>${free.freeNo}번 게시글</div>
	<div>조회수 : ${free.freeHit}</div>
	<div>작성자 : ${free.id}</div>
	<div>제목 : ${free.freeTitle}</div>
	<div>내용 : ${free.freeContent}</div>
	
	<div>
		<form id="frm_btn" method="post">
			<input type="hidden" name="freeNo" value="${free.freeNo}">
			<input type="button" value="수정" id="btn_edit_freeboard">
			<input type="button" value="삭제" id="btn_remove_freeboard">
			<input type="button" value="목록" onclick="location.href='${contextPath}/freeboard/list'">
		</form>
		<script>
			$('#btn_edit_freeboard').click(function(){
				$('#frm_btn').attr('action', '${contextPath}/freeboard/edit');
				$('#frm_btn').submit();
			});
			
			$('#btn_remove_freeboard').click(function(){
				if(confirm('게시글을 삭제하면 게시글에 달린 댓글을 더 이상 확인할 수 없습니다. 삭제하시겠습니까?')){
					$('#frm_btn').attr('action', '${contextPath}/freeboard/remove');
					$('#frm_btn').submit();
				} else {
					alert('게시글 삭제를 취소하였습니다.');
				}
				
			});
		</script>
		
	</div>


</body>
</html>