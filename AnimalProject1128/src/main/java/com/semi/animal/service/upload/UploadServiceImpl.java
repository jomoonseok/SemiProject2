package com.semi.animal.service.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.domain.upload.UploadDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.mapper.upload.UploadMapper;
import com.semi.animal.util.MyFileUtil;
import com.semi.animal.util.PageUtil;
import com.semi.animal.util.PageUtil2;
import com.semi.animal.util.SecurityUtil;

@Service
public class UploadServiceImpl implements UploadService {

	@Autowired
	private UploadMapper uploadMapper;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private PageUtil2 pageUtil2;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@Override
	public void getUploadList(HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		int totalRecord = uploadMapper.selectUploadCount();
		
		pageUtil.setPageUtil(page, totalRecord);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		List<UploadDTO> uploadList = uploadMapper.selectUploadListPage(map);
		
		model.addAttribute("uploadList", uploadList);
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/upload"));
		model.addAttribute("loginUser", loginUser);  // ????????? ?????? loginUser??? ????????? ?????? uplaod_list.jsp??? ??????
	}
	
	@Transactional
	@Override
	public void addUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		
		String id = loginUser.getId();   																	
		
		String title = request.getParameter("title"); 
		title = securityUtil.preventXSS(title);
		String content = request.getParameter("content");
		String ip = request.getRemoteAddr(); 
		
		UploadDTO upload = UploadDTO.builder()
				.id(id).uploadTitle(title).uploadContent(content).uploadIp(ip).build();
		long uploadResult = uploadMapper.insertUpload(upload);
		
		List<MultipartFile> files = request.getFiles("files");
		int attachResult;
		if(files.get(1).getSize() != 0) {  
			attachResult = 1;  // ??????????????? ????????? 1
		} else { 
			attachResult = 2;  // ??????????????? ????????? 2 
		}
		
//		int modifyAttachResult;
//		modifyAttachResult = attachResult - 1;  // ????????? ????????? ????????? ??? (files.size() - 1) - ????????????..
//		System.out.println("modifyattachresult" +modifyAttachResult);
		
		for(MultipartFile multipartFile : files) {
			try {
				if(multipartFile != null && multipartFile.isEmpty() == false) {  // ??? ??? ?????????
					String origin = multipartFile.getOriginalFilename();
					origin = origin.substring(origin.lastIndexOf("\\") + 1);  // IE??? origin??? ?????? ????????? ????????? ???????????? ???????????? ???
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
				// ????????? ?????? ??? 10????????? ??????
				uploadMapper.updateAddPoint(id);
				
				// attach ?????? ??? ?????? ??? ????????? 5??? ??????
				if(files.get(1).getSize() == 0) {
					attachResult = 0;
				} else if(attachResult == 2) {
					attachResult = 1;
				} else {
					attachResult -= 1;
				}
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("attachResult", attachResult);
				
				uploadMapper.updateAttachAddPoint(map);
				
				out.println("<script>");
				out.println("alert('????????? ???????????????.');");
				out.println("location.href='" + request.getContextPath() + "/upload'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('????????? ??????????????????.');");
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
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  // request ?????? session ???????????? ??????
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		
		model.addAttribute("upload", uploadMapper.selectUploadByNo(uploadNo));
		model.addAttribute("attachList", uploadMapper.selectAttachList(uploadNo));
		model.addAttribute("attachCnt", uploadMapper.selectAttachCnt(uploadNo));
		model.addAttribute("loginUser", loginUser);
	}
	
	@Override
	public void increaseUploadHit(long uploadNo) {
		uploadMapper.updateUploadHit(uploadNo);
	}
	
	@Override
	public ResponseEntity<Resource> download(String userAgent, long attachNo) {
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  // request ?????? session ???????????? ??????
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		
		long uploadNo = uploadMapper.selectUploadNoInAttach(attachNo);
		String myId = uploadMapper.selectTrueAttachId(uploadNo);
		
		AttachDTO attach = uploadMapper.selectAttachByNo(attachNo);
		File file = new File(attach.getPath(), attach.getFilesystem());
		
		Resource resource = new FileSystemResource(file);
		
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		uploadMapper.updateDownloadCnt(attachNo);
		
		// attach ???????????? ??? 5????????? ??????
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", loginUser.getId());
		map.put("attachResult", 1);
		
		// ?????? ?????? ??????????????? ???????????? ??? ????????? ?????? ??????
		if(!loginUser.getId().equals(myId)) {
			uploadMapper.updateAttachSubtractPoint(map);
		}
		
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
	
	@Override
	public ResponseEntity<Resource> downloadAll(String userAgent, int uploadNo) {
		
		HttpServletRequest req = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();  // request ?????? session ???????????? ??????
		HttpSession session = req.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		
		int attachResult = 0; 
		List<AttachDTO> attachList = uploadMapper.selectAttachList(uploadNo);
		String myId = uploadMapper.selectTrueAttachId(uploadNo);
		
		FileOutputStream fout = null;
		ZipOutputStream zout = null;  
		FileInputStream fin = null;
		
		String tmpPath = "storage" + File.separator + "temp";
		
		File tmpDir = new File(tmpPath);
		if(tmpDir.exists() == false) {
			tmpDir.mkdirs();
		}
		
		String tmpName =  System.currentTimeMillis() + ".zip";
		
		try {
			
			fout = new FileOutputStream(new File(tmpPath, tmpName));
			zout = new ZipOutputStream(fout);
			
			if(attachList != null && attachList.isEmpty() == false) {

				for(AttachDTO attach : attachList) {
					
					ZipEntry zipEntry = new ZipEntry(attach.getOrigin());
					zout.putNextEntry(zipEntry);
					
					fin = new FileInputStream(new File(attach.getPath(), attach.getFilesystem()));
					byte[] buffer = new byte[1024];
					int length;
					while((length = fin.read(buffer)) != -1){
						zout.write(buffer, 0, length);
					}
					zout.closeEntry();
					fin.close();
					
					
					uploadMapper.updateDownloadCnt(attach.getAttachNo());
					attachResult++;
					
				} // for???
				
				// ??? ?????? ??????????????? ????????? ?????? ?????? - ???????????? ID??? DB?????? ???????????? ?????? ID??? ??????
				if(!loginUser.getId().equals(myId)) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", loginUser.getId());
					map.put("attachResult", attachResult);
					uploadMapper.updateAttachSubtractPoint(map);
				} 
				
				zout.close();

			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		// ????????? Resource
		File file = new File(tmpPath, tmpName);
		Resource resource = new FileSystemResource(file);
		
		// Resource??? ????????? ?????? (??????????????? ????????? ??????)
		if(resource.exists() == false) {
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
		// ???????????? ?????? ?????????
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Disposition", "attachment; filename=" + tmpName);  // ??????????????? zip???????????? ?????????????????? ?????? ????????? ????????? ??????
		header.add("Content-Length", file.length() + "");
		
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
	
	
	
	@Transactional
	@Override
	public void modifyUpload(MultipartHttpServletRequest request, HttpServletResponse response) {
		
		long no = Long.parseLong(request.getParameter("uploadNo"));
		String ip = request.getRemoteAddr();
		String title = request.getParameter("title");
		title = securityUtil.preventXSS(title);
		String content = request.getParameter("content");
		
		UploadDTO upload = UploadDTO.builder()
				.uploadNo(no).uploadIp(ip).uploadTitle(title).uploadContent(content).build();
		
		int uploadResult = uploadMapper.updateUpload(upload); 
		
		List<MultipartFile> files = request.getFiles("files");
		int attachResult;
		if(files.get(1).getSize() != 0) {  
			attachResult = 1;  // ???????????? ?????????
		} else {
			attachResult = 2;  // ???????????????
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
				out.println("alert('?????? ???????????????.');");
				out.println("location.href='" + request.getContextPath() + "/upload/detail?uploadNo=" + no + "'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('?????? ??????????????????.');");
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
		
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");  // ????????? ??? ????????? ?????? loginUser ????????????
		String id = loginUser.getId(); 
		System.out.println(id);
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
				uploadMapper.updateSubtractPoint(id);
				out.println("<script>");
				out.println("alert('?????? ???????????????.');");
				out.println("location.href='" + request.getContextPath() + "/upload'");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('?????? ??????????????????.');");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public void findUploadListByQuery(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		String column = request.getParameter("column");
		String query = request.getParameter("query");
		
		Map<String, Object> pageUtilMap = new HashMap<String, Object>();
		pageUtilMap.put("column", column);
		pageUtilMap.put("query", query);
		
		pageUtil2.setPageUtil(page, uploadMapper.selectFindBoardsCount(pageUtilMap));
		
		Map<String, Object> findBoardsMap = new HashMap<String, Object>();
		findBoardsMap.put("begin", pageUtil2.getBegin());
		findBoardsMap.put("end", pageUtil2.getEnd());
		findBoardsMap.put("column", column);
		findBoardsMap.put("query", query);
//		System.out.println("findBoardsMap" + findBoardsMap);
		
		List<UploadDTO> uploadList = uploadMapper.selectFindBoardsByQuery(findBoardsMap);
		
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("uploadList", uploadList);
//		result.put("paging", pageUtil.getPaging(request.getContextPath() + "/upload/upload_listQuery"));
		
//		model.addAttribute("result", result);
		model.addAttribute("uploadList", uploadList);
		model.addAttribute("paging", pageUtil2.getPaging(request.getContextPath() + "/upload/find?column=" + column + "&query=" + query));
		
		
		
	}
	
//	@Override
//	public List<UploadDTO> getUploadListByOption(HttpServletRequest request) {
//		
//		return null;
//	}
	
	
//	@Override
//	public List<UploadDTO> getUploadListByOption() {
//		
//		return null;
//	}
	
	
	
}
