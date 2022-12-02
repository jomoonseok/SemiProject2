package com.semi.animal.mapper.freeboard;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.freeboard.FreeBoardDTO;

@Mapper
public interface FreeBoardMapper {

	public int selectFreeListCount();
	public List<FreeBoardDTO> selectFreeListByMap(Map<String, Object> map);
	public int updateHit(int freeNo);
	public int insertFreeBoard(FreeBoardDTO freeBoard);
	public FreeBoardDTO selectFreeBoardByNo(int freeNo);
	public int updateFreeBoard(FreeBoardDTO freeBoard);
	public int deleteFreeBoard(int freeNo);
}
