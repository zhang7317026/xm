package com.zrz.service;

import com.zrz.entity.SysParamPO;

public interface SysParamService {
	
	SysParamPO getByCode(String code);
	
	int save(SysParamPO sysParamPO);
	
	int deleteByCode(String code);
	
}
