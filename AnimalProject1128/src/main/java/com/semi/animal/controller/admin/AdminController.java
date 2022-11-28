package com.semi.animal.controller.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.semi.animal.service.admin.AdminService;

@Controller
public class AdminController {
	
	@Autowired
	private AdminService adminService;

//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@GetMapping("admin/main")
	public String list() {
		return "admin/main";
	}
	
	@GetMapping("/admin/userList")
	public String userList(HttpServletRequest request, Model model){
		adminService.getUserList(request, model);
		return "admin/userList";
	}
	
	@GetMapping("/admin/removeUser")
	public void removeUser(HttpServletRequest request, HttpServletResponse response) {
		adminService.removeUser(request, response);
	}
	
}
