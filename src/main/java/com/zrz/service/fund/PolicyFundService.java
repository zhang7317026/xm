package com.zrz.service.fund;

import java.util.List;
import java.util.Map;

import com.zrz.entity.fund.OptTablePO;
import com.zrz.entity.fund.PolicyFundPO;


public interface PolicyFundService {
	
	PolicyFundPO getById(String id);
	
	int save(PolicyFundPO policyFundPO);
	
	int insert(PolicyFundPO policyFundPO);
	
	int update(PolicyFundPO policyFundPO);
	
	int updateSelective(PolicyFundPO policyFundPO);
	
	int deleteById(String id);
	
	
	/**
	 * 根据策略id获取自己持有的基金详细信息
	 */
	List<PolicyFundPO> getListByPolicyId(String policy_id);
	
	/**
	 * 根据fund_code获取自己持有的基金PO列表
	 */
	List<PolicyFundPO> getListByFundCode(String fund_code);
	
	/**
	 * 根据policy_id及fund_code查询PO
	 */
	PolicyFundPO getPOByPolicyIdAndFundCode(String policy_id, String fund_code);
	
	/**
	 * 每日交易结算
	 */
	boolean deal(PolicyFundPO policyFundPO, OptTablePO optTablePO);
	
}
