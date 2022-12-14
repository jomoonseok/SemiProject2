package com.semi.animal.service.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.semi.animal.domain.user.SleepUserDTO;
import com.semi.animal.domain.user.UserDTO;

public interface UserService {
	
	public Map<String, Object> isReduceId(String id);
	public Map<String, Object> isReduceEmail(HttpServletRequest request);
	public Map<String, Object> isReduceIdEmail(String id, String email);
	public Map<String, Object> sendAuthCode(String email);
	public void join(HttpServletRequest request, HttpServletResponse response);
	public void retire(HttpServletRequest request, HttpServletResponse response);
	public void login(HttpServletRequest request, HttpServletResponse response);
	public void keepLogin(HttpServletRequest request, HttpServletResponse response);
	public void logout(HttpServletRequest request, HttpServletResponse response);
	public UserDTO getUserBySessionId(Map<String, Object> map);  // KeepLoginInterceptor에서 호출
	public Map<String, Object> confirmPassword(HttpServletRequest request);
	public void modifyUser(HttpServletRequest request, HttpServletResponse response);
	public void sleepUserHandle();  // SleepUserScheduler에서 호출
	public SleepUserDTO getSleepUserById(String id);
	public void restoreUser(HttpServletRequest request, HttpServletResponse response);

	public void getSessionForwardUser(HttpServletRequest request, Model model);
	public SleepUserDTO findSleep(String email);

	
	public String getNaverLoginApiURL(HttpServletRequest request);  // 네이버로그인-1
	public String getNaverLoginToken(HttpServletRequest request);   // 네이버로그인-2
	public UserDTO getNaverLoginProfile(String access_token);       // 네이버로그인-3
	public UserDTO getNaverUserById(String id);
	public void naverLogin(HttpServletRequest request, UserDTO naverUser);
	public void naverJoin(HttpServletRequest request, HttpServletResponse response);


	
}