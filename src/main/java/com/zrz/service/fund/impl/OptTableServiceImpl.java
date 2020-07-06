package com.zrz.service.fund.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.OptTablePO;
import com.zrz.entity.fund.PolicyFundPO;
import com.zrz.entity.fund.UserPolicyPO;
import com.zrz.fund.core.DingTouPoolDateBase;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.mapper.fund.OptTablePOMapper;
import com.zrz.mapper.fund.PolicyFundPOMapper;
import com.zrz.mapper.fund.UserPolicyPOMapper;
import com.zrz.service.fund.OptTableService;
import com.zrz.util.ToolClass;

 
 
 
@Service
public class OptTableServiceImpl implements OptTableService{
	
	private final static Logger logger = LoggerFactory.getLogger(OptTableServiceImpl.class);

 
	@Autowired
    private FundHistoryPOMapper fundHistoryPOMapper;
    @Autowired
    private UserPolicyPOMapper userPolicyPOMapper;
    @Autowired
    private PolicyFundPOMapper policyFundPOMapper;
    @Autowired
    private OptTablePOMapper optTablePOMapper;
    
    public OptTablePO getById(String id){
    	if(StringUtils.isBlank(id)){
    		return null;
    	}
    	return optTablePOMapper.selectByPrimaryKey(id);
    }
    
    public int save(OptTablePO optTablePO){
    	int num = 0;
    	OptTablePO optTablePOOld = 
				optTablePOMapper.getPOByUidAndPIdAndFCodeAndDate0(
						optTablePO.getUserId(), 
						optTablePO.getPolicyId(), 
						optTablePO.getFundCode(), 
						optTablePO.getDate0());
		if(optTablePOOld==null){
    		num = optTablePOMapper.insert(optTablePO);
    	}else{
    		optTablePO.setId(optTablePOOld.getId());
    		num = optTablePOMapper.updateByPrimaryKey(optTablePO);
    	}
    	return num;
    }
    
    public int insert(OptTablePO optTablePO){
    	return optTablePOMapper.insert(optTablePO);
    }
    
    public int update(OptTablePO optTablePO){
    	return optTablePOMapper.updateByPrimaryKey(optTablePO);
    }
    
    public int updateSelective(OptTablePO optTablePO){
    	return optTablePOMapper.updateByPrimaryKeySelective(optTablePO);
    }
    
    public int deleteById(String id){
    	return optTablePOMapper.deleteByPrimaryKey(id);
    }
    
    /**
	 * 根据user_id,policy_id,fund_code查询最新的一条操作记录，比传入日期早的
	 */
	public OptTablePO getLastPO(String user_id, String policy_id, String fund_code, String date0){
		return optTablePOMapper.getLastPO(user_id, policy_id, fund_code, date0);
	}
	
	/**
	 * 根据user_id,policy_id,fund_code,date0查询PO
	 */
	public OptTablePO getPOByUidAndPIdAndFCodeAndDate0(
			String user_id, String policy_id, String fund_code, String date0){
		return optTablePOMapper.getPOByUidAndPIdAndFCodeAndDate0(user_id, policy_id, fund_code, date0);
	}
	
	/**
	 * 根据user_id,policy_id查询PO
	 */
	public List<OptTablePO> getListByUserIdAndPolicyId(String user_id, String policy_id){
		return optTablePOMapper.getListByUserIdAndPolicyId(user_id, policy_id);
	}
	
	/**
	 * 根据user_id,policy_id,fund_code查询PO
	 */
	public List<OptTablePO> getListByUIdAndPIdAndFCode(String user_id, String policy_id, String fund_code){
		return optTablePOMapper.getListByUIdAndPIdAndFCode(user_id, policy_id, fund_code);
	}
	
	
	/**
	 * 每凌晨1:00,计算昨日利息，移动days
	 */
	@Transactional
	public Map<String,Object> computeBefore(String user_id, String policy_id, String date0){
		Map<String,Object> mapReturn = new HashMap<String,Object>();

		//策略合规性判断
		//获取用户策略UserPolicyPO信息
		UserPolicyPO userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
		if(userPolicyPO==null||StringUtils.isBlank(userPolicyPO.getPolicyId())){
			mapReturn.put("flag", "ERROR");
			mapReturn.put("message", "用户策略信息为空");
			return mapReturn;
		}
		//策略基金List<PolicyFundPO>信息
		List<PolicyFundPO> policyFundList = policyFundPOMapper.getListByPolicyId(policy_id);
		if(policyFundList==null||policyFundList.size()==0){
			mapReturn.put("flag", "ERROR");
			mapReturn.put("message", "策略基金信息为空");
			return mapReturn;
		}
		//每凌晨1:00,计算昨日利息，移动days
		long lastDays = ToolClass.getDiffDays(userPolicyPO.getLastDate(), date0, "yyyy-MM-dd");
		//日期后移
		for(int k=0;k<policyFundList.size();k++){
			PolicyFundPO policyFundPO = policyFundList.get(k);
			double[] days = new double[8];
			
			//计算day0,确认份额
			String fund_code = policyFundPO.getFundCode();
			FundHistoryPO lastFundHistoryPO = fundHistoryPOMapper.getLastPO(fund_code, date0);
			Double day0 = optTablePOMapper.getTrueDay0(lastFundHistoryPO.getDate0(), date0, fund_code);
			if(day0!=null){
				days[0] = optTablePOMapper.getTrueDay0(lastFundHistoryPO.getDate0(), date0, fund_code);
			}else{
				days[0] = 0.0;
			}
			
			days[1] = policyFundPO.getDay1().doubleValue();
			days[2] = policyFundPO.getDay2().doubleValue();
			days[3] = policyFundPO.getDay3().doubleValue();
			days[4] = policyFundPO.getDay4().doubleValue();
			days[5] = policyFundPO.getDay5().doubleValue();
			days[6] = policyFundPO.getDay6().doubleValue();
			days[7] = policyFundPO.getDay7().doubleValue();
			//日期后移
			for(int n=0;n<lastDays;n++){
				days = ToolClass.moveDays(days);
			}
			//更新days
			PolicyFundPO policyFundPONew = new PolicyFundPO();
			policyFundPONew.setId(policyFundPO.getId());
			policyFundPONew.setDay0(days[0]);
			policyFundPONew.setDay1(days[1]);
			policyFundPONew.setDay2(days[2]);
			policyFundPONew.setDay3(days[3]);
			policyFundPONew.setDay4(days[4]);
			policyFundPONew.setDay5(days[5]);
			policyFundPONew.setDay6(days[6]);
			policyFundPONew.setDay7(days[7]);
			policyFundPOMapper.updateByPrimaryKeySelective(policyFundPONew);
		}
		//叠加利息
		double surplus = userPolicyPO.getSurplus().doubleValue();
		double make_oneDay = surplus*userPolicyPO.getSurplusInterest().doubleValue()*0.01/365;
		surplus = make_oneDay*lastDays + surplus;
		//叠加利息到make_all
		double make_all = userPolicyPO.getMakeAll().doubleValue();
		make_all = make_all + make_oneDay*lastDays;
		UserPolicyPO userPolicyPONew0 = new UserPolicyPO();
		//更新
		userPolicyPONew0.setPolicyId(policy_id);
		userPolicyPONew0.setMakeAll(make_all);
		userPolicyPONew0.setSurplus(surplus);
		userPolicyPONew0.setLastDate(date0);
		userPolicyPONew0.setUpdateDate(ToolClass.getDate());
		userPolicyPONew0.setUpdateTime(ToolClass.getTime());
		userPolicyPONew0.setStatus("computer_before_success");
		userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew0);
		
		return mapReturn;
	}
	
	
	
	/**
	 * 每晚6:00,计算当日份额
	 */
	@Transactional
	public Map<String,Object> computeAfter(String user_id, String policy_id, String date0){
		Map<String,Object> mapReturn = new HashMap<String,Object>();
		
		//获取用户策略UserPolicyPO信息
		UserPolicyPO userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
		if(userPolicyPO==null||StringUtils.isBlank(userPolicyPO.getPolicyId())){
			mapReturn.put("flag", "ERROR");
			mapReturn.put("message", "用户策略信息为空");
			return mapReturn;
		}
		//策略基金List<PolicyFundPO>信息
		List<PolicyFundPO> policyFundList = policyFundPOMapper.getListByPolicyId(policy_id);
		if(policyFundList==null||policyFundList.size()==0){
			mapReturn.put("flag", "ERROR");
			mapReturn.put("message", "策略基金信息为空");
			return mapReturn;
		}
		
		//实际操作
		DingTouPoolDateBase.dingTouPool(date0, user_id, policy_id, 
				optTablePOMapper, userPolicyPOMapper, policyFundPOMapper, fundHistoryPOMapper);
		
		//最终结算
		//计算now_all
		double now_all = userPolicyPO.getSurplus().doubleValue();
		for(int k=0;k<policyFundList.size();k++){
			PolicyFundPO policyFundPO = policyFundList.get(k);
			String fund_code = policyFundPO.getFundCode();
			//获取PO
			FundHistoryPO fundHistoryPO = fundHistoryPOMapper.getPOByCodeAndDate(fund_code, date0);
			if(fundHistoryPO==null){
				mapReturn.put("flag", "ERROR");
				mapReturn.put("message", "当日基金信息为空");
				throw new RuntimeException();
			}
			//计算now_all
			now_all = now_all + 
					policyFundPO.getSumAccount().doubleValue() * fundHistoryPO.getClose0().doubleValue();
			//计算float_money
			PolicyFundPO policyFundPONew = new PolicyFundPO();
			policyFundPONew.setId(policyFundPO.getId());
			double now_money = 
					policyFundPO.getSumAccount().doubleValue() * fundHistoryPO.getClose0().doubleValue();
			double float_money = now_money - policyFundPO.getInputMoney().doubleValue();
			policyFundPONew.setFloatMoney(float_money);
			double float_rate = 0;
			if(float_money!=0){
				float_rate = float_money/policyFundPO.getInputMoney().doubleValue();
			}
			//年线偏离率
			double year0 = fundHistoryPO.getAvg250().doubleValue();
			double close0 = fundHistoryPO.getClose0().doubleValue();
			double deviateRate = (year0-close0)/year0;
			//更新
			policyFundPONew.setNowMoney(now_money);
			policyFundPONew.setFloatRate(float_rate);
			policyFundPONew.setDeviateRate(deviateRate);
			policyFundPOMapper.updateByPrimaryKeySelective(policyFundPONew);
		}
				
		//更新lastday
		UserPolicyPO userPolicyPONew0 = new UserPolicyPO();
		userPolicyPONew0.setPolicyId(policy_id);
		userPolicyPONew0.setNowAll(now_all);
		userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew0);
		userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
		
		return mapReturn;
	}
	
	
	/**
	 * 产生预交易操作
	 * @param OptTablePO optTablePO
	 */
	@Transactional
	public Map<String,Object> operate(String user_id, String policy_id, String date0){
		Map<String,Object> mapReturn = new HashMap<String,Object>();
		
		
		//每晚6:00,计算当日份额
		DingTouPoolDateBase.dingTouPool(date0, user_id, policy_id, 
				optTablePOMapper, userPolicyPOMapper, policyFundPOMapper, fundHistoryPOMapper);
		
		
		return mapReturn;
	}
}