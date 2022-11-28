<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/jquery-3.6.1.min.js"></script>
<script src="${contextPath}/resources/js/agree.js"></script>
<link rel="stylesheet" href="${contextPath}/resources/css/agree.css"/>
</head>
<body>
   
   <div>
   
      <h1>약관 동의하기</h1>
      
      <form id="frm_agree" action="${contextPath}/user/join/write">
         
         <div>
            <input type="checkbox" id="check_all" class="blind">  <!-- input의 id와 label의 for의 이름 통일 -->
            <label for="check_all" class="lbl_all">모두 동의</label>
         </div>
         
         <hr>
         
          <div>
            <input type="checkbox" id="service" class="check_one blind">  <!-- class가 2개. (check_one, blind) -->
            <label for="service" class="lbl_one">이용약관 동의(필수)</label> 
            <div>
               <textarea>본 약관은...</textarea>
            </div>
         </div>
         
          <div>
            <input type="checkbox" id="privacy" class="check_one blind">
            <label for="privacy" class="lbl_one">개인정보수집 동의(필수)</label>
            <div>
               <textarea>개인정보보호법에 따라...</textarea>
            </div>
         </div>
         
          <div>
            <input type="checkbox" id="location" name="location" class="check_one blind">  <!-- 선택이라 코드를 따로 주기 위해 name 작성 -->
            <label for="location" class="lbl_one">위치정보수집 동의(선택)</label>
            <div>
               <textarea>위치정보 ...</textarea>
            </div>
         </div>
         
         <div>
            <input type="checkbox" id="promotion" name="promotion" class="check_one blind">
            <label for="promotion" class="lbl_one">마케팅 동의(선택)</label>
            <div>
               <textarea>이벤트 ...</textarea>
            </div>
         </div>
         
         <hr>
         
         <div>
            <input type="button" value="취소" onclick="history.back();"> <!-- button이 2개면 하나는 input타입으로 작성 -->
            <button>다음</button>
         </div>
         
      </form>
         
      
   </div>
      

</body>
</html>