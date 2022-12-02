<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="게시글 작성" name="title" />
</jsp:include>

<style>

@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

	body {
	  padding: 21px;
	}
	
	h3 {
		font-family: 'Noto Sans KR', sans-serif;
		color: rgb(127, 127, 127);
		text-align: center;
	}
	
	<%-- 1. 버튼 --%>
	.button {
	  font-size: 13px;
	  line-height: 25px;
	  background: rgb(255, 255, 255);
	  color: rgb(127, 127, 127);
	  box-shadow: 0 2px 5px rgba(0,0,0,.25);
	  width: 50px;
	  border: 1px solid rgb(115, 104, 93);
	  border-radius: 5px;
	  overflow: hidden;
	  transition-duration: 0.3s;
	}
	
	.button:hover {
		color: rgb(115, 104, 93);
		font-weight: bold;
		background: rgb(233, 231, 228);
	}
	
	.button:active {
		color: rgb(115, 104, 93);
		font-weight: bold;
		background: rgb(255, 255, 255);
		box-shadow: none;
	}
	
	<%-- 2. 테이블 --%>

	table {
	  font-size: 13px;
	  box-shadow: 0 2px 5px rgba(0,0,0,.25);
	  width: 100%;
	  border-collapse: collapse;
	  border-radius: 5px;
	  overflow: hidden;
	}

  
	thead {
	  font-weight: bold;
	  color: rgb(78, 78, 78);
	  background: rgb(233, 231, 228);
	  text-align: center;
	}
	
	thead > tr > th:first-of-type {
		width: 5%;
	}
	
	thead > tr > th:nth-of-type(2) {
		width: 10%;
	}
	
	thead > tr > th:nth-of-type(4) {
		width: 10%;
	}
	
	thead > tr > th:nth-of-type(5) {
		width: 5%;
	}
	  
	 td, th {
	  padding: 15px 8px;
	}
  
	 td {
	  border-bottom: 1px solid rgba(0,0,0,.1);
	  background: #fff;
	}

  
@media all and (max-width: 768px) {  
	table, thead, tbody, th, td, tr {
	  display: block;
	}
	
	<%-- 3. 페이징 --%>
	.paging {
		width: 30%;
		display: flex;
		flex-wrap: nowrap;
		justify-content: space-evenly;
		margin: 0 auto;
		cursor: default;
		
	}


</style>

	<h3>자유게시판</h3>
	<div>
		<input type="button" value="글쓰기" id="write" class="button">
		<c:if test="${loginUser != null}">
			<script>
				$('#write').click(function(){
					location.href = "${contextPath}/freeboard/write";
				});
			</script>
		</c:if>
		
		<c:if test="${loginUser == null}">
			<script>
				$('#write').click(function(){
					if(confirm('글을 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동 하시겠습니까?')) {
						location.href = "${contextPath}/user/login/form";
					}
				});
			</script>
		</c:if>
		<span style="margin-left: 50px; ">전체 글 (${totalRecord})</span>
	</div>
	

	<div>
		<table border="1">
			<thead>
				<tr>
					<th>순번</th>
					<th>작성자</th>
					<th>제목</th>
					<th>작성날짜</th>
					<th>조회수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${freeBoardList}" var="free">
					<tr>
						<td style="text-align: center;">${free.freeNo}</td>
						<td style="text-align: center;">${free.id}</td>
						<td><a href="${contextPath}/freeboard/increse/hit?freeNo=${free.freeNo}">${free.freeTitle}</a></td>
						<td style="text-align: center;">${free.freeCreateDate}</td>
						<td style="text-align: center;">${free.freeHit}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						<span class="paging">${paging}</span>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
	


</body>
</html>