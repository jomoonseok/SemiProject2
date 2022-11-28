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
	
	<h1>${gall.gallTitle}</h1>
	
	<div>
		<span>✏📝✏📝작성일 <fmt:formatDate value="${gall.gallCreateDate}" pattern="yyyy. M. d HH:mm"/></span>
		&nbsp;&nbsp;&nbsp;
		<span>✏📝✏ 수정일 <fmt:formatDate value="${gall.gallModifyDate}" pattern="yyyy. M. d HH:mm"/></span>
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
			<input type="button" value="목록" onclick="location.href='${contectPath}/gall/list'">
		</form>
		<script>
			$('#btn_edit_gallbrd').click(function(){
				$('#frm_btn').attr('action', '${contextPath}/gall/edit');
				$('#frm_btn').submit();
			});
			$('#btn_remove_gallbrd').click(function(){
				if(confirm('게시글을 삭제하시겠습니까?')) {
					// cascade가 아닌 set null이기 때문에 함께 삭제 되진 않음
					$('#frm_btn').attr('action', '${contextPath}/gall/remove');
					$('#frm_btn').submit();
				}
			});
		</script>
	</div>
	
</div>

</body>
</html>