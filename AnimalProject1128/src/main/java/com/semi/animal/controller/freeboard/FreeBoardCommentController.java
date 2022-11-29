package com.semi.animal.controller.freeboard;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.animal.domain.freeboard.FreeBoardCommentDTO;
import com.semi.animal.service.freeboard.FreeBoardCommentService;

@Controller
public class FreeBoardCommentController {
	
	@Autowired
	private FreeBoardCommentService freeBoardCmdService;
	
	@ResponseBody
	@GetMapping(value="/freecomment/getCount", produces="application/json")
	public Map<String, Object> getCount(@RequestParam("freeNo") int freeNo){
		System.out.println("controller(getCount) : " + freeNo);
		return freeBoardCmdService.getCommentCount(freeNo);
	}
	
	@ResponseBody
	@PostMapping(value="/freecomment/add", produces="application/json")
	public Map<String, Object> add(FreeBoardCommentDTO freeComment){
		System.out.println("controller(add) : " + freeComment);
		return freeBoardCmdService.addComment(freeComment);
	}
	
	@ResponseBody
	@GetMapping(value="/freecomment/list", produces="application/json")
	public Map<String, Object> list(HttpServletRequest request){
		return freeBoardCmdService.getCommentList(request);
	}

	

	
		
	
}
