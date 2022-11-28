package com.semi.animal.mapper.admin;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.admin.AdminUserDTO;


@Mapper
public interface AdminMapper {
	
	public int selectUserListCount();
	public List<AdminUserDTO> selectUserListByMap(Map<String, Object> map);
	public int deleteUser(String id);
	
}
