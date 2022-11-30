package com.semi.animal.service.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.semi.animal.domain.gallery.GallCommentDTO;

public interface GallCommentService {
	public Map<String, Object> getCommentCount(int gallNo);
	public Map<String, Object> addComment(GallCommentDTO gallComment);
	public Map<String, Object> gatCommentList(HttpServletRequest request);
	
}
