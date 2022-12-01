package com.semi.animal.controller.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	public Map<String, Object> userList(HttpServletRequest request){
		return adminService.getUserList(request);
	}
	
	@ResponseBody
	@PostMapping(value="/admin/removeUser", produces="application/json")
	public Map<String, Object> removeUser(@RequestParam("idValueArr[]") List<String> id, @RequestParam("joinDateValueArr[]")@DateTimeFormat(pattern="yy/MM/dd") List<Date> joinDate) {
		return adminService.removeUser(id, joinDate);
	}
	
	@ResponseBody
	@PostMapping(value="/admin/sleepUser", produces="application/json")
	public Map<String, Object> sleepUser(@RequestParam("idValueArr[]") List<String> id) {
		return adminService.sleepUser(id);
	}
	
}
