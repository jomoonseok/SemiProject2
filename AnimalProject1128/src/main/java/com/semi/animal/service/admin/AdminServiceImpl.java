package com.semi.animal.service.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.semi.animal.domain.user.RetireUserDTO;
import com.semi.animal.mapper.admin.AdminMapper;
import com.semi.animal.util.PageUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PageUtil pageUtil;
	
	@Override
	public Map<String, Object> getUserList(HttpServletRequest request) {
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		int totalRecord = adminMapper.selectUserListCount();
		
		pageUtil.setPageUtil(page, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("list", adminMapper.selectUserListByMap(map));
		result.put("pageUtil", pageUtil);
		result.put("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
		return result;
		
	}
	
	
	
	@Override
	public Map<String, Object> removeUser(String id, Date joinDate) {
		java.sql.Date sqlDate = new java.sql.Date(joinDate.getTime());
		System.out.println(sqlDate);
		RetireUserDTO retireUser = RetireUserDTO.builder()
				.id(id)
				.joinDate(sqlDate) // Util.Date -> SQL.Date
				.build();
		
		int deleteResult = adminMapper.deleteUser(id);
		int insertresult = adminMapper.insertRetireUser(retireUser);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isRemove", deleteResult == 1 && insertresult == 1);
		return result;
		
	}
	
}
