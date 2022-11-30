package com.semi.animal.service.freeboard;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.semi.animal.domain.freeboard.FreeBoardCommentDTO;

public interface FreeBoardCommentService {
	
	public Map<String, Object> getCommentCount(int freeNo);
	public Map<String, Object> addComment(FreeBoardCommentDTO freeComment, HttpServletRequest request);
	public Map<String, Object> getCommentList(HttpServletRequest request);
	public Map<String, Object> addReply(FreeBoardCommentDTO freeComment);
	
	
	
}
