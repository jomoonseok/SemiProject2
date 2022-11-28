package com.semi.animal.domain.upload;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UploadDTO {

	private long uploadNo;  // 번호
	private String id;       // 아이디
	private String uploadTitle;  // 제목 
	private String uploadContent;  // 내용
	private long uploadHit;   // 조회수
	private Date uploadCreateDate;   // 게시일
	private Date uploadModifyDate;  // 수정일
	private String uploadIp;  // 아이피
	
}
