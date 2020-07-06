package com.zrz.service;

import com.zrz.entity.SysUserInfoPO;

public interface SysUserInfoService {
	
	SysUserInfoPO getById(String id);
	
	int save(SysUserInfoPO SysUserInfoPO);
	
	int insert(SysUserInfoPO SysUserInfoPO);
	
	int update(SysUserInfoPO SysUserInfoPO);
	
	int updateSelective(SysUserInfoPO SysUserInfoPO);
	
	int deleteById(String id);
	
	SysUserInfoPO getByAccount(String account);
	
	void updateGold(String user_id, String flag, int num);
	
}
