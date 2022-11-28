 $(function(){
      fn_checkAll();
      fn_checkOne();
      fn_toggleCheck();
      fn_submit();
   });
 
 // 모두 동의 (모두 동의의 체크 상태 = 개별 선택들의 체크 상태)
   function fn_checkAll(){
      $('#check_all').click(function(){
      // 체크 상태 변경
      $('.check_one').prop('checked', $(this).prop('checked'));  // $(this): 자기 자신, '.check_one' // 개별선택의 속성의 체크버튼을 체크하라. 언체크 -> 체크  
      // 체크 이미지 변경
      if($(this).is(':checked')){  // $(this): '#check_all' // .is(':checked') : 체크 여부 확인 // 모두 동의가 체크되었다면
         $('.lbl_one').addClass('lbl_checked');
      } else {
         $('.lbl_one').removeClass('lbl_checked');
      }
      
      });
   }
   
   // .prop('checked') : 속성 변경. 체크 -> 언체크, 언체크 -> 체크
   // .is(':checked') : true, false를 반환한다. 체크되었다면?
   
   // 개별 선택 (항상 개별 선택 4개를 모두 순회하면서 어떤 상태인지 체크해야 함)
   function fn_checkOne(){
      $('.check_one').click(function(){  // 개별선택을 체크할 때 항상 개별선택 체크된 갯수를 파악하기
         // 체크 상태 변경
         // 체크 갯수 확인
         let checkCount = 0;
         for(let i = 0; i < $('.check_one').length; i++){
            checkCount += $($('.check_one')[i]).prop('checked');  // 체크된 상태만큼 checkCount에 +=하여라.
         }
         // 체크박스개수 vs 체크된박스개수
         $('#check_all').prop('checked', $('.check_one').length == checkCount);  // 모두선택이 체크되면 개별선택의 길이와 체크갯수를 맞추어라. 개별선택의 길이는 전체 개별선택의 갯수이기 때문에 즉, 모두선택을 누르면 개별선택이 전체 체크된다.
         // 체크 이미지 변경
         if($('#check_all').is(':checked')){
            $('.lbl_all').addClass('lbl_checked');
         } else {
            $('.lbl_all').removeClass('lbl_checked');
         }
      });
   }
   
   // 체크할때마다 lbl_checked 클래스를 줬다 뻈었다
   function fn_toggleCheck(){
      $('.lbl_all, .lbl_one').click(function(){
         $(this).toggleClass('lbl_checked');  // toggleClass : class를 추가하거나 제거한다. 
      });
   }
   
   // 서브밋 (필수 체크 여부 확인)
   function fn_submit(){
	   $('#frm_agree').submit(function(event){
		  if($('#service').is(':checked') == false || $('#privacy').is(':checked') == false) {  // 필수 항목이 언체크되어있다면
			  alert('필수 약관에 동의하세요.');
			  event.preventDefault();
			  return;
		  } 
	   });
   }