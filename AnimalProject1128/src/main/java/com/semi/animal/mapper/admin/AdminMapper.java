package com.semi.animal.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.freeboard.FreeBoardDTO;
import com.semi.animal.domain.user.RetireUserDTO;
import com.semi.animal.domain.user.UserDTO;


@Mapper
public interface AdminMapper {
	
	// user
	public int selectUserListCount();
	public List<UserDTO> selectUserListByMap(Map<String, Object> map);
	public int deleteUser(String id);
	public int insertRetireUser(RetireUserDTO retireUser);
	
	// free 
	public int selectFreeListCount();
	public List<FreeBoardDTO> selectFreeListByMap(Map<String, Object> map);
	public int insertFreeBoard(FreeBoardDTO freeBoard);
	public FreeBoardDTO selectFreeBoardByNo(int freeNo);
	public int deleteFreeBoard(int freeNo);
	
}
