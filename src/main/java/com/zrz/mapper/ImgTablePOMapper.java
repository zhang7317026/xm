package com.zrz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.ImgTablePO;

public interface ImgTablePOMapper {

	int deleteByPrimaryKey(String ID);

	ImgTablePO selectByPrimaryKey(String ID);

	int updateByPrimaryKeySelective(ImgTablePO record);

	int updateByPrimaryKey(ImgTablePO record);

	List<ImgTablePO> selectListByWhere(@Param("where") String where);

	int insert(ImgTablePO record);

	int insertSelective(ImgTablePO record);
}