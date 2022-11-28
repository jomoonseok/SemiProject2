package com.semi.animal.domain.upload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AttachDTO {
	
	private long attachNo;  // pk
	private long uploadNo;  // fk
	private String path;
	private String origin;
	private String filesystem;
	private String downloadCnt;
	
	
}
