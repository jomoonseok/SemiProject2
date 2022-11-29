package com.semi.animal.domain.gallery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SummernoteImageDTO {
	private int gallNo;
	private String path;
	private String filesystem;
}
