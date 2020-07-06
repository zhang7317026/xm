package com.zrz.service.fund.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zrz.entity.fund.OptTablePO;
import com.zrz.entity.fund.PolicyFundPO;
import com.zrz.mapper.fund.OptTablePOMapper;
import com.zrz.mapper.fund.PolicyFundPOMapper;
import com.zrz.service.fund.PolicyFundService;

 
 
 
@Service
public class PolicyFundServiceImpl implements PolicyFundService{
 
	private final static Logger logger = LoggerFactory.getLogger(PolicyFundServiceImpl.class);
	
    @Autowired
    private PolicyFundPOMapper policyFundPOMapper;
    @Autowired
    private OptTablePOMapper optTablePOMapper;
    
    
    public PolicyFundPO getById(String id){
    	if(StringUtils.isBlank(id)){
    		return null;
    	}
    	return policyFundPOMapper.selectByPrimaryKey(id);
    }
    
    public int save(PolicyFundPO policyFundPO){
    	int num = 0;
    	if(StringUtils.isEmpty(policyFundPO.getId())
    			||policyFundPOMapper.selectByPrimaryKey(policyFundPO.getId())==null){
    		num = policyFundPOMapper.insert(policyFundPO);
    	}else{
    		num = policyFundPOMapper.updateByPrimaryKey(policyFundPO);
    	}
    	
    	return num;
    }
    
    public int insert(PolicyFundPO policyFundPO){
    	return policyFundPOMapper.insert(policyFundPO);
    }
    
    public int update(PolicyFundPO policyFundPO){
    	return policyFundPOMapper.updateByPrimaryKey(policyFundPO);
    }
    
    public int updateSelective(PolicyFundPO policyFundPO){
    	return policyFundPOMapper.updateByPrimaryKeySelective(policyFundPO);
    }
    
    public int deleteById(String id){
    	return policyFundPOMapper.deleteByPrimaryKey(id);
    }

    /**
	 * 根据策略id获取自己持有的基金详细信息
	 */
    @Override
    public List<PolicyFundPO> getListByPolicyId(String policy_id){
    	return policyFundPOMapper.getListByPolicyId(policy_id);
    }
	
    /**
	 * 根据fund_code获取自己持有的基金PO列表
	 */
    @Override
    public List<PolicyFundPO> getListByFundCode(String fund_code){
		return policyFundPOMapper.getListByFundCode(fund_code);
    }
    
	/**
	 * 根据policy_id及fund_code查询PO
	 */
	@Override
	public PolicyFundPO getPOByPolicyIdAndFundCode(String policy_id, String fund_code){
		return policyFundPOMapper.getPOByPolicyIdAndFundCode(policy_id, fund_code);
	}
	
	/**
	 * 每日交易结算
	 */
	@Transactional
	public boolean deal(PolicyFundPO PolicyFundPO, OptTablePO optTablePO){
		
		int numPolicyFund = policyFundPOMapper.updateByPrimaryKeySelective(PolicyFundPO);
		int numOptTable = optTablePOMapper.updateByPrimaryKeySelective(optTablePO);
		if(numPolicyFund == 1 && numOptTable == 1){
			return true;
		}else{
			return false;
		}
	}
	
}