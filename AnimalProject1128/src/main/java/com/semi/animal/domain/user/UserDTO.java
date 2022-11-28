package com.semi.animal.domain.user;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  // getter/setter 생성
@NoArgsConstructor  // 파라미터로 생성된 필드의 모든 값을 넘기지 않아도 됨. // @AllArgsConstructor만 사용하면 모든 생성된 필드의 값을 넘겨야 함.
@AllArgsConstructor  // 생성자(Constructor) 생성
@Builder



public class UserDTO {
	private int userNo;  // mybatis-config파일에서 CamelCase를 설정했기 때문에 user_no가 아닌 userNo로 작성
	private String id;
	private String pw;
	private String name;
	private String gender;  // M, F, NO
	private String email;  // 이메일 인증 때문에 UNIQUE
	private String mobile;
	private String birthYear;  // 출생년도(YYYY)
	private String birthDay;   // 출생월일(MMDD)
	private String postcode;   // 우편번호
	private String roadAddress;  // 도로명주소
	private String jibunAddress;  // 지번주소
	private String detailAddress; // 상세주소
	private String extraAddress;  // 참고항목
	private int agreeCode; // 약관 동의여부(0:필수, 1:필수+위치, 2:필수+프로모션, 3:필수+위치+프로모션)
	private String snsType;  // 간편가입종류(사이트가입:null, 네아로:naver)
	private Date joinDate;  
	private Date pwModifyDate;  // 비밀번호 수정일
	private Date infoModifyDate;  // 회원정보 수정일
	// 세션 : 인터넷 창을 추가해도 저장되어 있음. but 인터넷 창을 다 껐을 경우에는 저장x. 
	// 쿠키 : 기한을 지정해서 쿠키에 저장하면 그 기간동안 인터넷 창을 다 껐어도 저장되어 있음.
	private String sessionId;  // 세션아이디  
	private Date sessionLimitDate;  // 세션만료기한
	private int point;
	
	// DB 칼럼순서대로 필드 선언 할 필요는 없지만, 검토할 때 편하기 때문에 순서대로 작성하는게 좋음.
	// VARCHAR2 : String, NUMBER : int, DATE : date
	
	// 젠더는 문자가 아닌 체크하는건데 왜 varchar2타입? 약관동의도 체크인데 스트링이 아니다?
	// 성별은 join.jsp에서 <input type="radio" name="gender" id="male" value="M"> 즉, value="M" 스트링 타입으로 파라미터를 넘겨주기로 설정할 것임.
	// 약관동의 Integer로 선언. 약관동의는 agreeCode = 1; ServiceImpl에서 숫자로 선언할 것이기 때문
	
	//private Integer agreeCode; // null값이 필요하기 때문에 Integer로 저장. but 코드 짜기 복잡해서 int로 변경해서 작성한다.
	
}
