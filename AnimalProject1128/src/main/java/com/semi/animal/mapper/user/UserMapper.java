package com.semi.animal.mapper.user;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.semi.animal.domain.user.RetireUserDTO;
import com.semi.animal.domain.user.SleepUserDTO;
import com.semi.animal.domain.user.UserDTO;

@Mapper
public interface UserMapper {

	public UserDTO selectUserByMap(Map<String, Object> map);
	public RetireUserDTO selectRetireUserById(String id);
	public int insertUser(UserDTO user);
	public int updateAccessLog(String id);
	public int insertAccessLog(String id);
	public int deleteUser(int userNo);
	public int insertRetireUser(RetireUserDTO retireUser);
	public int updateSessionInfo(UserDTO user);
	public int updateUserPassword(UserDTO user);
	public int insertSleepUser();
	public int deleteUserForSleep();
	public SleepUserDTO selectSleepUserById(String id);
	public int insertRestoreUser(String id);
	public int deleteSleepUser(String id);
	
}
	
	// resultType이 생략되면 무조건 int
	// 업뎃 딜릿 인서트면 인트

