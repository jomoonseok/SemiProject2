<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../../layout/header.jsp">
	<jsp:param value="메인게시판" name="title" />
</jsp:include>
<style>
	.enable_link, .enable_link2:hover {
		cursor:pointer;
	}
</style>
<script>

	$(document).ready(function(){
		fn_getUserList();
		fn_changePage();
		fn_removeUser();
		fn_SleepUser();
		fn_getSearchUsers();
		fn_changeSearchPage();
		fn_init();
	});
	
	
		
	function fn_getUserList(){
		$.ajax({
			type: 'get',
			url: '${contextPath}/admin/userList',
			data: 'page=' + $('#page').val(),
			dataType: 'json',
			success: function(resData){
				$('#list').empty();
				$.each(resData.list, function(i, user){
					var tr = '';
					tr += '<tr>';
					tr += '<td>'+ user.userNo +'</td>';
					tr += '<td><a href="${contextPath}/admin/boardList?id=' + user.id + '">'+ user.id +'</a></td>';
					tr += '<td>'+ user.name +'</td>';
					tr += '<td>'+ user.gender +'</td>';
					tr += '<td>'+ user.email +'</td>';
					tr += '<td>'+ user.mobile +'</td>';
					moment.locale('ko-KR');
					tr += '<td>'+ moment(user.joinDate).format('YY/MM/DD') +'</td>';
					tr += '<td>'+ user.point +'</td>';
					tr += '<td>'; 
					tr += '<input type="checkbox" name="chkRemove" class="chk_remove" value="' + moment(user.joinDate).format('YY/MM/DD') + '" data-id="' + user.id + '">';
					tr += '</td>';
					tr += '<td>'; 
					tr += '<input type="checkbox" name="chkSleep" class="chk_sleep" value="' + user.id + '">';
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
		$('#btn_remove').click(function(){
			if($('.chk_remove').is(':checked')){
				
				var joinDateValueArr = [];
				var idValueArr = [];
				
			    $("input[name='chkRemove']:checked").each(function(i) {
			    	idValueArr.push($(this).data('id'));
			    	joinDateValueArr.push($(this).val());
			    });
				var valueObj = {"idValueArr" : idValueArr, "joinDateValueArr" : joinDateValueArr}
				
				if(confirm('유저를 삭제할까요?')){
					$.ajax({
						type: 'post',
						url: '${contextPath}/admin/removeUser',
						data: valueObj,
						dataType: 'json',
						success: function(resData){
							if(resData.isRemove){
								alert('삭제되었습니다.');
								fn_getUserList();
							}
						}
					});
				}
			}else{
				alert('체크하세요');
			}
		});
	}
	
	function fn_SleepUser(){
		$('#btn_sleep').click(function(){
			if($('.chk_sleep').is(':checked')){
				
				var idValueArr = [];
				
			    $("input[name='chkSleep']:checked").each(function(i) {
			    	idValueArr.push($(this).val());
			    });
				var valueObj = {"idValueArr" : idValueArr}
				
				if(confirm('유저를 휴면 처리할까요?')){
					$.ajax({
						type: 'post',
						url: '${contextPath}/admin/sleepUser',
						data: valueObj,
						dataType: 'json',
						success: function(resData){
							if(resData.isSleep){
								alert('휴면 처리 되었습니다.');
								fn_getUserList();
							}
						}
					});
				}
			}else{
				alert('체크하세요');
			}
		});
	}
	
	function fn_getSearchUsers(){
		$('#btn_search').click(function(){
			$.ajax({
				data: 'GET',
				url: '${contextPath}/admin/searchUsers',
				data: 'page=' + $('#page').val() + '&column=' + $('#column').val() + '&searchText=' + $('#searchText').val(),
				datatype: 'json',
				success: function(resData){
					$('#list').empty();
					$.each(resData.users, function(i, user){
						var tr = '';
						tr += '<tr>';
						tr += '<td>'+ user.userNo +'</td>';
						tr += '<td><a href="">'+ user.id +'</a></td>';
						tr += '<td>'+ user.name +'</td>';
						tr += '<td>'+ user.gender +'</td>';
						tr += '<td>'+ user.email +'</td>';
						tr += '<td>'+ user.mobile +'</td>';
						moment.locale('ko-KR');
						tr += '<td>'+ moment(user.joinDate).format('YY/MM/DD') +'</td>';
						tr += '<td>'+ user.point +'</td>';
						tr += '<td>'; 
						tr += '<input type="checkbox" name="chkRemove" class="chk_remove" value="' + moment(user.joinDate).format('YY/MM/DD') + '" data-id="' + user.id + '">';
						tr += '</td>';
						tr += '<td>'; 
						tr += '<input type="checkbox" name="chkSleep" class="chk_sleep" value="' + user.id + '">';
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
						paging += '<span class="enable_link2" data-page="'+ (pageUtil.beginPage - 1) +'">◀</span>';
					}
					// 페이지번호
					for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++) {
						if(p == $('#page').val()){
							paging += '<strong>' + p + '</strong>';
						} else {
							paging += '<span class="enable_link2" data-page="'+ p +'">' + p + '</span>';
						}
					}
					// 다음 블록
					if(pageUtil.endPage != pageUtil.totalPage){
						paging += '<span class="enable_link2" data-page="'+ (pageUtil.endPage + 1) +'">▶</span>';
					}
					$('#paging').append(paging);
				},
				error: function(jqXHR){
					$('#list').empty();
					$('<tr>')
					.append( $('<td>').attr('colspan', '10').text('검색결과가 없습니다.') )
					.appendTo('#list');
				}
				
			}); 
			
		}); 
	}
	
	function fn_changeSearchPage(){
		$(document).on('click', '.enable_link2', function(){
			$('#page').val( $(this).data('page') );
			$.ajax({
				data: 'GET',
				url: '${contextPath}/admin/searchUsers',
				data: 'page=' + $('#page').val() + '&column=' + $('#column').val() + '&searchText=' + $('#searchText').val(),
				datatype: 'json',
				success: function(resData){
					$('#list').empty();
					$.each(resData.users, function(i, user){
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
						tr += '<td>'; 
						tr += '<input type="checkbox" name="chkRemove" class="chk_remove" value="' + moment(user.joinDate).format('YY/MM/DD') + '" data-id="' + user.id + '">';
						tr += '</td>';
						tr += '<td>'; 
						tr += '<input type="checkbox" name="chkSleep" class="chk_sleep" value="' + user.id + '">';
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
						paging += '<span class="enable_link2" data-page="'+ (pageUtil.beginPage - 1) +'">◀</span>';
					}
					// 페이지번호
					for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++) {
						if(p == $('#page').val()){
							paging += '<strong>' + p + '</strong>';
						} else {
							paging += '<span class="enable_link2" data-page="'+ p +'">' + p + '</span>';
						}
					}
					// 다음 블록
					if(pageUtil.endPage != pageUtil.totalPage){
						paging += '<span class="enable_link2" data-page="'+ (pageUtil.endPage + 1) +'">▶</span>';
					}
					$('#paging').append(paging);
				}
			});
		});
	}
	
	function fn_init(){
		$('#btn_init').click(function(){
			fn_getUserList();
		});
	}
	
</script>
	
	
	
	<div>
		<form id=frm_search>
			<select id="column" name="column">
				<option value="USER_NO">번호</option>
				<option value="ID">아이디</option>
				<option value="NAME">이름</option>
			</select>
			<input type="text" id="searchText" name="searchText">
			<input type="button" id="btn_search" value="검색">
			<input type="button" id="btn_init" value="초기화">
		</form>
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
					<td id="sleep"><input type="button" value="휴면" id="btn_sleep"></td>
				</tr>
			</thead>
			<tbody id="list"></tbody>
			<tfoot id="paging"></tfoot>
		</table>
	</div>
	<input type="hidden" id="page" value="1">

</body>
</html>