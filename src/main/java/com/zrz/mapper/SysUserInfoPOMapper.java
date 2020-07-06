package com.zrz.mapper;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.SysUserInfoPO;

public interface SysUserInfoPOMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUserInfoPO record);

    int insertSelective(SysUserInfoPO record);

    SysUserInfoPO selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUserInfoPO record);

    int updateByPrimaryKey(SysUserInfoPO record);
    
    SysUserInfoPO getByAccount(String account);
    
    void updateGold(@Param("user_id") String user_id, @Param("flag") String flag, @Param("num") int num);
    
    void updateLevel(@Param("user_id")String user_id ,@Param("level")int level);
}