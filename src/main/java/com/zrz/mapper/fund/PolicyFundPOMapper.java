package com.zrz.mapper.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.fund.PolicyFundPO;

public interface PolicyFundPOMapper {
    /**
     *
     * @mbggenerated 2019-03-27
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insert(PolicyFundPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insertSelective(PolicyFundPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    PolicyFundPO selectByPrimaryKey(String id);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKeySelective(PolicyFundPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKey(PolicyFundPO record);
    
    /**
	 * 根据策略id获取自己持有的基金详细信息
	 */
    List<PolicyFundPO> getListByPolicyId(@Param("policy_id") String policy_id);
    
    /**
	 * 根据fund_code获取自己持有的基金PO列表
	 */
    List<PolicyFundPO> getListByFundCode(@Param("fund_code")String fund_code);
    
    /**
	 * 根据policy_id及fund_code查询PO
	 */
	PolicyFundPO getPOByPolicyIdAndFundCode(
			@Param("policy_id")String policy_id, @Param("fund_code")String fund_code);
}