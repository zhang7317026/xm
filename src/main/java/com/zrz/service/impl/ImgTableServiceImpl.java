package com.zrz.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.ImgTablePO;
import com.zrz.mapper.ImgTablePOMapper;
import com.zrz.service.ImgTableService;;
 
 
 
@Service
public class ImgTableServiceImpl implements ImgTableService{
 
    @Autowired
    private ImgTablePOMapper imgTablePOMapper;
    
    public List<ImgTablePO> selectList(Map<String,Object> mapWhere){
    	
    	String typeSelect = (String) mapWhere.get("typeSelect");
    	String nameSelect = (String) mapWhere.get("nameSelect");
    	String mainChipGroupSelect = (String) mapWhere.get("mainChipGroupSelect");
    	
    	String where = "";
    	if(StringUtils.isNotEmpty(typeSelect)){
    		where += " and TYPE = '"+typeSelect+"'";
    	}
    	if(StringUtils.isNotEmpty(nameSelect)){
    		where += " and NAME like '%"+nameSelect+"%'";
    	}
    	if(StringUtils.isNotEmpty(mainChipGroupSelect)){
    		where += " and mainChipGroup like '%"+mainChipGroupSelect+"%'";
    	}
    	where += " order by NAME";
    	
    	return imgTablePOMapper.selectListByWhere(where);
    }
    
    public ImgTablePO getById(String id){
    	return imgTablePOMapper.selectByPrimaryKey(id);
    }
    
    public int save(ImgTablePO imgTablePO){
    	int num = 0;
    	
    	ImgTablePO imgTablePOTemp =  getById(imgTablePO.getId());
    	if(imgTablePOTemp!=null&&!"".equals(imgTablePOTemp.getId())){
    		num = imgTablePOMapper.updateByPrimaryKeySelective(imgTablePO);
    	}else{
    		num = imgTablePOMapper.insert(imgTablePO);
    	}
    	
    	return num;
    }
    
    public int deleteById(String id){
    	return imgTablePOMapper.deleteByPrimaryKey(id);
    }
    
}