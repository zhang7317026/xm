package com.zrz.service.fund;

import java.util.List;

import com.zrz.entity.fund.IndexHistoryPO;
import com.zrz.entity.fund.IndexHistoryPOKey;


public interface IndexHistoryService {
	
	IndexHistoryPO getByKey(IndexHistoryPOKey key);
	
	int save(IndexHistoryPO indexHistoryPO);
	
	int insert(IndexHistoryPO indexHistoryPO);
	
	int update(IndexHistoryPO indexHistoryPO);
	
	int updateSelective(IndexHistoryPO indexHistoryPO);
	
	int deleteById(IndexHistoryPOKey key);
	
	/**
     * 根据index_code及date0查询PO
     */
	IndexHistoryPO getPOByCodeAndDate(String date0, String index_code);
	
	/**
	 * 根据index_code查询最后一个PO，当日之前的
	 */
	IndexHistoryPO getLastPO(String index_code);
	
	/**
     * 根据index_code及date0、date1查询List
     */
    List<IndexHistoryPO> getListByCodeAndDate(String date0, String date1, String index_code);
    
    /**
     * 根据index_code计算年线250日均线
     */
    void computeYearLine(String index_code);
    
    /**
     * 根据index_code计算日均线
     */
    void computeAvgLine(String index_code);
}
