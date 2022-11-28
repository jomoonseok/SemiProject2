package com.semi.animal.service.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.semi.animal.domain.user.UserDTO;

public interface AdminService {
	public List<UserDTO> getUserList(HttpServletRequest request, Model model);
	public void removeUser(HttpServletRequest request, HttpServletResponse response);
}
