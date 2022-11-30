<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../../layout/header.jsp">
	<jsp:param value="메인게시판" name="title" />
</jsp:include>
<style>
	.enable_link:hover {
		cursor:pointer;
	}
</style>
<script>

	$(document).ready(function(){
		fn_getUserList();
		fn_changePage();
		fn_removeUser();
		
	});
	
	
		
	function fn_getUserList(){
		$.ajax({
			type: 'get',
			url: '${contextPath}/admin/userList',
			data: 'page=' + $('#page').val(),
			dataType: 'json',
			success: function(resData){
				console.log(resData);
				
				$('#list').empty();
				$.each(resData.list, function(i, user){
					var tr = '';
					tr += '<tr>';
					tr += '<td>'+ user.userNo +'</td>';
					tr += '<td>'+ user.id +'</td>';
					tr += '<td>'+ user.name +'</td>';
					tr += '<td>'+ user.gender +'</td>';
					tr += '<td>'+ user.email +'</td>';
					tr += '<td>'+ user.mobile +'</td>';
					moment.locale('ko-KR');
					tr += '<td>'+ moment(user.joinDate).format('YY/MM/DD') +'</td>';
					tr += '<td>'+ user.point +'</td>';
					tr += '<td>'; // form
					//tr += '<form class="frm_remove">';
					//tr += '<input type="hidden" name="userNo" value="' + user.userNo + '">';
					tr += '<input type="hidden" name="id" value="' + user.id + '">';
					tr += '<input type="hidden" name="joinDate" value="' + moment(user.joinDate).format('YY/MM/DD') + '">';
					//tr += '<button class="btn_remove">탈퇴</button>';
					tr += '<input type="checkbox" name="chkRemove" class="chk_remove" id="' + user.userNo + '">';
					//tr += '</form>';
					tr += '</td>';
					tr += '</tr>';
					$('#list').append(tr);
				});
				// 페이징
				$('#paging').empty();
				var pageUtil = resData.pageUtil;
				var paging = '';
				// 이전 블록
				if(pageUtil.beginPage != 1) {
					paging += '<span class="enable_link" data-page="'+ (pageUtil.beginPage - 1) +'">◀</span>';
				}
				// 페이지번호
				for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++) {
					if(p == $('#page').val()){
						paging += '<strong>' + p + '</strong>';
					} else {
						paging += '<span class="enable_link" data-page="'+ p +'">' + p + '</span>';
					}
				}
				// 다음 블록
				if(pageUtil.endPage != pageUtil.totalPage){
					paging += '<span class="enable_link" data-page="'+ (pageUtil.endPage + 1) +'">▶</span>';
				}
				$('#paging').append(paging);
			}
		});
	}
	
	function fn_changePage(){
		/* enable_link는 동적으로 만들었기 때문에 지금까지 사용한 방법으로는 이벤트를 사용할 수 없다. */
		$(document).on('click', '.enable_link', function(){
			$('#page').val( $(this).data('page') );
			fn_getUserList();
		});
	}
	
	function fn_removeUser(){
		$(document).on('click', '#btn_remove', function(){
			if($('.chk_remove').is(':checked')){
				console.log($('.chk_remove').val());
				/*
				for(){
					if($('.chk_remove input[type=checkbox]').attr('checked')){
						var idArr = [];
					}
				}
				*/			
				if(confirm('유저를 삭제할까요?')){
					$.ajax({
						type: 'post',
						url: '${contextPath}/admin/removeUser',
						data: $('#frm_list').serialize(),
						dataType: 'json',
						success: function(resData){
							if(resData.isRemove){
								alert('삭제되었습니다.');
								fn_getUserList();
							}
						}
					});
				}
			}
		});
	}
	
</script>
	
	
	<div>
		<input type="text" name="searchUser">
		<input type="button" value="검색">
	</div>
	
	<div>
		<form id="frm_list">
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
						<td id="remove"><input type="button" value="탈퇴" id="btn_remove"></td>
					</tr>
				</thead>
				<tbody id="list"></tbody>
				<tfoot id="paging"></tfoot>
			</table>
		</form>
	</div>
	<input type="hidden" id="page" value="1">

</body>
</html>