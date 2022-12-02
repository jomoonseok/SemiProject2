<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="관리자페이지" name="title" />
</jsp:include>
	
<style>
	body {
	  padding: 21px;
	}
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
</style>
	
	
	
	<h1>${id}님의 게시글</h1>
	
	<span style="margin-left: 50px; ">전체 글 (${totalRecord})</span>
	
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
						${paging}
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	
</body>
</html>