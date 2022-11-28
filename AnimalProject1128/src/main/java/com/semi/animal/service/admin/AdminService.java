package com.semi.animal.service.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

public interface AdminService {
	public void getUserList(HttpServletRequest request, Model model);
	public void removeUser(HttpServletRequest request, HttpServletResponse response);
}
