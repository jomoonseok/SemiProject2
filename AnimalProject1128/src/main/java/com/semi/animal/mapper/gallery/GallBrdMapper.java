package com.semi.animal.mapper.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.gallery.GallBoardDTO;

@Mapper
public interface GallBrdMapper {
	public int selectGallBrdListCount();
	public List<GallBoardDTO> selectGallBrdListByMap(Map<String, Object> map);
	public int insertGallBrd(GallBoardDTO gallbroard);
	public int updateGallHit(int gallNo);
	public GallBoardDTO selectGallBrdByNo(int gallNo);
}
