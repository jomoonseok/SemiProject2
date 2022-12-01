package com.semi.animal.controller.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.animal.domain.gallery.GallCommentDTO;
import com.semi.animal.service.gallery.GallCommentService;

@Controller
public class GallCommentController {

	@Autowired
	private GallCommentService gallCommentService;
	
	@ResponseBody
	@GetMapping(value="/gall/comment/getCount", produces="application/json")
	public Map<String, Object> getCount(@RequestParam("gallNo") int gallNo) {
		return gallCommentService.getGallCommentCount(gallNo);
	}
	
	@ResponseBody
	@PostMapping(value="/gall/comment/add", produces="application/json")
	public Map<String, Object> add(GallCommentDTO gallComment){
		return gallCommentService.addGallComment(gallComment);
	}
	

	@ResponseBody
	@GetMapping(value="/gall/comment/list", produces="application/json") 
	public Map<String, Object> list(HttpServletRequest request) { 
		return gallCommentService.getGallCommentList(request); 
	}
	
	@ResponseBody
	@PostMapping(value="/gall/comment/remove", produces="application/json")
	public Map<String, Object> remove(@RequestParam("gallCmtNo") int gallCmtNo){
		return gallCommentService.removeGallComment(gallCmtNo);
	}
	
	@ResponseBody
	@PostMapping(value="/gall/comment/reply/add", produces="application/json")
	public Map<String, Object> replyAdd(GallCommentDTO gallCmtreply) {
		return gallCommentService.addGallCommentReply(gallCmtreply);
	}
}
