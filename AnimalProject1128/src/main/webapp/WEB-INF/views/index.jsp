<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="layout/header.jsp">
	<jsp:param value="메인게시판" name="title" />
</jsp:include>

<style>
        body {
            padding: 10px;
        }
        
        h2 {
        	text-align: center;
        }
        
        .main_section {
            display: flex;
        }

        .left_section {
        	border: 1px solid gray;
            margin-right: 20px;
            flex: 4;
            /* width: 80%; */
        }
        
        .left_section_top {
        	border: 1px solid gray;
        	display: flex;
        	height:50%;
        }
        
        .left_section_bottom {
        	border: 1px solid gray;
        	display: flex;
        	height:50%;
        }
        
        .left_article{
        	border: 1px solid gray;
        	width: 50%;
        }

        .right_section {
            border: 1px solid gray;
            flex: 1;
            /* width: 20%; */
        }
        
        .right_article {
        	border: 1px solid gray;
        }

        .img {
            padding: 20px;
            background-color: #888;
        }
</style>

	<section class="main_section">
        <section class="left_section">
        	<section class="left_section_top">
	            <article class="left_article">
	                <h2>자유게시판</h2>
	            </article>
	            <article class="left_article">
	                <h2>Title2</h2>
	            </article>
            </section>
            <section class="left_section_bottom">
	            <article class="left_article">
	                <h2>갤러리게시판</h2>
	            </article>
	            <article class="left_article">
	                <h2>파일게시판</h2>
	            </article>
            </section>
        </section>
        <section class="right_section">
            <article class="right_article">
                <h2><a href="${contextPath}/user/login/form">로그인</a></h2>
            </article>
            <article class="right_article">
                <h2>포인트</h2>
                <ol>
                	<li>홍길동</li>
                	<li>고길동</li>
                	<li>김길동</li>
                </ol>
            </article>
            <article class="right_article">
                <h2>Title3</h2>
                <div class="img"></div>
                <div class="img"></div>
                <p>description</p>
            </article>
        </section>
    </section>

</body>
</html>