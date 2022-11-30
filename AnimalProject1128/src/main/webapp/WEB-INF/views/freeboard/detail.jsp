<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="[자유게시판] ${free.freeNo}번 게시글" name="title" />
</jsp:include>

<style>
	@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css');
	.blind {
		display: none;
	}
</style>
	
	<!-- 1. 게시글 -->
	<div><a href="${contextPath}/freeboard/list">자유게시판 > </a></div>
	<div><i class="fa-solid fa-check"></i>&nbsp; ${free.freeNo}번 게시글</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 조회수 : ${free.freeHit}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 작성자 : ${free.id}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 작성일 : ${free.freeCreateDate}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 수정일 : ${free.freeModifyDate}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 아이피 : ${free.freeIp}</div>
	<div><i class="fa-solid fa-check"></i>&nbsp; 제목 : ${free.freeTitle}</div>
	<hr>
	<div>내용 ${free.freeContent}</div>
	
	<hr>
	<!-- 2. 댓글 쓰기 -->
	댓글
	
	<div>
		<form id="frm_add_comment">
			<div class="add_comment">
				<div class="add_comment_input">
					<input type="text" name="freeCmtContent" id="content" size="50" placeholder="댓글을 작성하려면 로그인을 해야합니다.">
					<span class="add_comment_btn">
						<input type="button" value="등록" id="btn_add_comment">
					</span>
				</div>
			</div>
			<input type="hidden" name="freeNo" value="${free.freeNo}">
		</form>
	</div>	
	
	<hr>
	<!-- 3. 댓글 리스트 -->
	<span id="btn_comment_list">
		<i class="fa-regular fa-comment-dots"></i>&nbsp;댓글
		<span id="comment_count"></span>개
	</span>
	
	<hr>
	
	<div id="comment_area" class="blind">
		<div id="comment_list"></div>
		<div id="paging"></div>
	</div>
	
	<input type="hidden" id="page" value="1">
	
	
	<script>
	
		fn_commentCount();
		fn_switchCommentList();
		fn_addComment();
		fn_commentList();
		fn_addCommentReply
		fn_addCommentReply();
		
		var page = 1;
	
		// 1. 댓글 갯수
		function fn_commentCount(){
			$.ajax({
				type: 'get',
				url: '${contextPath}/freecomment/getCount',
				data: 'freeNo=${free.freeNo}',
				dataType: 'json',
				success: function(resData){
					$('#comment_count').text(resData.commentCount);
				}
			});
		}
		
		// 2. 댓글 토글
		function fn_switchCommentList(){
			$('#btn_comment_list').click(function(){
				$('#comment_area').toggleClass('blind');
			});
		}

		// 3. 댓글 추가
		function fn_addComment(){
			$('#btn_add_comment').click(function(){
				if($('#content').val() == ''){
					alert('댓글을 입력하세요.');
					return;
				}
				$.ajax({
					type: 'post',
					url: '${contextPath}/freecomment/add',
					data: $('#frm_add_comment').serialize(),					
					dataType: 'json',
					success: function(resData){
						if(resData.isAdd) {
							alert('댓글이 등록되었습니다.');
							$('#content').val('');
							fn_commentList();
							fn_commentCount();
						}
					}
				});
			});
			
			
			
		}

		
		// 4. 댓글 리스트
		function fn_commentList(){
			$.ajax({
				type: 'get',
				url: '${contextPath}/freecomment/list',
				data: 'freeNo=${free.freeNo}&page=' + $('#page').val(),
				dataType: 'json',
				success: function(resData){
					$('#comment_list').empty();
					$.each(resData.commentList, function(i, comment){
						var div = '';
						if(comment.depth == 0){
							div += '<div>';
						} else {
							div += '<div style="margin-left: 40px;">';
						}
						if(comment.state == 1) {
							div += '<div>';
							div += '<span><strong>' + comment.id + '</strong></span>';
							div += '&nbsp;';
							div += comment.freeCmtContent;
							// 작성자만 삭제할 수 있도록 if 처리 필요
							div += '&nbsp;';
							div += '<input type="button" value="삭제" class="btn_comment_remove" data-comment_no="' + comment.commentNo + '">';
							// 계층하려면 삭제!
							if(comment.depth == 0){
							div += '&nbsp;';
								div += '<input type="button" value="답글" class="btn_reply_area">';								
							}
							div += '</div>'
						} else {
							if(comment.depth == 0) {
								div += '<div>삭제된 댓글입니다.</div>';
							} else {
								div += '<div>삭제된 답글입니다.</div>';
							}
						}
						div += '<div>';
						moment.locale('ko-KR');
						div += '<span style="font-size: 12px; color: silver;">' + moment(comment.freeCmtCreateDate).format('YYYY. MM. DD hh:mm') + '</span>';
						div += '</div>';
						div += '<div style="margin-left: 40px;" class="reply_area blind">';
						div += '<form class="frm_reply">';
						div += '<input type="hidden" name="freeNo" value="' + comment.freeNo + '">';
						div += '<input type="hidden" name="groupNo" value="' + comment.freeCmtNo + '">';
						div += '<input type="text" name="content" placeholder="답글을 작성하려면 로그인을 해주세요">';
						// 로그인한 사용자만 볼 수 있도록 if 처리
						div += '<input type="button" value="답글작성완료" class="btn_reply_add">';		
						div += '</form>';
						div += '</div>';
						div += '</div>';
						$('#comment_list').append(div);
						$('#comment_list').append('<div style="border-bottom: 1px dotted gray;"></div>');

					});
					// 페이징
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
				}
					
			});
		}
		
		function fn_switchReplyArea(){
			$(document).on('click', '.btn_reply_area', function(){
				$(this).parent().next().next().toggleClass('blind');
			});
		}
		
		// 5. 대댓글 추가
		function fn_addCommentReply(){
			$(document).on('click', '.btn_reply_add', function(){
				if($(this).prev().val() == ''){
					alert('답글 내용을 입력하세요.');
					return;
				}
				$.ajax({
					type: 'post',
					url: '${contextPath}/freecomment/addReply',
					data: $(this).closest('.frm_reply').serialize(),
					dataType: 'json',
					success: function(resData) {
						if(resData.isAddReply){
							alert('답글이 등록되었습니다.');
							fn_commentList();
							fn_commentCount();
						}
					}
				}); // ajax		
			});
			
		} // fn_addCommentReply()
		
		

		
		

	</script>
	
	
	<!-- . 게시판 관련 버튼(제일 하단 배치 예정) -->
	<div>
		<form id="frm_btn" method="post">
			<input type="hidden" name="freeNo" value="${free.freeNo}">
			<input type="button" value="수정" id="btn_edit_freeboard">
			<input type="button" value="삭제" id="btn_remove_freeboard">
			<input type="button" value="목록" onclick="location.href='${contextPath}/freeboard/list'">
		</form>
		<script>
			$('#btn_edit_freeboard').click(function(){
				$('#frm_btn').attr('action', '${contextPath}/freeboard/edit');
				$('#frm_btn').submit();
			});
			
			$('#btn_remove_freeboard').click(function(){
				if(confirm('게시글을 삭제하면 게시글에 달린 댓글을 더 이상 확인할 수 없습니다. 삭제하시겠습니까?')){
					$('#frm_btn').attr('action', '${contextPath}/freeboard/remove');
					$('#frm_btn').submit();
				} else {
					alert('취소되었습니다.');
				}
			});
		</script>
	</div>
	
	
	


</body>
</html>