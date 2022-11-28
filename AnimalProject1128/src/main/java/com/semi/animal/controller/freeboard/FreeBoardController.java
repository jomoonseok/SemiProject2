package com.semi.animal.controller.freeboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.semi.animal.service.freeboard.FreeBoardService;

@Controller
public class FreeBoardController {

	@Autowired
	private FreeBoardService freeBoardService;
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//
//	}
	
	@GetMapping("/freeboard/list")
	public String list(HttpServletRequest request, Model model) {
		model.addAttribute("request", request);
		freeBoardService.getFreeList(model);
		return "freeboard/list";
	}
	
	@GetMapping("/freeboard/write")
	public String write() {
		return "freeboard/write";
	}
	
	@PostMapping("/freeboard/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		freeBoardService.addFreeBoard(request, response);
	}
	
	@GetMapping("/freeboard/detail")
	public String detail(@RequestParam(value="freeNo", required=false, defaultValue="0") int freeNo, Model model) {
		
		model.addAttribute("free", freeBoardService.getBlogByNo(freeNo));
		return "freeboard/detail";
	}
	
	
	@GetMapping("/freeboard/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		freeBoardService.removeFreeBoard(request, response);
	}
	
	
}
