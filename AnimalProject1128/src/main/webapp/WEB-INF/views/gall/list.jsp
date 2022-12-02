<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- header.jsp로 parameter 넘기기 -->
<jsp:include page="../layout/header.jsp">
	<jsp:param value="갤러리게시판목록" name="title"/>
</jsp:include>

<div>
	
	<h3>게시판 목록(전체 ${totalRecord}개)</h3>
	
	<div>
		<input type="button" value="게시글 작성하기" id="btn_gallWrite">
	</div>
	<script>
		$('#btn_gallWrite').click(function(){
			if(${loginUser != null}) {
				location.href="${contextPath}/gall/write"
			} else {
				alert('로그인한 회원만 작성가능합니다.');
				location.href="${contextPath}/user/login/form"
			}
		});
	</script>
	<div>
		<table border="1">
			<thead>
				<tr>
					<td>글번호</td>
					<td>제목</td>
					<td>작성자</td>
					<td>조회수</td>
					<td>작성일</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${gallList}" var="gall" varStatus="vs">
					<tr>
						<td>${beginNo - vs.index}</td>
						<td><a id="btn_galldetail"> ${gall.gallTitle}</a></td>
						<td>${gall.id}</td>						
						<td>${gall.gallHit}</td>
						<td>${gall.gallCreateDate}</td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5">
						${paging}
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
	<script>
	//href="${contextPath}/gall/increase/hit?gallNo=${gall.gallNo}"
		$('#btn_galldetail').click(function(){
			if(${loginUser != null}) {
				location.href="${contextPath}/gall/increase/hit?gallNo=${gall.gallNo}"
			} else {
				alert('로그인한 회원만 조회가능합니다.');
				location.href="${contextPath}/user/login/form"
			}
		});
	</script>
	
</div>

</body>
</html>