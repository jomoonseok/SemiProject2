package com.semi.animal.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.freeboard.FreeBoardDTO;
import com.semi.animal.domain.user.UserDTO;


@Mapper
public interface AdminMapper {
	
	// user
	public int selectUserListCount();
	public List<UserDTO> selectUserListByMap(Map<String, Object> map);
	public int deleteUser(Map<String, Object> id);
	public int insertRetireUser(Map<String, Object> retireUser);
	public List<UserDTO> selectUserListById(Map<String, Object> id);
	public int insertSleepUsers(Map<String, Object> sleepUser);
	public int selectUsersByQueryCount(Map<String, Object> map);
	public List<UserDTO> selectUsersByQuery(Map<String, Object> map);
	
	// free 
	public int selectFreeListCount(String id);
	public List<FreeBoardDTO> selectFreeListByMap(Map<String, Object> map);
	
	
}
