package com.semi.animal.service.freeboard;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.semi.animal.domain.freeboard.FreeBoardCommentDTO;
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

		return result;
	}
	
	@Override
	public Map<String, Object> addComment(FreeBoardCommentDTO freeComment, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String freeCmtIp = request.getRemoteAddr();
		
		freeComment.setId(loginUser.getId());
		freeComment.setFreeCmtIp(freeCmtIp);
				
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAdd", freeBoardCmdMapper.insertComment(freeComment) == 1);

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
		return result;
	}
	
	@Override
	public Map<String, Object> removeComment(int freeCmtNo) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isRemove", freeBoardCmdMapper.deleteComment(freeCmtNo) == 1);
		return result;
	}
	
	
	@Override
	public Map<String, Object> addReply(FreeBoardCommentDTO freeCommentReply) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		String freeCmtIp = request.getRemoteAddr();
		
		////////////////////////////////////////
		// 원글의 DEPTH, GROUP_NO, GROUP_ORDER
		int depth = Integer.parseInt(request.getParameter("depth"));
		int groupNo = Integer.parseInt(request.getParameter("groupNo"));
		int groupOrder = Integer.parseInt(request.getParameter("groupOrder"));
		
		// 원글DTO(updatePreviousReply를 위함)
		FreeBoardCommentDTO freeBoardCmd = new FreeBoardCommentDTO();
		freeBoardCmd.setDepth(depth);
		freeBoardCmd.setGroupNo(groupNo);
		freeBoardCmd.setGroupOrder(groupOrder);
		
		// updateRreviousReply 쿼리 실행
		freeBoardCmdMapper.updatePreviousReply(freeCommentReply);
		System.out.println("서비스임플의 freeCommentReply - 유저 추가 전 : " + freeCommentReply);
		System.out.println();
		
		////////////////////////////////////////
		
		freeCommentReply.setId(loginUser.getId());
		freeCommentReply.setFreeCmtIp(freeCmtIp);
		System.out.println("서비스임플의 freeCommentReply - 유저 추가 후 : " + freeCommentReply);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isAddReply", freeBoardCmdMapper.insertCommentReply(freeCommentReply) == 1);
		
		return result;
	}

	
	
}
