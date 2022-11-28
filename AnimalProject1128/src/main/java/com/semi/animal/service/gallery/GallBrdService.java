package com.semi.animal.service.gallery;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.semi.animal.domain.gallery.GallBoardDTO;

public interface GallBrdService {
	public void getGallBoardList(HttpServletRequest request, Model model);
	public void saveGallBrd(HttpServletRequest request, HttpServletResponse response);
	public Map<String, Object> saveSummernoteImage(MultipartHttpServletRequest mutipartRequest);
	public int increaseGallHit(int gallNo);
	public GallBoardDTO getGallbrdByNo(int gallNo);
}
