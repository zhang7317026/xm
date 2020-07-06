package com.zrz.service.fund;

import java.util.List;

import com.zrz.entity.fund.FundListPO;


public interface FundListService {
	
	FundListPO getById(String id);
	
	int save(FundListPO fundListPO);
	
	int insert(FundListPO fundListPO);
	
	int update(FundListPO fundListPO);
	
	int updateSelective(FundListPO fundListPO);
	
	int deleteById(String id);
	
	/**
	 * 获取所有的fund信息
	 */
	List<FundListPO> getAllList();
	
}
