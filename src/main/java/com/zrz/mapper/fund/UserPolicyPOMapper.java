package com.zrz.mapper.fund;

import java.util.List;

import com.zrz.entity.fund.UserPolicyPO;

public interface UserPolicyPOMapper {
    /**
     *
     * @mbggenerated 2019-03-27
     */
    int deleteByPrimaryKey(String policyId);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insert(UserPolicyPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int insertSelective(UserPolicyPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    UserPolicyPO selectByPrimaryKey(String policyId);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKeySelective(UserPolicyPO record);

    /**
     *
     * @mbggenerated 2019-03-27
     */
    int updateByPrimaryKey(UserPolicyPO record);
    
    /**
	 * 根据策略名称模糊查询自己持有的基金详细信息
	 */
	List<UserPolicyPO> getListLikePolicyName(String user_id, String policy_name);
	
	/**
	 * 根据user_id获取自己的策略PO列表
	 */
	List<UserPolicyPO> getListByUserId(String user_id);
}