package com.semi.animal.domain.freeboard;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FreeBoardDTO {
	private int freeNo;
	private String id;
	private String freeTitle;
	private String freeContent;
	private Date freeCreateDate;
	private Date freeModifyDate;
	private String freeIp;
	private int freeHit;
	
}
