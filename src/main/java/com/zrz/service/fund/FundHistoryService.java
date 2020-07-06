package com.zrz.service.fund;

import java.util.List;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.FundHistoryPOKey;


public interface FundHistoryService {
	
	FundHistoryPO getByKey(FundHistoryPOKey key);
	
	int save(FundHistoryPO fundHistoryPO);
	
	int insert(FundHistoryPO fundHistoryPO);
	
	int update(FundHistoryPO fundHistoryPO);
	
	int updateSelective(FundHistoryPO fundHistoryPO);
	
	int deleteById(FundHistoryPOKey key);
	
	/**
     * 根据fund_code及date0查询PO
     */
	FundHistoryPO getPOByCodeAndDate(String date0, String fund_code);
	
	/**
	 * 根据fund_code查询最后一个PO，当日之前的
	 */
	FundHistoryPO getLastPO(String fund_code);
	
	/**
     * 根据fund_code及date0、date1查询List
     */
    List<FundHistoryPO> getListByCodeAndDate(String date0, String date1, String fund_code);
    
    /**
     * 根据fund_code计算年线250日均线
     */
    void computeYearLine(String fund_code);
    
    /**
     * 根据fund_code计算日均线
     */
    void computeAvgLine(String fund_code);
}
