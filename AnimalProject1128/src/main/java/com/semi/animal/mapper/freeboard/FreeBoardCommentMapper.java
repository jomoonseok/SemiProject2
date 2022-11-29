package com.semi.animal.mapper.freeboard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.freeboard.FreeBoardCommentDTO;

@Mapper
public interface FreeBoardCommentMapper {
	
	public int selectCommentCount(int freeNo);
	public int insertComment(FreeBoardCommentDTO freeComment);
	public List<FreeBoardCommentDTO> selectCommentList(Map<String, Object> map);
	public int updatePreviousReply(FreeBoardCommentDTO freeComment);
	public int insertCommentReply(FreeBoardCommentDTO freeComment);
			

	

}
