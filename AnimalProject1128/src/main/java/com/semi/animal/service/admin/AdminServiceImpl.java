package com.semi.animal.service.admin;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.semi.animal.domain.admin.AdminUserDTO;
import com.semi.animal.mapper.admin.AdminMapper;
import com.semi.animal.util.PageUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public void getUserList(HttpServletRequest request, Model model) {
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		int totalRecord = adminMapper.selectUserListCount();
		
		pageUtil.setPageUtil(page, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		List<AdminUserDTO> userList = adminMapper.selectUserListByMap(map);
		
		model.addAttribute("userList", userList);
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/admin/user"));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
	}
	
	
	
	@Override
	public void removeUser(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		
		int result = adminMapper.deleteUser(id);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result>0) {
				out.println("alert('삭제 성공');");
				out.println("location.href='" + request.getContextPath() + "/admin/userList';");
			} else {
				out.println("alert('삭제 실패');");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
