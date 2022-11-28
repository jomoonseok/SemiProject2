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
public class GallBoardDTO {
	private int gallNo;
	private String id;
	private String gallTitle;
	private String gallContent;
	private int gallHit;
	private Date gallCreateDate;
	private Date gallModifyDate;
	private String gallIp;
}
