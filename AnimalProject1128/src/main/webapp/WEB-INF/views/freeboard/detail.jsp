<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<jsp:include page="../layout/header.jsp">
	<jsp:param value="[자유게시판] ${free.freeNo}번 게시글" name="title" />
</jsp:include>

<style>

	@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css'); <%-- 뎝 --%>
	
	
	.blind {
		display: none;
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
	
	<%-- 2. 답글 버튼--%>
	
	
	.button_reply {
		font-size: 11px;
		line-height: 20px;
		color: rgb(0, 159, 71);
		background: rgb(255, 255, 255);
		border: 1px solid rgb(0, 159, 71);
		border-radius: 5px;
		
		width: 40px;
		box-shadow: 1px 1px 1px rgb(141, 149, 163);
		transition-duration: 0.3s;
	}
	
	.button_reply:hover {
		font-size: 11px;
		line-height: 20px;
		color: rgb(0, 159, 71);
		background: rgb(224, 248, 235);
		border: 1px solid rgb(0, 159, 71);
		border-radius: 5px;
	}
	
	.button_reply:active {
		font-size: 11px;
		line-height: 20px;
		color: rgb(0, 159, 71);
		background: rgb(255, 255, 255);
		border: 1px solid rgb(0, 159, 71);
		border-radius: 5px;
		box-shadow: none;
	}
	
	
	
	
	<%-- 2. 인풋박스 --%>
	input[type=text] {
		border: 0;
		border-radius: 5px;
		width: 300px;
		height: 27px;
		padding-left: 10px;
		background-color: rgb(239, 240, 242);
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
	
	<%-- 4. 댓글 창 --%>
	.cmt_Content_Area {
		background: rgb(239, 240, 242);
		border: none;
		border-radius: 5px;
		padding: 0px 20px;

	}
	
	.free_board_a_link:hover {
		text-decoration: underline;
		color: rgb(115, 104, 93);
	}
	
	.free_board_content {
		font-size: 10px;
	}

</style>



	
	<!-- 1. 게시글 상세보기 -->
	<div><a href="${contextPath}/freeboard/list" class="free_board_a_link">자유게시판 > </a></div>
	<div class="free_board_content">
		<div><i class="fa-solid fa-check"></i>&nbsp; ${free.freeNo}번 게시글</div>
		<div><i class="fa-solid fa-check"></i>&nbsp; 조회수 : ${free.freeHit}</div>
		<div><i class="fa-solid fa-check"></i>&nbsp; 작성자 : ${free.id}</div>
		<span><i class="fa-solid fa-check"></i>&nbsp; 작성일 : ${free.freeCreateDate}</span>
		<span><i class="fa-solid fa-check"></i>&nbsp; 수정일 : ${free.freeModifyDate}</span>
		<div><i class="fa-solid fa-check"></i>&nbsp; 아이피 : ${free.freeIp}</div>
		<div><i class="fa-solid fa-check"></i>&nbsp; 제목 : ${free.freeTitle}</div>
	</div>
	<hr>
	<div>내용 ${free.freeContent}</div>
	
	<hr>
	<!-- 2-1. 댓글 쓰기 -->
	댓글
	
	<div>
		<form id="frm_add_comment">
			<div class="add_comment">
				<div class="add_comment_input">
					<input type="text" name="freeCmtContent" id="content" size="50" placeholder="댓글을 작성하려면 로그인을 해야합니다.">
					<span class="add_comment_btn">
						<input type="button" value="등록" id="btn_add_comment" class="button btnFade">
					</span>
				</div>
			</div>
			<input type="hidden" name="freeNo" value="${free.freeNo}">
		</form>
	</div>	
	<!-- 2-2. 로그인유저 댓글 쓰기 -->
	<c:if test="${loginUser == null}">
		<script>
			$('#btn_add_comment').click(function(){
				if(confirm('글을 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동 하시겠습니까?')) {
					location.href = "${contextPath}/user/login/form";
					return;
				}
			});
		</script>
	</c:if>
	<c:if test="${loginUser != null}">
		<script>
			// 1. 댓글 추가
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
		</script>
	</c:if>
	
	
	<hr>
	<!-- 3. 댓글 리스트 -->
	<span id="btn_comment_list">
		<i class="fa-regular fa-comment-dots"></i>&nbsp;댓글
		<span id="comment_count"></span>개
	</span>
	
	<hr>
	
	<div id="comment_area" class="blind">
		<div id="comment_list"></div>
		<div id="paging" class="paging"></div>
	</div>
	
	<input type="hidden" id="page" value="1">
	
	<script>
	
		fn_commentCount();
		fn_switchCommentList();
		fn_commentList();
		fn_changePage();
		fn_removeComment();
		fn_switchReplyArea();
		
		var page = 1;
	
		// 1. 로그인 컨펌
		function fn_loginConfirm(){
			if(confirm('답글을 작성하려면 로그인이 필요합니다. 로그인 페이지로 이동 하시겠습니까?')) {
				location.href = "${contextPath}/user/login/form";
				return;
			}
		}
		
		// 2. 댓글 갯수
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
		
		// 3. 댓글 리스트 토글
		function fn_switchCommentList(){
			$('#btn_comment_list').click(function(){
				$('#comment_area').toggleClass('blind');
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
							div += '<span><i class="fa-solid fa-caret-right"></i><strong>&nbsp;' + comment.id + '</strong></span>';
							div += '&nbsp;';
							div += '<span class="cmt_Content_Area">' + comment.freeCmtContent + '</span>';
							// 작성자만 삭제할 수 있도록 if 처리 필요
							div += '&nbsp;';
							
							if( '${loginUser.id}' == comment.id) {
								div += '<input type="button" value="삭제" class="button_reply btn_comment_remove" data-free_comment_no="' + comment.freeCmtNo + '">';
							// 계층하려면 삭제! comment.depth == 0 && 
							}
							if(${loginUser != null}){
								div += '&nbsp;';
								div += '<input type="button" value="답글" class="button_reply btn_reply_area">';								
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
						///////
						div += '<input type="hidden" name="depth" value="' + comment.depth + '">';
						div += '<input type="hidden" name="groupOrder" value="' + comment.groupOrder + '">';
						///////
						div += '<input type="hidden" name="groupNo" value="' + comment.freeCmtNo + '">';
						div += '<input type="text" name="freeCmtContent" placeholder="답글을 작성하려면 로그인을 해야합니다.">';
						div += '<input type="button" value="답글" class="button_reply btn_reply_add">';		
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
							paging += '<strong style="color: rgb(0, 159, 71); border: solid 1px #e5e5e5;">' + p + '</strong>';
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
		
		// 5. 페이지 버튼
		function fn_changePage(){
			$(document).on('click', '.enable_link', function(){
				$('#page').val( $(this).data('page') );
				fn_commentList();
			});
		}
		
		
		// 6. 댓글 삭제
		
		function fn_removeComment(){
			$(document).on('click', '.btn_comment_remove', function(){
				if(confirm('삭제된 댓글은 복구할 수 없습니다. 댓글을 삭제할까요?')){
					$.ajax({
						type: 'post',
						url: '${contextPath}/freecomment/remove',
						data: 'freeCmtNo=' + $(this).data('free_comment_no'),
						dataType: 'json',
						success: function(resData){
							if(resData.isRemove){
								alert('댓글이 삭제되었습니다.');
								fn_commentList();
								fn_commentCount();
							}
							
						}
					});
				}
			});
		}
		
		
		// 7. 대댓글 리스트 토글
		function fn_switchReplyArea(){
			$(document).on('click', '.btn_reply_area', function(){
				$(this).parent().next().next().toggleClass('blind');
			});
		}
		
	</script>
	
	<!-- 4-1. 로그인유저 대댓글 쓰기 -->
	<c:if test="${loginUser == null}">
		<script>
			$(document).on('click', '.btn_reply_add', function(){
				fn_loginConfirm();
			});
		</script>
	</c:if>
	<!-- 4-2. 로그인유저 대댓글 쓰기 -->
	<c:if test="${loginUser != null}">
		<script>
			fn_addCommentReply();
		
			function fn_addCommentReply(){
				$(document).on('click', '.btn_reply_add', function(){
					if($(this).prev().val() == ''){
						alert('답글 내용을 입력하세요.');
						return;
					}
					$.ajax({
						type: 'post',
						url: '${contextPath}/freecomment/reply/add',
						data: $(this).closest('.frm_reply').serialize(),
						dataType: 'json',
						success: function(resData) {
							if(resData.isAddReply) {
								alert('답글이 등록되었습니다.');
								fn_commentCount();
								fn_commentList();
							}
						}
					}); // ajax		
				});
				
			} // fn_addCommentReply()

		</script>
	</c:if>
	
	
	
	
	
	
	
	
	
	
	<!-- 5. 게시판 관련 버튼(제일 하단 배치 예정) -->
	<div>
		<form id="frm_btn" method="post">
			<input type="hidden" name="freeNo" value="${free.freeNo}">
			<input type="button" value="수정" id="btn_edit_freeboard" class="button">
			<input type="button" value="삭제" id="btn_remove_freeboard" class="button">
			<input type="button" value="목록" class="button" onclick="location.href='${contextPath}/freeboard/list'">
		</form>
		
		<!-- 5-1. 작성자가 아니면 수정/삭제 버튼 숨기기 -->
		<c:if test="${loginUser.id != free.id}">
			<script>
				$("#btn_remove_freeboard").hide();
				$("#btn_edit_freeboard").hide();
			</script>
		</c:if>
		
		<!-- 5-2. 작성자라면 수정/삭제 버튼 활성화 및 서브밋 -->
		<c:if test="${loginUser.id == free.id}">
			<script>
				// 1. 게시글 수정
				$('#btn_edit_freeboard').click(function(){
					$('#frm_btn').attr('action', '${contextPath}/freeboard/edit');
					$('#frm_btn').submit();
				});
				// 2. 게시글 삭제
				$('#btn_remove_freeboard').click(function(){
					if(confirm('게시글을 삭제하면 게시글에 달린 댓글을 더 이상 확인할 수 없습니다. 삭제하시겠습니까?')){
						$('#frm_btn').attr('action', '${contextPath}/freeboard/remove');
						$('#frm_btn').submit();
					} else {
						alert('취소되었습니다.');
					}
				});
			</script>
		</c:if>
	</div>
	
	
	


</body>
</html>