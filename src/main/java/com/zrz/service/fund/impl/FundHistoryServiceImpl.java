package com.zrz.service.fund.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.FundHistoryPOKey;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.service.fund.FundHistoryService;
import com.zrz.util.ToolClass;

 
 
 
@Service
public class FundHistoryServiceImpl implements FundHistoryService{
 
    @Autowired
    private FundHistoryPOMapper fundHistoryPOMapper;
    
    public FundHistoryPO getByKey(FundHistoryPOKey key){
    	if(key == null || StringUtils.isBlank(key.getFundCode()) || StringUtils.isBlank(key.getDate0())){
    		return null;
    	}
    	return fundHistoryPOMapper.selectByPrimaryKey(key);
    }
    
    public int save(FundHistoryPO fundHistoryPO){
    	int num = 0;
    	
    	FundHistoryPOKey key = new FundHistoryPOKey();
    	key.setFundCode(fundHistoryPO.getFundCode());
    	key.setDate0(fundHistoryPO.getDate0());
    	
    	FundHistoryPO fundHistoryPOOld = fundHistoryPOMapper.selectByPrimaryKey(key);
    	if(fundHistoryPOOld == null
    			||StringUtils.isEmpty(fundHistoryPO.getFundCode())){
    		num = fundHistoryPOMapper.insert(fundHistoryPO);
    	}else{
    		num = fundHistoryPOMapper.updateByPrimaryKey(fundHistoryPO);
    	}
    	
    	return num;
    }
    
    public int insert(FundHistoryPO fundHistoryPO){
    	return fundHistoryPOMapper.insert(fundHistoryPO);
    }
    
    public int update(FundHistoryPO fundHistoryPO){
    	return fundHistoryPOMapper.updateByPrimaryKey(fundHistoryPO);
    }
    
    public int updateSelective(FundHistoryPO fundHistoryPO){
    	return fundHistoryPOMapper.updateByPrimaryKeySelective(fundHistoryPO);
    }
    
    public int deleteById(FundHistoryPOKey key){
    	return fundHistoryPOMapper.deleteByPrimaryKey(key);
    }
    
    /**
     * 根据fund_code及date0查询PO
     */
    public FundHistoryPO getPOByCodeAndDate(String date0, String fund_code){
    	return fundHistoryPOMapper.getPOByCodeAndDate(fund_code, date0);
    }
    
    /**
     * 根据fund_code及date0、date1查询List
     */
    public List<FundHistoryPO> getListByCodeAndDate(String date0, String date1, String fund_code){
    	return fundHistoryPOMapper.getListByCodeAndDate(date0, date1, fund_code);
    }
    
    /**
	 * 根据fund_code查询最后一个PO，当日之前的
	 */
    public FundHistoryPO getLastPO(String fund_code){
    	String date0 = ToolClass.getDate();
    	return fundHistoryPOMapper.getLastPO(fund_code, date0);
    }
	
    
    /**
     * 根据fund_code计算年线250日均线
     */
    public void computeYearLine(String fund_code){
    	//查出所有的记录
    	List<FundHistoryPO> list 
    		= fundHistoryPOMapper.getListByCodeAndDate("1900-01-01", "2900-01-01", fund_code);
    	for(FundHistoryPO fundHistoryPO : list){
	    	//根据fund_code及date0、dayNum获取过去从date0开始dayNum天的平均值
	    	double avg250 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 250);
	    	fundHistoryPO.setAvg250(avg250);
	    	fundHistoryPOMapper.updateByPrimaryKeySelective(fundHistoryPO);
    	}
    }
    
    /**
     * 根据fund_code计算日均线
     */
    public void computeAvgLine(String fund_code){
    	//查出所有的记录
    	List<FundHistoryPO> list 
    		= fundHistoryPOMapper.getListByCodeAndDate("1900-01-01", "2900-01-01", fund_code);
    	System.out.println("-共计-"+list.size()+"条--");
    	for(FundHistoryPO fundHistoryPO : list){
	    	//根据fund_code及date0、dayNum获取过去从date0开始dayNum天的平均值
	    	double avg5 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 5);
	    	fundHistoryPO.setAvg5(avg5);
	    	double avg10 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 10);
	    	fundHistoryPO.setAvg10(avg10);
	    	double avg20 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 20);
	    	fundHistoryPO.setAvg20(avg20);
	    	double avg60 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 60);
	    	fundHistoryPO.setAvg60(avg60);
	    	double avg120 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 120);
	    	fundHistoryPO.setAvg120(avg120);
	    	double avg250 = fundHistoryPOMapper.getAvgByDateAndDayNum(fund_code, fundHistoryPO.getDate0(), 250);
	    	fundHistoryPO.setAvg250(avg250);
	    	fundHistoryPOMapper.updateByPrimaryKeySelective(fundHistoryPO);
    	}
    }
    
}