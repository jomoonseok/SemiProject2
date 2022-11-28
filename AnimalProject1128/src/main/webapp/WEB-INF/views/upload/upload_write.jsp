<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="파일 업로드 게시판" name="title" />
</jsp:include>


	<form method='post' enctype="multipart/form-data" action="${contextPath}/upload/add" >
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title">
	 	</div>
	 	<div>
	 		<label for="content">내용</label>
			<textarea id="content" name="content"></textarea>
		</div>
		<div>
			<label for="files">첨부</label>
			<input type="file" name="files" value="파일선택" multiple="multiple">
		</div>
		<div>	
			<button>작성완료</button>
			<input type="button" value="목록" onclick="location.href='${contextPath}/upload'">
		</div>
	</form>
</body>
</html>