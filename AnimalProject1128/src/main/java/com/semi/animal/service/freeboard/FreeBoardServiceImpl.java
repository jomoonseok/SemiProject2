package com.semi.animal.service.freeboard;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.semi.animal.domain.freeboard.FreeBoardDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.mapper.freeboard.FreeBoardMapper;
import com.semi.animal.util.PageUtil;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {
	
	private FreeBoardMapper freeBoardMapper;
	private PageUtil pageUtil;
	
	@Autowired
	public void set(FreeBoardMapper freeBoardMapper, PageUtil pageUtil) {
		this.freeBoardMapper = freeBoardMapper;
		this.pageUtil = pageUtil;
	}
	
	@Override
	public void getFreeList(Model model) {
		
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		int totalRecord = freeBoardMapper.selectFreeListCount();
		
		pageUtil.setPageUtil(page, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("freeBoardList", freeBoardMapper.selectFreeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/freeboard/list"));

	}
	
	@Override
	public int increseFreeBoardHit(int freeNo) {
		return freeBoardMapper.updateHit(freeNo);
	}
	
	@Override
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)session.getAttribute("loginUser"); 
		
		String id = userDTO.getId();
		String freeTitle = request.getParameter("title");
		String freeContent = request.getParameter("content");
		String freeIp = request.getRemoteAddr();
		
		FreeBoardDTO freeBoard = FreeBoardDTO.builder()
				.id(id)
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeIp(freeIp)
				.build();
		
		
		
		int result = freeBoardMapper.insertFreeBoard(freeBoard);
			
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("if(confirm('게시글 등록이 완료되었습니다. 확인하러 가시겠습니까?')) { location.href='" + request.getContextPath() + "/freeboard/detail?freeNo=" + freeBoard.getFreeNo() + "'}");
				out.println("else { location.href='" + request.getContextPath() + "/freeboard/list'}");
			} else {
				out.println("alert('게시글 등록에 실패하였습니다.');");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		
	}
	
	
	@Override
	public FreeBoardDTO getFreeBoardByNo(int freeNo) {
		return freeBoardMapper.selectFreeBoardByNo(freeNo);
	}
	
	
	
	@Override
	public void modifyFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		String freeTitle = request.getParameter("freeTitle");
		String freeContent = request.getParameter("freeContent");
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		
		FreeBoardDTO freeBoard = FreeBoardDTO.builder()
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeNo(freeNo)
				.build();
		
		int result = freeBoardMapper.updateFreeBoard(freeBoard);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("alert('수정에 성공하였습니다.');");
				out.println("location.href='" + request.getContextPath() + "/freeboard/detail?freeNo=" + freeNo + "';");
			} else {				
				out.println("alert('수정에 실패하였습니다.');");
				out.println("history.back();");
			}
			out.println("</script>");			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	@Override
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response) {
		
		int freeNo = Integer.parseInt(request.getParameter("freeNo"));
		
		int result = freeBoardMapper.deleteFreeBoard(freeNo);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("alert('게시글이 삭제되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/freeboard/list';");
			} else {
				out.println("alert('게시글이 삭제되지 않았습니다.");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
