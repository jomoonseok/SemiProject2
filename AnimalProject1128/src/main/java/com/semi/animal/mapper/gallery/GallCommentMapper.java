package com.semi.animal.mapper.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.gallery.GallCommentDTO;

@Mapper
public interface GallCommentMapper {
	public int selectGallCommentCount(int gallNo);
	public int insertGallComment(GallCommentDTO gallComment);
	public List<GallCommentDTO> selectGallCommentList(Map<String, Object> map);
	public int deleteGallComment(int gallCmtNo);
	public int insertGallCommentReply(GallCommentDTO gallCmtreply);
}
