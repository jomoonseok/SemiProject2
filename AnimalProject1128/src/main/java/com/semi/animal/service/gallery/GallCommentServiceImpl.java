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
	public Map<String, Object> getGallCommentCount(int gallNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("gallCommentCount", gallCommentMapper.selectGallCommentCount(gallNo));
		return result;
	}
	
	
	@Override
	public Map<String, Object> addGallComment(GallCommentDTO gallComment) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isGallCommentAdd", gallCommentMapper.insertGallComment(gallComment) == 1);
		return result;
	}
	
	@Override
	public Map<String, Object> getGallCommentList(HttpServletRequest request) {
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int gallCommentCount = gallCommentMapper.selectGallCommentCount(gallNo);
		
		pageUtil.setPageUtil(page, gallCommentCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("gallNo", gallNo);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("gallCommentList", gallCommentMapper.selectGallCommentList(map));
		result.put("pageUtil", pageUtil);
		
		return result;
		
		
	}
	
	@Override
	public Map<String, Object> removeGallComment(int gallCmtNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isGallCommentRemove", gallCommentMapper.deleteGallComment(gallCmtNo) == 1); // true
		return result;
	}
	
	@Override
	public Map<String, Object> addGallCommentReply(GallCommentDTO gallCmtreply) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isGallCommentAdd", gallCommentMapper.insertGallCommentReply(gallCmtreply) == 1); // 1과 같으면 true
		return result;
	}
	
}
