<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="파일 업로드 게시판" name="title" />
</jsp:include>

<style>
	
	table {
		text-align: center;
		width: 1200px;
		margin: 30px auto 30px;
	}
	
	tbody > tr > td:nth-of-type(3):hover {
		cursor: pointer;
	}
	
	td:nth-of-type(1) {
		width: 10%;
	}
	td:nth-of-type(2) {
		width: 20%;
	}
	td:nth-of-type(3) {
		width: 45%;
	}
	td:nth-of-type(4) {
		width: 15%;
	}
	td:nth-of-type(5) {
		width: 10%;
	}
	.table_head {
		background-color: #EBEBEB;
	}
	
	tbody > tr:hover {
		background-color: #ECEDF1;
		
	}
	
	
</style>

<script>
	
	$(function() {
		$('#btn_write').click(function(event) {
			if(${loginUser.id == null}) {  // 세션에 담긴 id가 없으면 로그인 여부 확인
				
				if(confirm('게시글 작성은 로그인이 필요한 서비스입니다. 로그인 하시겠습니까?')) {  // 로그인 동의 - 로그인 화면으로
					 location.href="${contextPath}/user/login/form";
					event.preventDefault();
					return;
				} else {  // 로그인 거부 - 리스트 화면으로
					location.href="${contextPath}/upload";
				}
			} else {  // // 세션에 담긴 id가 있으면 게시글 작성으로
				location.href="${contextPath}/upload/write";
			}
			
		});
	});
	
	
</script>
	
	<div id="upload_list_body">
	
		<div id="upload_list_body_top">
			
			<input type="button" value="글쓰기" id="btn_write">
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
			<table>
				<thead>
					<tr class="table_head">
						<td>번호</td>
						<td>작성자</td>
						<td>제목</td>
						<td>게시일자</td>
						<td>조회수</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${uploadList}" var="upload">
					<tr>
						<td>${upload.uploadNo}</td>
						<td>${upload.id}</td>
						<td onclick="location.href='${contextPath}/upload/detail?uploadNo=${upload.uploadNo}'">${upload.uploadTitle}</td>
						<td>${upload.uploadCreateDate}</td>
						<td>${upload.uploadHit}</td>
					</tr> 
				</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="upload_list_body_foot">
			<table>
				<tr>
					<td colspan="6">${paging}</td>
				</tr>
			</table>
		</div>
	
	</div>
	
	
</body>
</html>