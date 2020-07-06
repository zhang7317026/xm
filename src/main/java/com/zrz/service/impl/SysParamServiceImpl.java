package com.zrz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.SysParamPO;
import com.zrz.mapper.SysParamPOMapper;
import com.zrz.service.SysParamService;;
 
 
 
@Service
public class SysParamServiceImpl implements SysParamService{
 
    @Autowired
    private SysParamPOMapper sysParamPOMapper;
    
    public SysParamPO getByCode(String code){
    	return sysParamPOMapper.selectByPrimaryKey(code);
    }
    
    public int save(SysParamPO sysParamPO){
    	int num = 0;
    	
    	SysParamPO sysParamPOTemp =  getByCode(sysParamPO.getParamCode());
    	if(sysParamPOTemp!=null&&!"".equals(sysParamPOTemp.getParamCode())){
    		num = sysParamPOMapper.updateByPrimaryKeySelective(sysParamPO);
    	}else{
    		num = sysParamPOMapper.insert(sysParamPO);
    	}
    	
    	return num;
    }
    
    public int deleteByCode(String code){
    	return sysParamPOMapper.deleteByPrimaryKey(code);
    }

}