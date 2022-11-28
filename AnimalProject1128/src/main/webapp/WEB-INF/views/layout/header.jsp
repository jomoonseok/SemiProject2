<%@page import="java.util.Optional"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Optional<String> opt = Optional.ofNullable(request.getParameter("title"));
	String title = opt.orElse("Welcome");
	pageContext.setAttribute("title", title);
	pageContext.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
<script src="${contextPath}/resources/js/moment-with-locales.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.css">
<style>
	
	h1{
		text-align: center;
	}
	a{
		color: #000;
		text-decoration: none;
	}
    #navigation a {
        color: #000;
        text-decoration: none;
        font-weight: bold;
    }
    #navigation {
        width: 500px;
        border-top: 1px solid gray;
        border-bottom: 1px solid gray;
        list-style: none; /* 글머리기호 없애기*/
        display: flex;    /* 메뉴들의 수평 레이아웃 배치 */
        padding: 0;
        margin: 0 auto 45px;
    }
    #navigation > li {
        width: 125px;            /* 너비 */
        height: 50px;           /* 높이 */
        text-align: center;     /* 가로 가운데 정렬 */
        line-height: 50px;      /* 세로 가운데 정렬 */
    }
    #navigation > li > a {
        display: block; /* 블록레벨의 요소만 너비/높이를 가질 수 없음 */
        width: 100%;    /* 부모 요소 li와 같은 너비 */
        height: 100%;   /* 부모 요소 li와 같은 높이 */
    }
    #navigation > li:hover {
        height: 47px;   /* height와 border값을 합쳐서 원래 크기 50px을 만듬 */
        border-bottom: 3px solid limegreen;
    }
    #navigation > li:hover > a {
        color: limegreen;
    }
	
</style>
</head>
<body>
	<header>
		<h1><a href="${contextPath}">5조 게시판</a></h1>
		
		<ul id="navigation">
			<li><a href="${contextPath}/freeboard/list">자유게시판</a></li>
			<li><a href="${contextPath}/gall/list">사진게시판</a></li>
			<li><a href="${contextPath}/upload">파일게시판</a></li>
			<li><a href="${contextPath}/admin/main">관리자페이지</a></li>
		</ul>
	</header>	
	
	