package com.semi.animal.service.freeboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.semi.animal.domain.freeboard.FreeBoardDTO;

public interface FreeBoardService {
	
	public void getFreeList(Model model);
	public void addFreeBoard(HttpServletRequest request, HttpServletResponse response);
	public FreeBoardDTO getBlogByNo(int freeNo);
	public void removeFreeBoard(HttpServletRequest request, HttpServletResponse response);
	
}
