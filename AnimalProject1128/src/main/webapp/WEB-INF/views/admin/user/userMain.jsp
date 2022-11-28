<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../../layout/header.jsp">
	<jsp:param value="메인게시판" name="title" />
</jsp:include>
<script>

	$(document).ready(function(){
		fn_getUserList();
		
	});
	
	
		
	function fn_getUserList(){
		$.ajax({
			type: 'get',
			url: '${contextPath}/admin/userList',
			dataType: 'json',
			success: function(resData){
				$('#list').empty();
				$.each(resData, function(i, user){
					$('<tr>')
					.append( $('<td>').text(user.userNo) )
					.append( $('<td>').text(user.id) )
					.append( $('<td>').text(user.name) )
					.append( $('<td>').text(user.gender) )
					.append( $('<td>').text(user.email) )
					.append( $('<td>').text(user.mobile) )
					.append( $('<td>').text(user.joinDate) )
					.append( $('<td>').text(user.point) )
					.append( $('<td>').append( $('<input>').prop('type', 'button').prop('value', '탈퇴').prop('id', 'btn_remove') ))
					.appendTo('#list');
				});
			}
		});
	}
	
	function fn_removeUser(){
		$('#btn_remove').click(function(){
			location.href='${contextPath}/admin/removeUser?id=' + $(this).data('id');
		})
	}
	
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
					<td>보유포인트</td>
					<td></td>
				</tr>
			</thead>
			<tbody id="list"></tbody>
			<tfoot>
				<tr>
					<td colspan="9">${paging}</td>
				</tr>
			</tfoot>
		</table>
	</div>

</body>
</html>