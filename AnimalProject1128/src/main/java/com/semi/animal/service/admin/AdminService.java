package com.semi.animal.service.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface AdminService {
	public Map<String, Object> getUserList(HttpServletRequest request);
	public Map<String, Object> removeUser(List<String> id,  List<Date> joinDate);
	public Map<String, Object> sleepUser(List<String> id);
	public Map<String, Object> getSearchUsers(HttpServletRequest request);
	
	public void getFreeList(Model model);
}
