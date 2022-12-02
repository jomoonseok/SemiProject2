
package com.semi.animal.service.gallery;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.gallery.GallBoardDTO;
import com.semi.animal.domain.gallery.SummernoteImageDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.mapper.gallery.GallBrdMapper;
import com.semi.animal.util.MyFileUtil;
import com.semi.animal.util.PageUtil;

@Service
public class GallBrdServiceImpl implements GallBrdService {

	@Autowired
	private GallBrdMapper gallBrdMapper;
	
	@Autowired
	private PageUtil pageUtil;
	
	@Autowired
	private MyFileUtil myFileUtil;

	@Override
	public void getGallBoardList(HttpServletRequest request, Model model) {
		
		Optional<String> opt = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt.orElse("1"));

		int totalRecord = gallBrdMapper.selectGallBrdListCount();

		pageUtil.setPageUtil(page, totalRecord);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());

		model.addAttribute("totalRecord", totalRecord);
		model.addAttribute("gallList", gallBrdMapper.selectGallBrdListByMap(map));
		model.addAttribute("beginNo", totalRecord - (page - 1) * pageUtil.getRecordPerPage());
		model.addAttribute("paging", pageUtil.getPaging(request.getContextPath() + "/gall/list"));
	}

	@Override
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest multipartRequest) {

		MultipartFile multipartFile = multipartRequest.getFile("file");

		String path = "C:" + File.separator + "galleryImage";

		String filesystem = myFileUtil.getFileName(multipartFile.getOriginalFilename());

		File dir = new File(path);
		if(dir.exists() == false) {
			dir.mkdirs();
		}

		File file = new File(path, filesystem);  // new File(dir, filesystem)도 가능

		try {
			multipartFile.transferTo(file);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("src", multipartRequest.getContextPath() + "/load/image/" + filesystem);
		map.put("filesystem", filesystem);  // HDD에 저장된 파일명 반환
		return map;

		
	}
	
	@Transactional
	@Override
	public void saveGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		String gallTitle = request.getParameter("gallTitle");
		String gallContent = request.getParameter("gallContent");
		
		Optional<String> opt = Optional.ofNullable(request.getHeader("X-Forwarded-For"));
		String gallIp = opt.orElse(request.getRemoteAddr());
		
		
		GallBoardDTO gallBoard = GallBoardDTO.builder()
				.gallTitle(gallTitle)
				.id(loginUser.getId())
				.gallContent(gallContent)
				.gallIp(gallIp)
				.build();
		
		int result = gallBrdMapper.insertGallBrd(gallBoard);
		
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
//			out.println("<script>");
			if(result > 0) {

				String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");

				if(summernoteImageNames !=  null) {
					for(String filesystem : summernoteImageNames) {
						SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
								.gallNo(gallBoard.getGallNo())
								.filesystem(filesystem)
								.build();
						gallBrdMapper.insertSummernoteImage(summernoteImage);
					}
				}
				out.println("<script>");
				out.println("alert('게시글이 등록되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/gall/list';");
				out.println("</script>");

			} else {
				out.println("<script>");
				out.println("alert('게시글 등록 실패')");
				out.println("history.back();");
				out.println("</script>");
			}
//			out.println("</script>");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int increaseGallHit(int gallNo) {
		return gallBrdMapper.updateGallHit(gallNo);
	}
	
	
	@Override
	public GallBoardDTO getGallbrdByNo(int gallNo) {
	
		GallBoardDTO gallBoard = gallBrdMapper.selectGallBrdByNo(gallNo);
		
		List<SummernoteImageDTO> summernoteImageList = gallBrdMapper.selectSummernoteImageListInGallBrd(gallNo);
		
		if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
			for(SummernoteImageDTO summernoteImage : summernoteImageList) {
				if(gallBoard.getGallContent().contains(summernoteImage.getFilesystem()) == false) {
					File file = new File("C:" + File.separator + "galleryImage", summernoteImage.getFilesystem());
					if(file.exists()) {
						file.delete();
					}
					gallBrdMapper.deleteSummernoteImage(summernoteImage.getFilesystem());
				}
			}
		}
		
		return gallBoard;
				
	}
	
	@Transactional
	@Override
	public void modifyGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserDTO loginUser = (UserDTO)session.getAttribute("loginUser");
		
		String gallTitle = request.getParameter("gallTitle");
		String gallContent = request.getParameter("gallContent");
		String id = request.getParameter(loginUser.getId());
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		
		
		GallBoardDTO gallBoard = GallBoardDTO.builder()
				.gallTitle(gallTitle)
				.gallContent(gallContent)
				.id(id)
				.gallNo(gallNo)
				.build();
		
		System.out.println(gallBoard);
		
		int result = gallBrdMapper.updateGallBrd(gallBoard);
		System.out.println(result);
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			/* out.println("<script>"); */
			if(result > 0) {
				
				String[] summernoteImageNames = request.getParameterValues("summernoteImageNames");
				
				if(summernoteImageNames !=  null) {
					for(String filesystem : summernoteImageNames) {
						SummernoteImageDTO summernoteImage = SummernoteImageDTO.builder()
								.gallNo(gallBoard.getGallNo())
								.filesystem(filesystem)
								.build();
						gallBrdMapper.insertSummernoteImage(summernoteImage);
					}
				}
				
				out.println("<script>");
				out.println("alert('게시글이 수정되었습니다.')");
				out.println("location.href='" + request.getContextPath() + "/gall/detail?gallNo=" + gallNo + "';");
				out.println("</script>");
			} else {
				out.println("<script>");
				out.println("alert('수정실패')");
				out.println("history.back();");
				out.println("</script>");
			}
			/* out.println("</script>"); */
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void removeGallBrd(HttpServletRequest request, HttpServletResponse response) {
		
		int gallNo = Integer.parseInt(request.getParameter("gallNo"));
		
		List<SummernoteImageDTO> summernoteImageList = gallBrdMapper.selectSummernoteImageListInGallBrd(gallNo);
		
		int result = gallBrdMapper.deleteGallBrd(gallNo);
		
		// 응답
		try {
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			if(result > 0) {	
				
				if(summernoteImageList != null && summernoteImageList.isEmpty() == false) {
					for(SummernoteImageDTO summernoteImage : summernoteImageList) {
						File file = new File("C:" + File.separator + "galleryImage", summernoteImage.getFilesystem());
						if(file.exists()) {
							file.delete();
						}
					}
				}
				
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
