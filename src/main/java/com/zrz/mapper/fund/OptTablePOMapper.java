package com.zrz.mapper.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.fund.OptTablePO;

public interface OptTablePOMapper {
    /**
     *
     * @mbggenerated 2019-03-27
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insert(OptTablePO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insertSelective(OptTablePO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    OptTablePO selectByPrimaryKey(String id);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKeySelective(OptTablePO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKey(OptTablePO record);
    
    /**
	 * 根据user_id,policy_id,fund_code查询最新的一条操作记录，比传入日期早的
	 */
	OptTablePO getLastPO(
			@Param("user_id")String user_id, 
			@Param("policy_id")String policy_id, 
			@Param("fund_code")String fund_code, 
			@Param("date0")String date0);
	
	/**
	 * 根据user_id,policy_id,fund_code,date0查询PO
	 */
	public OptTablePO getPOByUidAndPIdAndFCodeAndDate0(
			@Param("user_id")String user_id, 
			@Param("policy_id")String policy_id, 
			@Param("fund_code")String fund_code, 
			@Param("date0")String date0);
	
	/**
	 * 根据user_id,policy_id,fund_code,date0查询PO
	 */
	public OptTablePO getPOByUidAndPIdAndFCodeAndDate0AndType(
			@Param("user_id")String user_id, 
			@Param("policy_id")String policy_id, 
			@Param("fund_code")String fund_code, 
			@Param("date0")String date0,
			@Param("type")String type);
	
	/**
	 * 根据user_id,policy_id查询PO
	 */
	List<OptTablePO> getListByUserIdAndPolicyId(
			@Param("user_id")String user_id, 
			@Param("policy_id")String policy_id);
	
	/**
	 * 根据user_id,policy_id,fund_code查询PO
	 */
	List<OptTablePO> getListByUIdAndPIdAndFCode(
			@Param("user_id")String user_id, 
			@Param("policy_id")String policy_id, 
			@Param("fund_code")String fund_code);
	
	/**
	 * 根据前一个交易日与当前交易日，获取量交易日之间的此fund_code对应的待确认购买份数
	 */
	Double getTrueDay0(
			@Param("last_deal_date0")String lastDealDate0,
			@Param("now_deal_date0")String nowDealDate0,
			@Param("fund_code")String fund_code);
}