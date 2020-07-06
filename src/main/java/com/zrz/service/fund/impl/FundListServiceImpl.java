package com.zrz.service.fund.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.fund.FundListPO;
import com.zrz.mapper.fund.FundListPOMapper;
import com.zrz.service.fund.FundListService;

 
 
 
@Service
public class FundListServiceImpl implements FundListService{
 
    @Autowired
    private FundListPOMapper fundListPOMapper;
    
    public FundListPO getById(String id){
    	if(StringUtils.isBlank(id)){
    		return null;
    	}
    	return fundListPOMapper.selectByPrimaryKey(id);
    }
    
    public int save(FundListPO fundList){
    	int num = 0;
    	if(StringUtils.isEmpty(fundList.getFundCode())
    			||fundListPOMapper.selectByPrimaryKey(fundList.getFundCode())==null){
    		num = fundListPOMapper.insert(fundList);
    	}else{
    		num = fundListPOMapper.updateByPrimaryKey(fundList);
    	}
    	
    	return num;
    }
    
    public int insert(FundListPO fundList){
    	return fundListPOMapper.insert(fundList);
    }
    
    public int update(FundListPO fundList){
    	return fundListPOMapper.updateByPrimaryKey(fundList);
    }
    
    public int updateSelective(FundListPO fundList){
    	return fundListPOMapper.updateByPrimaryKeySelective(fundList);
    }
    
    public int deleteById(String id){
    	return fundListPOMapper.deleteByPrimaryKey(id);
    }
    
    /**
	 * 获取所有的fund信息
	 */
    public List<FundListPO> getAllList(){
    	return fundListPOMapper.getAllList();
    }
    
}