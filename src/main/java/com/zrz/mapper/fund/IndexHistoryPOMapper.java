package com.zrz.mapper.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.fund.IndexHistoryPO;
import com.zrz.entity.fund.IndexHistoryPOKey;

public interface IndexHistoryPOMapper {
    /**
     *
     * @mbggenerated 2020-01-09
     */
    int deleteByPrimaryKey(IndexHistoryPOKey key);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int insert(IndexHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int insertSelective(IndexHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    IndexHistoryPO selectByPrimaryKey(IndexHistoryPOKey key);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int updateByPrimaryKeySelective(IndexHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int updateByPrimaryKey(IndexHistoryPO record);
    
    
    
    /**
     * 根据index_code及date0查询PO
     */
    IndexHistoryPO getPOByCodeAndDate(@Param("index_code") String index_code, @Param("date0") String date0);

    /**
	 * 根据index_code查询最后一个PO，当日之前的
	 */
    IndexHistoryPO getLastPO(@Param("index_code") String index_code, @Param("date0") String date0);
    
    /**
     * 根据index_code及date0、date1查询List
     */
    List<IndexHistoryPO> getListByCodeAndDate(
    		@Param("date0")String date0, @Param("date1")String date1, @Param("index_code")String index_code);
    
    /**
     * 根据index_code及date0、dayNum获取过去从date0开始dayNum天的平均值
     */
    double getAvgByDateAndDayNum(
    		@Param("index_code")String index_code, @Param("date0")String date0, 
    		@Param("dayNum")int dayNum);
    
    /**
     * 根据index_code及date0、dayNum获取过去从date0开始dayNum天的最大值
     */
    double getMaxByDateAndDayNum(
    		@Param("index_code")String index_code, @Param("date0")String date0, 
    		@Param("dayNum")int dayNum);
}