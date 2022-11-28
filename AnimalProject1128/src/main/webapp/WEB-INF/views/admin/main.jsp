<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="관리자페이지" name="title" />
</jsp:include>
<script>
	$(document).ready(function(){
		
		
		
		
			
			
			
		});
		
		
	});
</script>
	
	<h1>메인페이지</h1>
	
	<div>
		<div>
			<a href="${contextPath}/admin/userList">회원관리</a>
			<a href="${contextPath}/admin/freeList">게시판관리</a>
			<a href="${contextPath}/admin/galleryList">갤러리관리</a>
			<a href="${contextPath}/admin/fileList">파일관리</a>
		</div>
	</div>
	
</body>
</html>