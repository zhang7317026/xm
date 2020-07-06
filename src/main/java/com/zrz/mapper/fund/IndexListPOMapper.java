package com.zrz.mapper.fund;

import java.util.List;

import com.zrz.entity.fund.IndexListPO;

public interface IndexListPOMapper {
    /**
     *
     * @mbggenerated 2020-01-08
     */
    int deleteByPrimaryKey(String indexCode);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int insert(IndexListPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int insertSelective(IndexListPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    IndexListPO selectByPrimaryKey(String indexCode);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int updateByPrimaryKeySelective(IndexListPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int updateByPrimaryKey(IndexListPO record);
    
    
    /**
	 * 获取所有的fund信息
	 */
    List<IndexListPO> getAllList();
}