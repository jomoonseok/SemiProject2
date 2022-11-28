package com.semi.animal.controller.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.service.admin.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin/main")
	public String main() {
		return "admin/main";
	}
	
	@GetMapping("/admin/userMain")
	public String userMain() {
		return "admin/user/userMain";
	}
	
	@ResponseBody
	@GetMapping(value="/admin/userList", produces="application/json")
	public List<UserDTO> userList(HttpServletRequest request, Model model){
		return adminService.getUserList(request, model);
	}
	
	
	@GetMapping("/admin/removeUser")
	public void removeUser(HttpServletRequest request, HttpServletResponse response) {
		adminService.removeUser(request, response);
	}
	
}
