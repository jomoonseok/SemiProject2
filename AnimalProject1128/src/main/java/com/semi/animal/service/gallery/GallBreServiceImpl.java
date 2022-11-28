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
		
		String gallTitle = securityUtil.sha256(request.getParameter("gallTitle"));
		String id = securityUtil.preventXSS(request.getParameter("id"));
		String gallContent = request.getParameter("gallContent");
		
		Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
		String gallIp = opt.orElse(request.getRemoteAddr());
		
		GallBoardDTO gallbroard = GallBoardDTO.builder()
				.gallTitle(gallTitle)
				.id(id)
				.gallContent(gallContent)
				.gallIp(gallIp)
				.build();
		
		int result = gallBrdMapper.insertGallBrd(gallbroard);
		
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
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest mutipartRequest) {
		
		MultipartFile multipartFile = mutipartRequest.getFile("file");
		
		String filesystem = myFileUtil.getFileName(multipartFile.getOriginalFilename());
		
		String path = "C:\\galleryBoard";
		
		File dir = new File(path);
		if(dir.exists() == false) {
			dir.mkdirs();
		}
		
		File file = new File(path, filesystem);
		
		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", mutipartRequest.getContextPath() + "/load/image/" + filesystem);
		return map;

	}
	
	@Override
	public int increaseGallHit(int gallNo) {
		return gallBrdMapper.updateGallHit(gallNo);
	}
	
	
	@Override
	public GallBoardDTO getGallbrdByNo(int gallNo) {
		return gallBrdMapper.selectGallBrdByNo(gallNo);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
