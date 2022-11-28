package com.semi.animal.service.upload;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.domain.upload.UploadDTO;
import com.semi.animal.mapper.upload.UploadMapper;
import com.semi.animal.util.MyFileUtil;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadMapper uploadMapper;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Override
	public List<UploadDTO> getUploadList() {
		
		return uploadMapper.selectUploadList();
	}
	
	@Transactional
	@Override
	public void addUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		String id = "admin";   																	// 수정하기
		
		String title = request.getParameter("title"); 
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr(); 
		
		UploadDTO upload = UploadDTO.builder()
				.id(id).uploadTitle(title).uploadContent(content).uploadIp(ip).build();
		long uploadResult = uploadMapper.insertUpload(upload);
		
		List<MultipartFile> files = request.getFiles("files");
		int attachResult;
		if(files.get(0).getSize() == 0) {  
			attachResult = 1;
		} else { 
			attachResult = 0;
		}
		
		for(MultipartFile multipartFile : files) {
			try {
				if(multipartFile != null && multipartFile.isEmpty() == false) {  // 둘 다 필요함
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE는 origin에 전체 경로가 붙어서 파일명만 사용해야 함
					String filesystem = myFileUtil.getFileName(origin);
					String path = myFileUtil.getTodayPath(); 
					
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					multipartFile.transferTo(file); 
					
					AttachDTO attach = AttachDTO.builder()
							.uploadNo(upload.getUploadNo())
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.build();
					attachResult += uploadMapper.insertAttach(attach);
				}
			} catch(Exception e) { 
				e.printStackTrace();
			}
		}
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(uploadResult > 0 && attachResult == files.size()) {
				out.println("<script>");
				out.println("alert('업로드 되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/upload'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('업로드 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>"); 
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void getUploadAttachByNo(long uploadNo, Model model) {
		model.addAttribute("upload", uploadMapper.selectUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.selectAttachList(uploadNo));
		model.addAttribute("attachCnt", uploadMapper.selectAttachCnt(uploadNo));
	}
	
	@Override
	public void increaseUploadHit(long uploadNo) {
		uploadMapper.updateUploadHit(uploadNo);
	}
	
	@Override
	public ResponseEntity<Resource> download(String userAgent, long attachNo) {
		
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		File file = new File(attach.getPath(), attach.getFilesystem());
		
		Resource resource = new FileSystemResource(file);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		uploadMapper.updateDownloadCnt(attachNo);
		String origin = attach.getOrigin();
		try {
			if(userAgent.contains("Trident")) {
				origin = URLEncoder.encode(origin, "UTF-8").replaceAll("\\+", " ");  
			}
			else if(userAgent.contains("Edg")) {
				origin = URLEncoder.encode(origin, "UTF-8");
			}
			else {
				origin = new String(origin.getBytes("UTF-8"), "ISO-8859-1");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + origin);
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	@Transactional
	@Override
	public void modifyUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		long no = Long.parseLong(request.getParameter("uploadNo"));
		String ip = request.getRemoteAddr();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		UploadDTO upload = UploadDTO.builder()
				.uploadNo(no).uploadIp(ip).uploadTitle(title).uploadContent(content).build();
		
		int uploadResult = uploadMapper.updateUpload(upload);   // 게시판 업데이트
		
		// attach 테이블 
		List<MultipartFile> files = request.getFiles("files");
		
		int attachResult;
		if(files.get(0).getSize() == 0) {  
			attachResult = 1;
		} else {
			attachResult = 0;
		}
		
		for(MultipartFile multipartFile : files) {
			
			try {
				
				if(multipartFile != null && multipartFile.isEmpty() == false) {  
					
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1);
					
					String filesystem = myFileUtil.getFileName(origin);
					
					String path = myFileUtil.getTodayPath();
					
					File dir = new File(path);
					if(dir.exists() == false) {
						dir.mkdirs();
					}
					
					File file = new File(dir, filesystem);
					
					multipartFile.transferTo(file);
					
					AttachDTO attach = AttachDTO.builder()
							.path(path)
							.origin(origin)
							.filesystem(filesystem)
							.uploadNo(no)
							.build();
					
					attachResult += uploadMapper.insertAttach(attach);
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(uploadResult > 0 && attachResult == files.size()) {
				out.println("<script>");
				out.println("alert('수정 되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/upload/detail?uploadNo=" + no + "'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('수정 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public UploadDTO getUploadByNo(long uploadNo) {
		return uploadMapper.selectUploadByNo(uploadNo);
	}
	
	@Override
	public List<AttachDTO> removeAttachByAttachNo(HttpServletRequest request) {
		long attachNo = Long.parseLong(request.getParameter("attachNo"));
		long uploadNo = Long.parseLong(request.getParameter("uploadNo"));
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		int result = uploadMapper.deleteAttach(attachNo);
		
		if(result > 0) {
			
			File file = new File(attach.getPath(), attach.getFilesystem());
			
			if(file.exists()) {
				file.delete();
			}
			
		}
		
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		return attachList;
	}
	
	@Override
	public void removeUploadByUploadNo(HttpServletRequest request, HttpServletResponse response) {
		long uploadNo = Long.parseLong(request.getParameter("uploadNo"));
		
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		
		int result = uploadMapper.deleteUpload(uploadNo);
		
		if(result > 0) {
			if(attachList != null && attachList.isEmpty() == false) {
				for(AttachDTO attach : attachList) {
					File file = new File(attach.getPath(), attach.getFilesystem());
					
					if(file.exists()) {
						file.delete();
					}
				}
			}
		}
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				out.println("<script>");
				out.println("alert('삭제 되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/upload'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('삭제 실패했습니다.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	
//	@Override
//	public List<UploadDTO> getUploadListByOption() {
//		
//		return null;
//	}
	
	
	
}
