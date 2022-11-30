package com.semi.animal.domain.gallery;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GallCommentDTO {
	private int gallCmtNo;
	private int gallNo;
	private String id;
	private String gallCmtContent;
	private Date gallCmtCreateDate;
	//private Date gallCmtModifyDate;
	//private String gallCmtIp;
}
