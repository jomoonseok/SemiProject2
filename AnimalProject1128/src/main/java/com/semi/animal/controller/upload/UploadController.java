package com.semi.animal.controller.upload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.service.upload.UploadService;

@Controller
public class UploadController {
	
	@Autowired
	public UploadService uploadService;
	
//	@GetMapping("/")
//	public String index() {
//		return "index";
//	}

	@GetMapping("/upload")
	public String upload(HttpServletRequest request ,Model model) {
		model.addAttribute("uploadList", uploadService.getUploadList());
		return "upload/upload_list";
	}
	
	@GetMapping("/upload/write")
	public String write() {
		return "upload/upload_write";
	}
	
	@PostMapping("/upload/add")
	public void add(MultipartHttpServletRequest request, HttpServletResponse response) {
		uploadService.addUpload(request, response);
	}
	
	@GetMapping("/upload/detail")
	public String detail(@RequestParam(value="uploadNo", required=true) long uploadNo, Model model) {
		uploadService.increaseUploadHit(uploadNo);  // 나중에 시간 많으면 수정하기..
		uploadService.getUploadAttachByNo(uploadNo, model);
		return "upload/upload_detail";
	}
	
	@PostMapping("/upload/edit")
	public String edit(@RequestParam(value="uploadNo", required=true) long uploadNo, Model model) {
		uploadService.getUploadAttachByNo(uploadNo, model);
		return "upload/upload_edit";
	}
	
	@ResponseBody
	@GetMapping("/upload/download")
	public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent, @RequestParam("attachNo") long attachNo) {
		return uploadService.download(userAgent, attachNo);
	}
	
	@PostMapping("/upload/modify")
	public void modify(MultipartHttpServletRequest request, HttpServletResponse response) {
		uploadService.modifyUpload(request, response);
	}
	
	@ResponseBody
	@PostMapping(value="/upload/attach/remove", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AttachDTO> attachRemove(HttpServletRequest request) {
		return uploadService.removeAttachByAttachNo(request);
	}
	
	@PostMapping("/upload/remove")
	public void remove(HttpServletRequest request, HttpServletResponse response) {
		uploadService.removeUploadByUploadNo(request, response);
	}
	
	
//	@PostMapping("/upload/find")
//	public String find(HttpServletRequest request) {
//		
//	}
	
	
}
