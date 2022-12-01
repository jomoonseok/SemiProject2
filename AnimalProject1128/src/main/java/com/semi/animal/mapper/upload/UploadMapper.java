package com.semi.animal.mapper.upload;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.domain.upload.UploadDTO;

@Mapper
public interface UploadMapper {

	
	public List<UploadDTO> selectUploadListPage(Map<String, Object> map); 	// upload 게시글 리스트 전체조회
	public List<AttachDTO> selectAttachList(long uploadNo);  // attach 게시글 리스트 전체조회
	
	public UploadDTO selectUploadByNo(long uploadNo);  		// upload 게시글 조회(번호 받아서)
	public AttachDTO selectAttachByNo(long attachNo);		// attach 게시글 조회(번호 받아서)
	
	public long selectAttachCnt(long uploadNo);
	
	// upload 게시글 삽입
	public long insertUpload(UploadDTO upload);
	public long insertAttach(AttachDTO attach);
	
	public int updateUpload(UploadDTO upload);
	
	public int deleteAttach(long attachNo);
	public int deleteUpload(long uploadNo);
	
	public long updateUploadHit(long uploadNo);				// upload 게시글 조회수 증가
	public long updateDownloadCnt(long attachNo);				// attach 다운로드수 증가
	
	public int selectUploadCount();						// upload 게시글 수
	
	public int updateAddPoint(String id);
	public int updateAttachPoint(String id);        // 게시글 등록 포인트 증가
	public int updateSubtractPoint(String id);		// 게시글 삭제 포인트 차감
	
	public int updateAttachAddPoint(Map<String, Object> map);       // 파일첨부 포인트 증가 (1. (id) / 2. (변수) * 2)
	public int updateAttachSubtractPoint(Map<String, Object> map);  // 파일다운 포인트 차감 (1. (id) / 2. -2.5 * -2)
	
	public List<UploadDTO> selectFindBoardsByQuery(Map<String, Object> map);  // 조건별 검색 쿼리
	public int selectFindBoardsCount(Map<String, Object> map);	 // 조건별 검색 쿼리에 이용할 카운트 수
	
	public List<AttachDTO> selectAttachListInYesterday();
	public String selectTrueAttachId(long uploadNo);      // 테이블 3개 조인, 게시글 번호를 받아서 작성자 ID 받아오기 
	public long selectUploadNoInAttach(long attachNo);  // attachNo가 가진 uploadNo를 가져오기
	
	
	
	// upload 게시글 리스트 선택조회(옵션)
//	public List<UploadDTO> selectByOption();
	
}
