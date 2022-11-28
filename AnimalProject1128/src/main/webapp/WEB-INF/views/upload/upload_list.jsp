<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="파일 업로드 게시판" name="title" />
</jsp:include>

<style>
	tbody > tr:hover {
		cursor: pointer;
	
	}
</style>

<script>

	$('#btn_find').click(function() {
		
	});
</script>
	
	<div id="upload_list_body">
	
		<div id="upload_list_body_top">
			
			<input type="button" value="글쓰기" id="btn_write" onclick="location.href='${contextPath}/upload/write'">
			<input type="button" value="목록보기" id="btn_list">
			
			<form id="frm_find" action="${contextPath}/upload/find">
				
				<select id="opt_column" name="opt_column">
					<option value="find_all">전체검색</option>
					<option value="find_name">이름검색</option>
					<option value="find_title">제목검색</option>
					<option value="find_content">내용검색</option>
					<option value="find_id">아이디검색</option>
				</select>
				
				<input type="text" name="query">
				<input type="submit" value="검색">
			</form>
		
		</div>
		
		<div id="upload_list_body_body">
			<table border="1">
				<thead>
					<tr>
						<td>번호</td>
						<td>작성자</td>
						<td>제목</td>
						<td>게시일자</td>
						<td>조회수</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${uploadList}" var="upload">
					<tr onclick="location.href='${contextPath}/upload/detail?uploadNo=${upload.uploadNo}'">
						<td>${upload.uploadNo}</td>
						<td>${upload.id}</td>
						<td>${upload.uploadTitle}</td>
						<td>${upload.uploadCreateDate}</td>
						<td>${upload.uploadHit}</td>
					</tr> 
				</c:forEach>
				</tbody>
				<tfoot>
					
					
				
				</tfoot>
			</table>
		</div>
	
	</div>
	
	
</body>
</html>