<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="자유게시판 ${free.freeNo}번 게시글" name="title" />
</jsp:include>

	<div>${free.freeNo}번 게시글</div>
	<div>작성자 : ${free.id}</div>
	<div>제목 : ${free.freeTitle}</div>
	<div>내용 : ${free.freeContent}</div>


</body>
</html>