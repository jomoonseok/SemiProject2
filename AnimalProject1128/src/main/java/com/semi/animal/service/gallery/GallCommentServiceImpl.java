package com.semi.animal.service.gallery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semi.animal.domain.gallery.GallCommentDTO;
import com.semi.animal.mapper.gallery.GallCommentMapper;
import com.semi.animal.util.PageUtil;

@Service
public class GallCommentServiceImpl implements GallCommentService {

	@Autowired
	private GallCommentMapper gallCommentMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public Map<String, Object> getCommentCount(int gallNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentCount", gallCommentMapper.selectGallCommentCount(gallNo));
		return null;
	}
	
	
	@Override
	public Map<String, Object> addComment(GallCommentDTO gallComment) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdd", gallCommentMapper.insertGallComment(gallComment) == 1);
		return null;
	}
	
	@Override
	public Map<String, Object> gatCommentList(HttpServletRequest request) {
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int commentCount = gallCommentMapper.selectGallCommentCount(gallNo);
		
		pageUtil.setPageUtil(page, commentCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gallNo", gallNo);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentList", gallCommentMapper.selectGallCommentList(map));
		result.put("pageUtil", pageUtil);
		
		return result;
	}
}
