package com.semi.animal.mapper.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;


import com.semi.animal.domain.gallery.GallBoardDTO;
import com.semi.animal.domain.gallery.SummernoteImageDTO;

@Mapper
public interface GallBrdMapper {
	public int selectGallBrdListCount();
	public List<GallBoardDTO> selectGallBrdListByMap(Map<String, Object> map);
	public int insertSummernoteImage(SummernoteImageDTO summernote);
	public int insertGallBrd(GallBoardDTO gallBroard);
	public int updateGallHit(int gallNo);
	public GallBoardDTO selectGallBrdByNo(int gallNo);
	public int updateGallBrd(GallBoardDTO gallBoard);
	public int deleteGallBrd(int gallNo);
	public List<SummernoteImageDTO> selectSummernoteImageListInGallBrd(int gallNo);
	public List<SummernoteImageDTO> selectAllSummernoteImageList();
	public int deleteSummernoteImage(String filesystem);
}
