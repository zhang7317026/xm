package com.zrz.service.fund.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.fund.IndexHistoryPO;
import com.zrz.entity.fund.IndexHistoryPOKey;
import com.zrz.mapper.fund.IndexHistoryPOMapper;
import com.zrz.service.fund.IndexHistoryService;
import com.zrz.util.ToolClass;

 
 
 
@Service
public class IndexHistoryServiceImpl implements IndexHistoryService{
 
    @Autowired
    private IndexHistoryPOMapper indexHistoryPOMapper;
    
    public IndexHistoryPO getByKey(IndexHistoryPOKey key){
    	if(key == null || StringUtils.isBlank(key.getIndexCode()) || StringUtils.isBlank(key.getDate0())){
    		return null;
    	}
    	return indexHistoryPOMapper.selectByPrimaryKey(key);
    }
    
    public int save(IndexHistoryPO indexHistoryPO){
    	int num = 0;
    	
    	IndexHistoryPOKey key = new IndexHistoryPOKey();
    	key.setIndexCode(indexHistoryPO.getIndexCode());
    	key.setDate0(indexHistoryPO.getDate0());
    	
    	IndexHistoryPO indexHistoryPOOld = indexHistoryPOMapper.selectByPrimaryKey(key);
    	if(indexHistoryPOOld == null
    			||StringUtils.isEmpty(indexHistoryPO.getIndexCode())){
    		num = indexHistoryPOMapper.insert(indexHistoryPO);
    	}else{
    		num = indexHistoryPOMapper.updateByPrimaryKey(indexHistoryPO);
    	}
    	
    	return num;
    }
    
    public int insert(IndexHistoryPO indexHistoryPO){
    	return indexHistoryPOMapper.insert(indexHistoryPO);
    }
    
    public int update(IndexHistoryPO indexHistoryPO){
    	return indexHistoryPOMapper.updateByPrimaryKey(indexHistoryPO);
    }
    
    public int updateSelective(IndexHistoryPO indexHistoryPO){
    	return indexHistoryPOMapper.updateByPrimaryKeySelective(indexHistoryPO);
    }
    
    public int deleteById(IndexHistoryPOKey key){
    	return indexHistoryPOMapper.deleteByPrimaryKey(key);
    }
    
    /**
     * 根据index_code及date0查询PO
     */
    public IndexHistoryPO getPOByCodeAndDate(String date0, String index_code){
    	return indexHistoryPOMapper.getPOByCodeAndDate(index_code, date0);
    }
    
    /**
     * 根据index_code及date0、date1查询List
     */
    public List<IndexHistoryPO> getListByCodeAndDate(String date0, String date1, String index_code){
    	return indexHistoryPOMapper.getListByCodeAndDate(date0, date1, index_code);
    }
    
    /**
	 * 根据index_code查询最后一个PO，当日之前的
	 */
    public IndexHistoryPO getLastPO(String index_code){
    	String date0 = ToolClass.getDate();
    	return indexHistoryPOMapper.getLastPO(index_code, date0);
    }
	
    
    /**
     * 根据index_code计算年线250日均线
     */
    public void computeYearLine(String index_code){
    	//查出所有的记录
    	List<IndexHistoryPO> list 
    		= indexHistoryPOMapper.getListByCodeAndDate("1900-01-01", "2900-01-01", index_code);
    	for(IndexHistoryPO indexHistoryPO : list){
	    	//根据index_code及date0、dayNum获取过去从date0开始dayNum天的平均值
	    	double avg250 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 250);
	    	indexHistoryPO.setAvg250(avg250);
	    	indexHistoryPOMapper.updateByPrimaryKeySelective(indexHistoryPO);
    	}
    }
    
    /**
     * 根据index_code计算日均线
     */
    public void computeAvgLine(String index_code){
    	//查出所有的记录
    	List<IndexHistoryPO> list 
    		= indexHistoryPOMapper.getListByCodeAndDate("1900-01-01", "2900-01-01", index_code);
    	System.out.println("-共计-"+list.size()+"条--");
    	for(IndexHistoryPO indexHistoryPO : list){
	    	//根据index_code及date0、dayNum获取过去从date0开始dayNum天的平均值
	    	double avg5 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 5);
	    	indexHistoryPO.setAvg5(avg5);
	    	double avg10 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 10);
	    	indexHistoryPO.setAvg10(avg10);
	    	double avg20 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 20);
	    	indexHistoryPO.setAvg20(avg20);
	    	double avg60 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 60);
	    	indexHistoryPO.setAvg60(avg60);
	    	double avg120 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 120);
	    	indexHistoryPO.setAvg120(avg120);
	    	double avg250 = indexHistoryPOMapper.getAvgByDateAndDayNum(index_code, indexHistoryPO.getDate0(), 250);
	    	indexHistoryPO.setAvg250(avg250);
	    	indexHistoryPOMapper.updateByPrimaryKeySelective(indexHistoryPO);
    	}
    }
    
}