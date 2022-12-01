package com.semi.animal.service.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.semi.animal.domain.gallery.GallCommentDTO;

public interface GallCommentService {
	public Map<String, Object> getGallCommentCount(int gallNo);
	public Map<String, Object> addGallComment(GallCommentDTO gallComment);
	public Map<String, Object> getGallCommentList(HttpServletRequest request);
	public Map<String, Object> removeGallComment(int gallCmtNo);
	public Map<String, Object> addGallCommentReply(GallCommentDTO gallCmtreply);
}
