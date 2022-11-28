package com.semi.animal.domain.admin;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO {
	private int userNo;
	private String id;
	private String pw;
	private String name;
	private String gender;
	private String email;
	private String mobile;
	private String birthYear;
	private String birthDay;
	private String postcode;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String extraAddress;
	private String profilePicture;
	private String nickname;
	private int age;
	private int agreeCode;
	private String snsType;
	private Date joinDate;
	private Date pw_modifyDate;
	private Date info_modifyDate;
	private String sessionId;
	private Date sessionLimitDate;
	private int point;
}
