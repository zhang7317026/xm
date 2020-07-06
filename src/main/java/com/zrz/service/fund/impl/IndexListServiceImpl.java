package com.zrz.service.fund.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.fund.IndexListPO;
import com.zrz.mapper.fund.IndexListPOMapper;
import com.zrz.service.fund.IndexListService;

 
 
 
@Service
public class IndexListServiceImpl implements IndexListService{
 
    @Autowired
    private IndexListPOMapper indexListPOMapper;
    
    public IndexListPO getById(String id){
    	if(StringUtils.isBlank(id)){
    		return null;
    	}
    	return indexListPOMapper.selectByPrimaryKey(id);
    }
    
    public int save(IndexListPO indexList){
    	int num = 0;
    	if(StringUtils.isEmpty(indexList.getIndexCode())
    			||indexListPOMapper.selectByPrimaryKey(indexList.getIndexCode())==null){
    		num = indexListPOMapper.insert(indexList);
    	}else{
    		num = indexListPOMapper.updateByPrimaryKey(indexList);
    	}
    	
    	return num;
    }
    
    public int insert(IndexListPO indexList){
    	return indexListPOMapper.insert(indexList);
    }
    
    public int update(IndexListPO indexList){
    	return indexListPOMapper.updateByPrimaryKey(indexList);
    }
    
    public int updateSelective(IndexListPO indexList){
    	return indexListPOMapper.updateByPrimaryKeySelective(indexList);
    }
    
    public int deleteById(String id){
    	return indexListPOMapper.deleteByPrimaryKey(id);
    }
    
    /**
	 * 获取所有的index信息
	 */
    public List<IndexListPO> getAllList(){
    	return indexListPOMapper.getAllList();
    }
    
}