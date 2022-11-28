package com.semi.animal.domain.freeboard;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FreeBoardCommentDTO {
	private int freeCmtNo;
	private int freeNo;
	private String id;
	private String freeCmtContent;
	private Date freeCmtCreateDate;
	private Date freeCmtModifyDate;
	private String freeCmtIp;
	private int state;
	private int depth;
	private int groupNo;
	private int groupOrder;
	
}
