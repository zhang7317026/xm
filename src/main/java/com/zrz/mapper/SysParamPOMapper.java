package com.zrz.mapper;

import com.zrz.entity.SysParamPO;

public interface SysParamPOMapper {
    /**
     *
     * @mbggenerated 2020-01-08
     */
    int deleteByPrimaryKey(String code);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int insert(SysParamPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int insertSelective(SysParamPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    SysParamPO selectByPrimaryKey(String code);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int updateByPrimaryKeySelective(SysParamPO record);

    /**
     *
     * @mbggenerated 2020-01-08
     */
    int updateByPrimaryKey(SysParamPO record);
}