package com.semi.animal.service.upload;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.domain.upload.UploadDTO;

public interface UploadService {

	public void getUploadList(HttpServletRequest request, Model model);  // upload 게시글 리스트 전체조회
	
	public void addUpload(MultipartHttpServletRequest request, HttpServletResponse response);  // upload 게시글 삽입
	
	public void getUploadAttachByNo(long uploadNo, Model model);  // upload 게시글 조회
	
	public void increaseUploadHit(long uploadNo);  // upload 조회수 증가
	
	public ResponseEntity<Resource> download(String userAgent, long attachNo);  // attach 다운로드
	
	public ResponseEntity<Resource> downloadAll(String userAgent, int uploadNo);
	
	public void modifyUpload(MultipartHttpServletRequest request, HttpServletResponse response);   // 게시글 수정
	
	public UploadDTO getUploadByNo(long uploadNo);
	
	public List<AttachDTO> removeAttachByAttachNo(HttpServletRequest request);
	
	public void removeUploadByUploadNo(HttpServletRequest request, HttpServletResponse response);
	
	public void findUploadListByQuery(HttpServletRequest request, Model model);
	
	
}
