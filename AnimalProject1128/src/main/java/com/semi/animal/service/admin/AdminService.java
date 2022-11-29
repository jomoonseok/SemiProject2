package com.semi.animal.service.admin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {
	public Map<String, Object> getUserList(HttpServletRequest request);
	public Map<String, Object> removeUser(int userNo, String id, String joinDate);
}
