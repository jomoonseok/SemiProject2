<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- header.jsp로 parameter 넘기기 -->
<jsp:include page="../layout/header.jsp">
	<jsp:param value="${gall.gallNo}번 게시글" name="title"/>
</jsp:include>

<div>
	
	<h3>${gall.gallTitle}</h3>
	
	<div>
		<span>작성일 <fmt:formatDate value="${gall.gallCreateDate}" pattern="yyyy. M. d HH:mm"/></span>
		&nbsp;&nbsp;&nbsp;
		<span>수정일 <fmt:formatDate value="${gall.gallModifyDate}" pattern="yyyy. M. d HH:mm"/></span>
	</div>
	
	<div>
		<span>조회수 <fmt:formatNumber value="${gall.gallHit}" pattern="#,##0"/></span>
	</div>
	
	<hr>
	
	<div>
		${gall.gallContent}
	</div>
	
	<div>
		<form id="frm_btn" method="post">
			<input type="hidden" name="gallNo" value="${gall.gallNo}">
			<input type="button" value="수정" id="btn_edit_gallbrd">
			<input type="button" value="삭제" id="btn_remove_gallbrd">
			<input type="button" value="목록" onclick="location.href='${contextPath}/gall/list'">
		</form>
		<script>
			$('#btn_edit_gallbrd').click(function(){
				$('#frm_btn').attr('action', '${contextPath}/gall/edit');
				$('#frm_btn').submit();
			});
			$('#btn_remove_gallbrd').click(function(){
				if(confirm('게시글을 삭제하시겠습니까?')) {
					$('#frm_btn').attr('action', '${contextPath}/gall/remove');
					$('#frm_btn').submit();
				}
			});
		</script>
	</div>
	
	<hr>
	
	<span id="btn_comment_list">
		댓글
		<span id="comment_count"></span>개
	</span>
	
	<hr>
	
	<div id="comment_area" class="blind">
		<div id="comment_list"></div>
		<div id="paging"></div>
	</div>
	
	<hr>
	
	<div>
		<form id="frm_add_comment">
			<div class="add_commnet">
				<div class="add_comment_input">
					<input type="text" name="content" id="content" placeholder="댓글을 작성하려면 로그인 해 주세요.">
				</div>
				<div class="add_comment_btn">
					<!-- ajax로 할거라 submit필요없이 바로 button -->
					<input type="button" value="작성완료" id="btn_add_comment">
				</div>
			</div>
			<input type="hidden" name="gallNo" value="${gall.gallNo}">
		</form>
	</div>
	
	<!-- 현재 페이지 번호를 저장하고 있는 hidden -->
	<input type="hidden" id="page" value="1">
	
	
</div>

</body>
</html>