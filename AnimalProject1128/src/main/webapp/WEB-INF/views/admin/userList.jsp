<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="메인게시판" name="title" />
</jsp:include>
<script>
	$(document).ready(function(){
		
		$('#btn_remove').click(function(){
			location.href='${contextPath}/admin/removeUser?id=' + $(this).data('id');
		})
		
	});
</script>

	<div>
		<input type="text" name="searchUser">
		<input type="button" value="검색">
	</div>
	
	<div>
		<table border="1">
			<thead>
				<tr>
					<td>번호</td>
					<td>아이디</td>
					<td>이름</td>
					<td>성별</td>
					<td>이메일</td>
					<td>핸드폰</td>
					<td>가입일</td>
					<td>닉네임</td>
					<td></td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="user" items="${userList}" varStatus="vs">
					<tr>
						<td>${beginNo - vs.index}</td>
						<td>${user.id}</td>
						<td>${user.name}</td>
						<td>${user.gender}</td>
						<td>${user.email}</td>
						<td>${user.mobile}</td>
						<td>${user.joinDate}</td>
						<td>${user.nickname}</td>
						<td><input type="button" value="삭제" id="btn_remove" data-id="${user.id}"></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9">${paging}</td>
				</tr>
			</tfoot>
		</table>
	</div>

</body>
</html>