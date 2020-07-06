package com.zrz.service.fund.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zrz.entity.fund.UserPolicyPO;
import com.zrz.mapper.fund.UserPolicyPOMapper;
import com.zrz.service.fund.UserPolicyService;

 
 
 
@Service
public class UserPolicyServiceImpl implements UserPolicyService{
 
	private final static Logger logger = LoggerFactory.getLogger(UserPolicyServiceImpl.class);
	
    @Autowired
    private UserPolicyPOMapper userPolicyPOMapper;
    
    
    public UserPolicyPO getById(String id){
    	if(StringUtils.isBlank(id)){
    		return null;
    	}
    	return userPolicyPOMapper.selectByPrimaryKey(id);
    }
    
    public int save(UserPolicyPO userPolicyPO){
    	int num = 0;
    	if(StringUtils.isEmpty(userPolicyPO.getPolicyId())
    			||userPolicyPOMapper.selectByPrimaryKey(userPolicyPO.getPolicyId())==null){
    		num = userPolicyPOMapper.insert(userPolicyPO);
    	}else{
    		num = userPolicyPOMapper.updateByPrimaryKey(userPolicyPO);
    	}
    	
    	return num;
    }
    
    public int insert(UserPolicyPO userPolicyPO){
    	return userPolicyPOMapper.insert(userPolicyPO);
    }
    
    public int update(UserPolicyPO userPolicyPO){
    	return userPolicyPOMapper.updateByPrimaryKey(userPolicyPO);
    }
    
    public int updateSelective(UserPolicyPO userPolicyPO){
    	return userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPO);
    }
    
    public int deleteById(String id){
    	return userPolicyPOMapper.deleteByPrimaryKey(id);
    }

    
    /**
	 * 根据策略名称模糊查询自己持有的基金详细信息
	 */
    @Override
    public List<UserPolicyPO> getListLikePolicyName(String user_id, String policy_name){
    	return userPolicyPOMapper.getListLikePolicyName(user_id, policy_name);
    }
	
	/**
	 * 根据user_id获取自己的策略PO列表
	 */
    @Override
    public List<UserPolicyPO> getListByUserId(String user_id){
    	return userPolicyPOMapper.getListByUserId(user_id);
    }
	
	
}