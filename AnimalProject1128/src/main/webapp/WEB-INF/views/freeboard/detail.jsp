<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="자유게시판 ${free.freeNo}번 게시글" name="title" />
</jsp:include>

<style>
	@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css');
</style>
	
	<div><a href="${contextPath}/freeboard/list">자유게시판 > </a></div>
	<div><i class="fa-solid fa-check"></i>&nbsp; ${free.freeNo}번 게시글</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 조회수 : ${free.freeHit}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 작성자 : ${free.id}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 제목 : ${free.freeTitle}</div>
	<hr>
	<div>내용 ${free.freeContent}</div>
	
	
	<hr>

	댓글
	<div id="comment_area" class="blind">
		<div id="comment_list"></div>
		<div id="paging"></div>
	</div>	
	<div>
		<form id="frm_add_comment">
			<div class="add_comment">
				<div class="add_comment_input">
					<input type="text" name="content" id="content" size="50" placeholder="댓글을 작성하려면 로그인을 해야합니다.">
					<span class="add_comment_btn">
						<input type="button" value="등록" id="btn_add_comment">
					</span>
				</div>
			</div>
			<input type="hidden" name="freeNo" value="${free.freeNo}">
		</form>
	</div>
	
	<hr>
	
	<span id="btn_comment_list">
		<i class="fa-regular fa-comment-dots"></i>&nbsp;댓글
		<span id="comment_count"></span>개
	</span>
	
	<hr> 
	
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
					alert('취소되었습니다.');
				}
			});
		</script>
	</div>
	
	
	


</body>
</html>