package com.semi.animal.controller.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.semi.animal.domain.gallery.GallCommentDTO;
import com.semi.animal.service.gallery.GallCommentService;

@Controller
public class GallCommentController {

	@Autowired
	private GallCommentService gallCommentService;
	
	@ResponseBody
	@GetMapping(value="gall/comment/getCount", produces="application/json")
	public Map<String, Object> getCount(@RequestParam("gallNo") int gallNo) {
		return gallCommentService.getCommentCount(gallNo);
	}
	
	@ResponseBody
	@PostMapping(value="/gall/comment/add", produces="application/json")
	public Map<String, Object> add(GallCommentDTO gallComment){
		return gallCommentService.addComment(gallComment);
	}
	

//	@ResponseBody
//	@GetMapping(value="/gall/comment/list", produces="application/json") public
//	Map<String, Object> list(HttpServletRequest request) { return
//	gallCommentService.getCommentList(request); 
//	}
//	
	
	
	
}
