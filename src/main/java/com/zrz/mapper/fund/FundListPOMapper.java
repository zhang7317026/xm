package com.zrz.mapper.fund;

import java.util.List;

import com.zrz.entity.fund.FundListPO;

public interface FundListPOMapper {
    /**
     *
     * @mbggenerated 2018-10-24
     */
    int deleteByPrimaryKey(String fundCode);

    /**
     *
     * @mbggenerated 2018-10-24
     */
    int insert(FundListPO record);

    /**
     *
     * @mbggenerated 2018-10-24
     */
    int insertSelective(FundListPO record);

    /**
     *
     * @mbggenerated 2018-10-24
     */
    FundListPO selectByPrimaryKey(String fundCode);

    /**
     *
     * @mbggenerated 2018-10-24
     */
    int updateByPrimaryKeySelective(FundListPO record);

    /**
     *
     * @mbggenerated 2018-10-24
     */
    int updateByPrimaryKey(FundListPO record);
    
    /**
	 * 获取所有的fund信息
	 */
    List<FundListPO> getAllList();
}