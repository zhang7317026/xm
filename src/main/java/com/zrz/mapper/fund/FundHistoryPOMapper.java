package com.zrz.mapper.fund;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.FundHistoryPOKey;

public interface FundHistoryPOMapper {
    /**
     *
     * @mbggenerated 2020-01-09
     */
    int deleteByPrimaryKey(FundHistoryPOKey key);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int insert(FundHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int insertSelective(FundHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    FundHistoryPO selectByPrimaryKey(FundHistoryPOKey key);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int updateByPrimaryKeySelective(FundHistoryPO record);

    /**
     *
     * @mbggenerated 2020-01-09
     */
    int updateByPrimaryKey(FundHistoryPO record);
    
    
    
    /**
     * 根据fund_code及date0查询PO
     */
    FundHistoryPO getPOByCodeAndDate(@Param("fund_code") String fund_code, @Param("date0") String date0);

    /**
	 * 根据fund_code查询最后一个PO，当日之前的
	 */
    FundHistoryPO getLastPO(@Param("fund_code") String fund_code, @Param("date0") String date0);
    
    /**
     * 根据fund_code及date0、date1查询List
     */
    List<FundHistoryPO> getListByCodeAndDate(
    		@Param("date0")String date0, @Param("date1")String date1, @Param("fund_code")String fund_code);
    
    /**
     * 根据fund_code及date0、dayNum获取过去从date0开始dayNum天的平均值
     */
    double getAvgByDateAndDayNum(
    		@Param("fund_code")String fund_code, @Param("date0")String date0, 
    		@Param("dayNum")int dayNum);
    
    /**
     * 根据fund_code及date0、dayNum获取过去从date0开始dayNum天的最大值
     */
    double getMaxByDateAndDayNum(
    		@Param("fund_code")String fund_code, @Param("date0")String date0, 
    		@Param("dayNum")int dayNum);
}