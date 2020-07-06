package com.zrz.service.fund;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.zrz.entity.fund.OptTablePO;


public interface OptTableService {
	
	OptTablePO getById(String id);
	
	int save(OptTablePO optTablePO);
	
	int insert(OptTablePO optTablePO);
	
	int update(OptTablePO optTablePO);
	
	int updateSelective(OptTablePO optTablePO);
	
	int deleteById(String id);
	
	/**
	 * 根据user_id,policy_id,fund_code查询最新的一条操作记录，比传入日期早的
	 */
	OptTablePO getLastPO(String user_id, String policy_id, String fund_code, String date0);
	
	/**
	 * 根据user_id,policy_id,fund_code,date0查询PO
	 */
	public OptTablePO getPOByUidAndPIdAndFCodeAndDate0(
			String user_id, String policy_id, String fund_code, String date0);
	
	/**
	 * 根据user_id,policy_id查询PO
	 */
	List<OptTablePO> getListByUserIdAndPolicyId(String user_id, String policy_id);
	
	/**
	 * 根据user_id,policy_id,fund_code查询PO
	 */
	List<OptTablePO> getListByUIdAndPIdAndFCode(String user_id, String policy_id, String fund_code);
	
	/**
	 * 每凌晨1:00,计算昨日利息，移动days
	 */
	@Transactional
	Map<String,Object> computeBefore(String user_id, String policy_id, String date0);
	
	/**
	 * 每晚6:00,计算当日份额
	 */
	@Transactional
	Map<String,Object> computeAfter(String user_id, String policy_id, String date0);
	
	/**
	 * 产生预交易操作
	 * @param OptTablePO optTablePO
	 */
	@Transactional
	Map<String,Object> operate(String user_id, String policy_id, String date0);
	
}
