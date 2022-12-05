package com.semi.animal.service.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.semi.animal.domain.user.RetireUserDTO;
import com.semi.animal.domain.user.SleepUserDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.mapper.admin.AdminMapper;
import com.semi.animal.util.PageUtil;
import com.semi.animal.util.SecurityUtil;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;
	@Autowired
	private PageUtil pageUtil;
	@Autowired
	private SecurityUtil securityUtil;
	
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
	
	
	
	@Transactional
	@Override
	public Map<String, Object> removeUser(List<String> id,  List<Date> joinDate) {
		
		//List<String> idList = new ArrayList<String>();
		List<RetireUserDTO> retireUserList = new ArrayList<RetireUserDTO>();
		
		int Count = id.size();
		for(int i = 0; i < Count; i++) {
			//idList.add(id.get(i));
			
			java.sql.Date sqlDate = new java.sql.Date(joinDate.get(i).getTime());
			RetireUserDTO retireUser = RetireUserDTO.builder()
					.id(id.get(i))
					.joinDate(sqlDate) // Util.Date -> SQL.Date
					.build();
			retireUserList.add(retireUser);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", id);
		map.put("retireUserList", retireUserList);
		
		int deleteResult = adminMapper.deleteUser(map);
		int insertresult = adminMapper.insertRetireUser(map);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isRemove", deleteResult == Count && insertresult == Count);
		return result;
	}
	
	
	
	@Transactional
	@Override
	public Map<String, Object> sleepUser(List<String> id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idList", id);
		
		List<UserDTO> users = adminMapper.selectUserListById(map);
		
		List<SleepUserDTO> sleepUsers = new ArrayList<SleepUserDTO>();
		java.sql.Date lastDate = java.sql.Date.valueOf("2000-01-01");
		
		int count = id.size();
		for(int i = 0; i < count; i++) {
			
			SleepUserDTO sleepUser = SleepUserDTO.builder()
					.userNo(users.get(i).getUserNo())
					.id(users.get(i).getId())
					.pw(users.get(i).getPw())
					.name(users.get(i).getName())
					.gender(users.get(i).getGender())
					.email(users.get(i).getEmail())
					.mobile(users.get(i).getMobile())
					.birthYear(users.get(i).getBirthYear())
					.birthDay(users.get(i).getBirthDay())
					.postcode(users.get(i).getPostcode())
					.roadAddress(users.get(i).getRoadAddress())
					.jibunAddress(users.get(i).getJibunAddress())
					.detailAddress(users.get(i).getDetailAddress())
					.extraAddress(users.get(i).getExtraAddress())
					.agreeCode(users.get(i).getAgreeCode())
					.snsType("none")
					.joinDate(users.get(i).getJoinDate())
					.lastLoginDate(lastDate)
					.point(users.get(i).getPoint())
					.build();
			sleepUsers.add(sleepUser);
		}
		
		map.put("sleepUsers", sleepUsers);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isSleep", adminMapper.insertSleepUsers(map) == count && adminMapper.deleteUser(map) == count);
		
			
		return result;
	}
	
	
	
	@Override
	public Map<String, Object> getSearchUsers(HttpServletRequest request) {
		String column = securityUtil.preventXSS(request.getParameter("column"));
		String searchText = securityUtil.preventXSS(request.getParameter("searchText"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", column);
		map.put("searchText", searchText);
		
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		int totalRecord = adminMapper.selectUsersByQueryCount(map);
		
		pageUtil.setPageUtil(page, totalRecord);
		
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("users", adminMapper.selectUsersByQuery(map));
		result.put("pageUtil", pageUtil);
		result.put("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		
		return result;
	}
	
	
	
	@Override
	public void getFreeList(Model model) {
		Map<String, Object> modelMap = model.asMap();
		HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		String id = request.getParameter("id");
		
		
		int totalRecord = adminMapper.selectFreeListCount(id);
		
		pageUtil.setPageUtil(page, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("freeBoardList", adminMapper.selectFreeListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/freeboard/list"));
		model.addAttribute("id", id);
		
	}
	
}
