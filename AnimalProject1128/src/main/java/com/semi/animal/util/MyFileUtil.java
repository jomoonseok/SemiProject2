package com.semi.animal.util;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;

@Component
public class MyFileUtil {
	
	// 파일명 : UUID값을 사용
	// 경로명 : 현재 날짜를 디렉터리로 생성해서 사용
	
	public String getFileName(String filename) {
		
		// 확장자 예외 처리
		String extension = null;
		if(filename.endsWith("tar.gz")) { // 다른 확장자도 찾아보기
			extension = "tar.gz";
		} else {
			// 파라미터로 전달된 filename의 확장자만 살려서 UUID.확장자 방식으로 반환 / unique / 인코딩 x
			String[] arr = filename.split("\\."); // 정규식에서 .(마침표) 인식 : \. 또는 [.]
			
			// 확장자
			extension = arr[arr.length - 1]; // Linux는 압축의 종류에 따라서 확장자 뒤에 더 붙는 경우도 있다.
		}
		
		// UUID.확장자
		return UUID.randomUUID().toString().replaceAll("\\-", "") + "." + extension;
	}
	
	
	
	// 어제 경로
	public String getYesterdayPath() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 1일전 
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String sep = Matcher.quoteReplacement(File.separator);
		return "storage" + sep + year + sep + makeZero(month) + sep + makeZero(day);
	}
	
	// 오늘 경로
	public String getTodayPath() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String sep = Matcher.quoteReplacement(File.separator); // 경로 구분 기호 \(Window), /(Linux)를 자동으로 넣어줌
		return "storage" + sep + year + sep + makeZero(month) + sep + makeZero(day);
	}
	
	
	
	// 1~9 => 01~09
	public String makeZero(int n) {
		return (n < 10) ? "0" + n : "" + n;
	}
	
	

}
