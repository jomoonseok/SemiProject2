package com.semi.animal.controller.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.service.gallery.GallBrdService;

@Controller
public class GallBrdController {
	
	@Autowired
	private GallBrdService gallBrdService;
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}
	
	@GetMapping("/gall/list")
	public String list(HttpServletRequest request, Model model) {
		gallBrdService.getGallBoardList(request, model);
		return "gall/list";
		
	}
	
	@GetMapping("/gall/write")
	public String write() {
		return "gall/write";
	}
	
	@PostMapping("/gall/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		gallBrdService.saveGallBrd(request, response);
	}
	
	@ResponseBody
	@PostMapping(value="/gall/uploadImage", produces="application/json")
	public Map<String, Object> uploadImage(MultipartHttpServletRequest multipartRequest) {
		return gallBrdService.saveSummernoteImage(multipartRequest);
	}
	
	@GetMapping("/gall/increase/hit")
	public String increase(@RequestParam(value="gallNo", required=false, defaultValue="0")int gallNo) {
		int result = gallBrdService.increaseGallHit(gallNo);
		if(result > 0) {
			return "redirect:/gall/detail?gallNo=" + gallNo;
		} else {
			return "redirect:/gall/list";
		}
	}
	
	
	
	
	
	
}