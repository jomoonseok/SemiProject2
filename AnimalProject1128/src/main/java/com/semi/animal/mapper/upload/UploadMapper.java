package com.semi.animal.mapper.upload;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.upload.AttachDTO;
import com.semi.animal.domain.upload.UploadDTO;

@Mapper
public interface UploadMapper {

	
	public List<UploadDTO> selectUploadList();  			// upload 게시글 리스트 전체조회
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
	
	
	
	
	
	
	
	
	
	
	
	// upload 게시글 리스트 선택조회(옵션)
//	public List<UploadDTO> selectByOption();
	
}
