<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="게시글 작성" name="title" />
</jsp:include>

	
	<div>
		<input type="button" value="글쓰기" id="write">
		<c:if test="${loginUser != null}">
			<script>
				$('#write').click(function(){
					location.href = "${contextPath}/freeboard/write";
				});
			</script>
		</c:if>
		
		<c:if test="${loginUser == null}">
			<script>
				$('#write').click(function(){
					if(confirm('글을 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동 하시겠습니까?')) {
						location.href = "${contextPath}/user/login/form";
					}
				});
			</script>
		</c:if>
		<span style="margin-left: 50px; ">전체 글 (${totalRecord})</span>
	</div>
	

	<div>
		<table border="1">
			<thead>
				<tr style="text-align: center; color: rgb(78, 78, 78);">
					<td><strong>순번</strong></td>
					<td><strong>작성자</strong></td>
					<td><strong>제목</strong></td>
					<td><strong>작성날짜</strong></td>
					<td><strong>조회수</strong></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${freeBoardList}" var="free">
					<tr>
						<td style="text-align: center;">${free.freeNo}</td>
						<td style="text-align: center;">${free.id}</td>
						<td><a href="${contextPath}/freeboard/increse/hit?freeNo=${free.freeNo}">${free.freeTitle}</a></td>
						<td style="text-align: center;">${free.freeCreateDate}</td>
						<td style="text-align: center;">${free.freeHit}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						${paging}
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
	


</body>
</html>