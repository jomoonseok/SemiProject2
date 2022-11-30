package com.semi.animal.service.freeboard;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semi.animal.domain.freeboard.FreeBoardCommentDTO;
import com.semi.animal.domain.freeboard.FreeBoardDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.mapper.freeboard.FreeBoardCommentMapper;
import com.semi.animal.util.PageUtil;

@Service
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService {
	
	@Autowired
	private FreeBoardCommentMapper freeBoardCmdMapper;
	
	@Autowired
	private PageUtil pageUtil;
	 
	@Override
	public Map<String, Object> getCommentCount(int freeNo) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentCount", freeBoardCmdMapper.selectCommentCount(freeNo));
		System.out.println("serviceImpl(getCommentCount) : " + result);
		System.out.println("serviceImpl(freeNo) : " + freeNo);
		return result;
	}
	
	@Override
	public Map<String, Object> addComment(FreeBoardCommentDTO freeComment, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUser");
		String freeCmtIp = request.getRemoteAddr();
		
		System.out.println();
		System.out.println("유저아이디 내놔!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + userDTO);
		System.out.println();
		
		freeComment.setId(userDTO.getId());
		freeComment.setFreeCmtIp(freeCmtIp);
				
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdd", freeBoardCmdMapper.insertComment(freeComment));

		return result;
	}
	
	@Override
	public Map<String, Object> getCommentList(HttpServletRequest request) {
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		int page = Integer.parseInt(request.getParameter("page"));
		
		int commentCount = freeBoardCmdMapper.selectCommentCount(freeNo);
		pageUtil.setPageUtil(page, commentCount);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("freeNo", freeNo);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("commentList", freeBoardCmdMapper.selectCommentList(map));
		result.put("pageUtil", pageUtil);
				
		System.out.println("serviceImpl(getCommentList) : " + result);
		return result;
	}
	
	
	@Override
	public Map<String, Object> addReply(FreeBoardCommentDTO freeComment) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAddReply", freeBoardCmdMapper.insertCommentReply(freeComment) == 1);
		System.out.println("serviceImpl(addReply) : " + result);
		
		return result;
	}

	
	
}
