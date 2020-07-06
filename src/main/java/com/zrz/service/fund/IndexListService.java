package com.zrz.service.fund;

import java.util.List;

import com.zrz.entity.fund.IndexListPO;


public interface IndexListService {
	
	IndexListPO getById(String id);
	
	int save(IndexListPO indexListPO);
	
	int insert(IndexListPO indexListPO);
	
	int update(IndexListPO indexListPO);
	
	int updateSelective(IndexListPO indexListPO);
	
	int deleteById(String id);
	
	/**
	 * 获取所有的index信息
	 */
	List<IndexListPO> getAllList();
	
}
