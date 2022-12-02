<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!-- header.jsp로 parameter 넘기기 -->
<jsp:include page="../layout/header.jsp">
	<jsp:param value="${gall.gallNo}번 게시글" name="title"/>
</jsp:include>
<style>
	.blind {
		display: none;
	}
	
	.dislike {
		height: 30px;
		width: 30px;
	}
	
	
	
</style>

<div>
	
	<h3>${gall.gallTitle}</h3>
	
	<div>
		<span>작성일 <fmt:formatDate value="${gall.gallCreateDate}" pattern="yyyy. M. d HH:mm"/></span>
		&nbsp;&nbsp;&nbsp;
		<span>수정일 <fmt:formatDate value="${gall.gallModifyDate}" pattern="yyyy. M. d HH:mm"/></span>
	</div>
	
	<div>
		<span>조회수 <fmt:formatNumber value="${gall.gallHit}" pattern="#,##0"/></span>
	</div>
	
	<hr>
	
	<div>
		${gall.gallContent}
	</div>
	
	<div>
		<img class="dislike" src="${contextPath}/resources/images/dislike.png">
	</div>
	
	<div>
		<form id="frm_btn" method="post">
			<input type="hidden" name="gallNo" value="${gall.gallNo}">
			<input type="button" value="수정" id="btn_edit_gallbrd" class="btn_edit_gallBrd"> 
			<input type="button" value="삭제" id="btn_remove_gallbrd" class="btn_remove_gallBrd">
			<input type="button" value="목록" onclick="location.href='${contextPath}/gall/list'">
		</form>
		<script>
			$('#btn_edit_gallbrd').click(function(){
				if(${loginUser.id == gall.id}){
					$('#frm_btn').attr('action', '${contextPath}/gall/edit');
					$('#frm_btn').submit();
				} else {
					alert('작성자만 수정가능합니다.');
				}
			});
			$('#btn_remove_gallbrd').click(function(){
				if(${loginUser.id == gall.id}) {
					if(confirm('게시글을 삭제하시겠습니까?')) {
						$('#frm_btn').attr('action', '${contextPath}/gall/remove');
						$('#frm_btn').submit();
					}
				} else {
					alert('작성자만 삭제가능합니다.');
				}
			});
		</script>
	</div>
	
	<hr>
	
	<span id="btn_gallComment_list">
		댓글
		<span id="gallComment_count"></span>개
	</span>
	
	<hr>
	
	<div id="gallComment_area" class="blind">
		<div id="gallComment_list"></div>
		<div id="paging"></div>
	</div>
	
	<hr>
	
	<div>
		<form id="frm_add_gallComment">
			<div class="add_gallComment">
				<div class="add_gallComment_input">
					<input type="text" name="gallCmtContent" id="gallCmtContent" placeholder="댓글을 작성하려면 로그인 해 주세요.">
				</div>
				<div class="add_gallComment_btn">
					<input type="button" value="작성완료" id="btn_add_gallComment">
				</div>
			</div>
			<input type="hidden" name="gallNo" value="${gall.gallNo}">
		</form>
	</div>
	


	<input type="hidden" id="page" value="1">
	
	<script>
	
	fn_gallCommentCount();
	fn_switchGallCommentList();
	fn_addGallComment();
	fn_gallCommentList();
	fn_changeGallCommentPage();
	fn_removeGallComment();
	fn_switchGallReplyArea();
	
	
	function fn_gallCommentCount() {
		$.ajax({
			type: 'get',
			url: '${contextPath}/gall/comment/getCount',
			data: 'gallNo=${gall.gallNo}',
			dataType: 'json',
			success: function(resData) {
				$('#gallComment_count').text(resData.gallCommentCount);
			}  // success
		});  // ajax
	}  // fn_gallCommentCount
	
	function fn_switchGallCommentList() {
		$('#btn_gallComment_list').click(function(){
			$('#gallComment_area').toggleClass('blind');
		});
	}  // fn_switchGallCommentList
	
	function fn_addGallComment() {
		$('#btn_add_gallComment').click(function() {
			if($('#gallComment').val() == '') {
				alert('댓글 내용을 입력하세요.');
				return;
			}
			$.ajax({
				type: 'post',
				url: '${contextPath}/gall/comment/add',
				data: $('#frm_add_gallComment').serialize(),
				dataType: 'json',
				success: function(resData){ 
					if(resData.isGallCommentAdd) {
						alert('댓글이 등록되었습니다.');
						$('#gallCmtContent').val('');
						fn_gallCommentList();
						fn_gallCommentCount();
					}
				}  // success
			});  // ajax
		});  // click
	}  // fn_addGallComment
	
	function fn_gallCommentList() {
		$.ajax({
			type: 'get',
			url:'${contextPath}/gall/comment/list',
			data: 'gallNo=${gall.gallNo}&page=' + $('#page').val(),
			dataType: 'json',
			success: function(resData) {
				$('#gallComment_list').empty();
				$.each(resData.gallCommentList, function(i, gallComment){
					var div = '';
					if(gallComment.depth == 0) {
						div += '<div>';
					} else {
						div += '<div style="margin-left: 40px;">';
					}
					if(gallComment.state == 1) {
						div += '<div>';
						div += gallComment.gallCmtContent
						div += '<input type="button" value="삭제" class="btn_gallComment_remove" data-gallCmtNo="' + gallComment.gallCmtNo + '">';
						if(gallComment.depth == 0) {
							div += '<input type="button" value="답글" class="btn_gallReply_area">';
						}
						div += '</div>';
					} else {
						if(gallComment.depth == 0) {
							div += '<div>삭제된 댓글입니다.</div>';
						} else {
							div += '<div>삭제된 답글입니다.</div>';
						}
					}
					div += '<div>';
					moment.locale('ko-KR');
					div += '<span style="font-size: 12px; color: silver;">' + moment(gallComment.gallCmtCreateDate).format('YYYY. MM. DD hh:mm') + '</span>';
					div += '</div>';
					div += '<div style="margin-left:40px;" class="gallReply_area blind">';
					div += '<form class="frm_gallReply">';
					div += '<input type="hidden" name="gallNo" value="' + gallComment.gallNo + '">';
					div += '<input type="hidden" name="groupNo" value="' + gallComment.gallCmtNo + '">';
					div += '<input typt="text" name="gallCmtContent" placeholder="답글을 작성하려면 로그인을 해주세요.">';
					
					div += '<input type="button" value="답글작성완료" class="btn_gallReply_add">';
					div += '</form>';
					div += '</div>';
					div += '</div>';
					$('#gallComment_list').append(div);
					$('#gallComment_list').append('<div style="border-bottom: 1px dotted gray;"></div>');
				});  // $.each
				$('#paging').empty();
				var pageUtil = resData.pageUtil;
				var paging = '';
				if(pageUtil.beginPage != 1) {
					paging += '<span class="enable_link" data-page="'+ (pageUtil.beginPage - 1) +'">◀</span>';
				}
				for(let p = pageUtil.beginPage; p <= pageUtil.endPage; p++) {
					if(p == $('#page').val()){
						paging += '<strong>' + p + '</strong>';
					} else {
						paging += '<span class="enable_link" data-page="'+ p +'">' + p + '</span>';
					}
				}
				if(pageUtil.endPage != pageUtil.totalPage){
					paging += '<span class="enable_link" data-page="'+ (pageUtil.endPage + 1) +'">▶</span>';
				}
				$('#paging').append(paging);
			}  // success
		}); // ajax
	}  // fn_gallCommentList
	
	function fn_changeGallCommentPage() {
		$(document).on('click', '.enable_link', function() {
			$('#page').val( $(this).data('page') );
			fn_gallCommentList();
		});
	}  //fn_changeGallCommentPage
	
	function fn_removeGallComment(){
		$(document).on('click', '.btn_gallComment_remove', function(){
			if(confirm('삭제된 댓글은 복구할 수 없습니다. 댓글을 삭제할까요?'));
			$.ajax({
				type: 'post',
				url: '${contextPath}/gall/comment/remove',
				data: 'gallCmtNo=' + $(this).data('gallCmtNo'),
				dataType: 'json',
				success: function(resData){ 
					if(resData.isGallCommentRemove){
						alert('댓글이 삭제되었습니다.');
						fn_gallCommentList();
						fn_gallCommentCount();
					}
				}
			});
		});
	}  // fn_removeGallComment
	
	function fn_switchGallReplyArea() {
		$(document).on('click', '.btn_gallReply_area', function() {
			$(this).parent().next().next().toggleClass('blind');
		});
	}  // fn_switchGallReplyArea
	
	function fn_addGallCommentReply() {
		$(document).on('click', '.btn_gallReply_add', function (){
			if($(this).prev().val() == ''){
				alert('답글 내용을 입력하세요.');
				return;
			}
			$.ajax({
				type: 'post',
				url: '${contextPath}/gall/comment/reply/add',
				data: $(this).closest('.frm_gallReply').serialize(),
				dataType: 'json',
				success: function(resData) {
					if(resData.isGallCommentAdd) {
						alert('답글이 등록되었습니다.');
						fn_gallCommentList();
						fn_gallCommentCount();
					}
				}
			});
		});
	}  // fn_addGallCommentReply
	
	</script>
	
	
</div>

</body>
</html>