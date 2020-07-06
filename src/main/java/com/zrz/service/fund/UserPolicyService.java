package com.zrz.service.fund;

import java.util.List;

import com.zrz.entity.fund.UserPolicyPO;


public interface UserPolicyService {
	
	UserPolicyPO getById(String id);
	
	int save(UserPolicyPO userPolicyPO);
	
	int insert(UserPolicyPO userPolicyPO);
	
	int update(UserPolicyPO userPolicyPO);
	
	int updateSelective(UserPolicyPO userPolicyPO);
	
	int deleteById(String id);
	
	
	/**
	 * 根据策略名称模糊查询自己持有的基金详细信息
	 */
	List<UserPolicyPO> getListLikePolicyName(String user_id, String policy_name);
	
	/**
	 * 根据user_id获取自己的策略PO列表
	 */
	List<UserPolicyPO> getListByUserId(String user_id);
	
}
