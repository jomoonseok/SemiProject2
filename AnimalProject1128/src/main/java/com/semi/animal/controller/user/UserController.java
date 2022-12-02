package com.semi.animal.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.semi.animal.domain.user.SleepUserDTO;
import com.semi.animal.domain.user.UserDTO;
import com.semi.animal.service.user.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	
	@GetMapping("/")         
	public String index() {  
		return "index";     
	}

	
	@GetMapping("/user/login") 
	public String login() {     
		return "user/login";  
	}
	
	@GetMapping("/user/agree")
	public String agree() {
		return "user/agree";
	}
	
	@GetMapping("/user/join/write")
	// required=false 값이 없을 수도 있다.(location, promotion이 선택사항) required=true 값이 필수.
	public String joinWrite(@RequestParam(required=false) String location  // @RequestParam은 1개의 값만 받을 수 있다. requst는 여러개 받을 수 있음.
			              , @RequestParam(required = false) String promotion
			              , Model model) {
		model.addAttribute("location", location);
		model.addAttribute("promotion", promotion);
		return "user/join";
	}
	
	@ResponseBody
	@GetMapping(value="/user/checkReduceId", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> checkReduceId(String id){
		return userService.isReduceId(id);
	}
	
	@ResponseBody
	@GetMapping(value="/user/checkReduceEmail", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> checkReduceEmail(HttpServletRequest request){
		return userService.isReduceEmail(request);
	}
	
	@ResponseBody
	@GetMapping(value="/user/checkSleep", produces=MediaType.APPLICATION_JSON_VALUE)
	public SleepUserDTO checkSleep(String email){
		return userService.findSleep(email);
	}
	
	@ResponseBody
	@GetMapping(value="/user/checkReduceIdEmail", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> checkReduceIdEmail(String id, String email){
		return userService.isReduceIdEmail(id, email);
	}
	
	@ResponseBody
	@GetMapping(value="/user/sendAuthCode", produces=MediaType.APPLICATION_JSON_VALUE)
	public Map<String, Object> sendAuthCode(String email){
		return userService.sendAuthCode(email);
	}
	
	@PostMapping("/user/join")
	public void join(HttpServletRequest request, HttpServletResponse response) {
		userService.join(request, response);
		
	}
	
	@PostMapping("/user/retire")
	public void retire(HttpServletRequest request, HttpServletResponse response) {
		userService.retire(request, response);  // response로 응답받은 데이터를 service에 이동시킨다.
	}
	
	@GetMapping("/user/login/form")
	public String loginForm(HttpServletRequest request, Model model) {  // model로 응답받은 데이터를 jsp파일에 이동시킨다.
		
		// 요청 헤더 referer : 이전 페이지의 주소가 저장
		model.addAttribute("url", request.getHeader("referer"));  // 로그인 후 되돌아 갈 주소 url
		
		// 네이버 로그인
		model.addAttribute("apiURL", userService.getNaverLoginApiURL(request));
		return "user/login";
		
	}
	
	@PostMapping("/user/login")
	public void login(HttpServletRequest request, HttpServletResponse response) {
		userService.login(request, response);
	}
	
	
	@GetMapping("/user/naver/login")
	public String naverLogin(HttpServletRequest request, Model model) {
		String access_token = userService.getNaverLoginToken(request);
		UserDTO profile = userService.getNaverLoginProfile(access_token);
		UserDTO naverUser = userService.getNaverUserById(profile.getId());
		
		if(naverUser == null) {
			model.addAttribute("profile", profile);
			return "user/naver_join";
		} else {
			userService.naverLogin(request, naverUser);
			return "redirect:/";
		}
	}
	


	@PostMapping("/user/naver/join")
	public void naverJoin(HttpServletRequest request, HttpServletResponse response) {
		userService.naverJoin(request, response);
	}

	

	
	@GetMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		userService.logout(request, response);
		return "redirect:/";
	}
	
	@GetMapping("/user/check/form")
	public String requiredLogin_checkForm() {
		return "user/check";
	}
	
	@ResponseBody
	@PostMapping(value="/user/check/pw", produces="application/json")
	public Map<String, Object> requiredLogin_checkPw(HttpServletRequest request) {
		return userService.confirmPassword(request);
	}
	
	@GetMapping("/user/mypage")
	public String requiredLogin_mypage() {
		return "user/mypage";
	}
	
	@PostMapping("/user/modify/pw")
	public void requiredLogin_modifyPw(HttpServletRequest request, HttpServletResponse response) {
		userService.modifyUser(request, response);
	}
	
	
	@GetMapping("/user/sleep/display")
	public String sleepDisplay() {
		return "user/sleep";
		
	}
	
	
	@GetMapping("/user/restore")
	public void restore(HttpServletRequest request, HttpServletResponse response) {
		userService.restoreUser(request, response);
	}
	
	
	@GetMapping("/user/findId")
	public String findId(UserDTO userDTO, Model model){
		return "user/findId";
	}
	
	@GetMapping("/user/findPw")
	public String findPw() {
		return "user/findPw";
	}
	
	
	// get오류가 떠서 비밀번호찾기 페이지의 로그인버튼의 경로를 설정해주었다.
	@PostMapping("/user/login/form/pw")
	public String loginFormByPw(HttpServletRequest request, Model model) { 
			
			// 요청 헤더 referer : 이전 페이지의 주소가 저장
			model.addAttribute("url", request.getHeader("referer"));  
			
			// 네이버 로그인
			model.addAttribute("apiURL", userService.getNaverLoginApiURL(request));
			return "user/login";
			
		}
		

	
	

	

	
	
	
}