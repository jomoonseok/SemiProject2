package com.semi.animal.service.gallery;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.gallery.GallBoardDTO;
import com.semi.animal.mapper.gallery.GallBrdMapper;
import com.semi.animal.util.MyFileUtil;
import com.semi.animal.util.PageUtil;
import com.semi.animal.util.SecurityUtil;

@Service
public class GallBreServiceImpl implements GallBrdService {

	@Autowired
	private GallBrdMapper gallBrdMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private MyFileUtil myFileUtil;
	
	@Autowired
	private SecurityUtil securityUtil;
	
	@Override
	public void getGallBoardList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));
		
		// 전체 레코드(직원) 개수 구하기
		int totalRecord = gallBrdMapper.selectGallBrdListCount();
		
		// PageUtil 계산하기
		pageUtil.setPageUtil(page, totalRecord);
	
		// Map 만들기(begin, end)
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// 뷰로 보낼 데이터
		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("gallList", gallBrdMapper.selectGallBrdListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/gall/list"));
	}

	@Override
	public void saveGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		// 여기서 오류납니다. sysout 찍어서 확인해보면 돼요. 제 생각에는 밑에 securityUtil 사용이 잘못된듯 합니다.
		
		//String gallTitle = securityUtil.sha256(request.getParameter("gallTitle"));
		String gallTitle = request.getParameter("title");
		//String id = request.getParameter("id");
		//String gallContent = securityUtil.preventXSS(request.getParameter("gallContent"));
		String gallContent = request.getParameter("content");
		
		Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
		String gallIp = opt.orElse(request.getRemoteAddr());
		
		String id = "admin";
		
		GallBoardDTO gallboard = GallBoardDTO.builder()
				.gallTitle(gallTitle)
				.id(id)
				.gallContent(gallContent)
				.gallIp(gallIp)
				.build();
		System.out.println(gallboard);
		int result = gallBrdMapper.insertGallBrd(gallboard);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(result > 0) {
				out.println("<script");
				out.println("alert('게시글이 등록되었습니다. 포인트적립작업필요')");
				out.println("location.href='" + request.getContextPath() + "/gall/list';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('게시글 등록 실패')");
				out.println("history.back();");
				out.println("</script>");
			}
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest) {
		
		// 파라미터 file
		MultipartFile multipartFile = multipartRequest.getFile("file");
		
		// 저장할 파일명
		String filesystem = myFileUtil.getFileName(multipartFile.getOriginalFilename());
		
		// 저장 경로
		String path = "C:\\galleryBoard";
		
		// 저장 경로가 없으면 만들기
		File dir = new File(path);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		// 저장할 File 객체
		File file = new File(path, filesystem);  // new File(dir, filesystem)도 가능
		
		// HDD에 File 객체 저장하기
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 저장된 파일을 확인할 수 있는 매핑을 반환
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", multipartRequest.getContextPath() + "/load/image/" + filesystem);
		return map;
		
		// 저장된 파일이 aaa.jpg라고 가정하면
		// src=${contextPath}/load/image/aaa.jpg 이다. 
		
	}
	
	@Override
	public int increaseGallHit(int gallNo) {
		return gallBrdMapper.updateGallHit(gallNo);
	}
	
	
	@Override
	public GallBoardDTO getGallbrdByNo(int gallNo) {
		return gallBrdMapper.selectGallBrdByNo(gallNo);
	}
	
	
	
	@Override
	public void modifyGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		String gallTitle = request.getParameter("title");
		String gallContent = request.getParameter("content");
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		
		GallBoardDTO gallBoard = GallBoardDTO.builder()
				.gallTitle(gallTitle)
				.gallContent(gallContent)
				.gallNo(gallNo)
				.build();
		
		int result = gallBrdMapper.updateGallBrd(gallBoard);
		
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {
				out.println("alert('게시글이 수정되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/gall/detail?gallNo=" + gallNo + "';");
			} else {
				out.println("alert('수정실패')");
				out.println("history.back();");
			}
			out.println("</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void removeGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		
		int result = gallBrdMapper.deleteGallBrd(gallNo);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {			
				out.println("alert('게시글이 삭제되었습니다.');");
				out.println("location.href='" + request.getContextPath() + "/gall/list';");
			} else {
				out.println("alert('대충실패메세지띄우기');");
				out.println("history.back();");
			}
			out.println("</script>");			
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
}
