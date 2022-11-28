<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="블로그목록" name="title" />
</jsp:include>

	
	<div>
		<a href="${contextPath}/freeboard/write">글쓰기</a>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		전체 글 (${totalRecord})
	</div>
	
	
	<!-- c:if test="${loginUser != null}" 로그인이 되어있다면 -->
	<!-- c:if test="${loginUser.id == 'admin'}"  관리자가 로그인이라면 -->
	
	<div>
		<table border="1">
			<thead>
				<tr>
					<td>순번</td>
					<td>작성자</td>
					<td>제목</td>
					<td>작성날짜</td>
					<td>조회수</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${freeBoardList}" var="free">
					<tr>
						<td>${free.freeNo}</td>
						<td>${free.id}</td>
						<td><a href="${contextPath}/freeboard/detail?freeNo=${free.freeNo}">${free.freeTitle}</a></td>
						<td>${free.freeCreateDate}</td>
						<td>${free.freeHit}</td>
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