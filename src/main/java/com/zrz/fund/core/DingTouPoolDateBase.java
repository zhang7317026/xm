package com.zrz.fund.core;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.OptTablePO;
import com.zrz.entity.fund.PolicyFundPO;
import com.zrz.entity.fund.UserPolicyPO;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.mapper.fund.OptTablePOMapper;
import com.zrz.mapper.fund.PolicyFundPOMapper;
import com.zrz.mapper.fund.UserPolicyPOMapper;
import com.zrz.util.DateUtils;
import com.zrz.util.ToolClass;
import com.zrz.util.UUIDUtil;


/**
 * 定投，无复投，活期，定额周一，池化
 */

public class DingTouPoolDateBase {

	public static Map<String,Object> dingTouPool(
			String date0,
			String user_id, 
			String policy_id,
			OptTablePOMapper optTablePOMapper,
			UserPolicyPOMapper userPolicyPOMapper,
			PolicyFundPOMapper policyFundPOMapper,
			FundHistoryPOMapper fundHistoryPOMapper){
		
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
		
		
		double surplus = userPolicyPO.getSurplus().doubleValue();
		
		//判断周几
		Calendar cal=Calendar.getInstance();
		cal.setTime(DateUtils.formatDate(date0, "yyyy-MM-dd")); 
		int week = cal.get(Calendar.DAY_OF_WEEK)-1;
				
		for(int k=0;k<policyFundList.size();k++){
			PolicyFundPO policyFundPO = policyFundList.get(k);
			String fund_code = policyFundPO.getFundCode();
			//获取PO
			FundHistoryPO fundHistoryPO = fundHistoryPOMapper.getPOByCodeAndDate(fund_code, date0);
			if(fundHistoryPO==null){
				continue;
			}
			float close0 = fundHistoryPO.getClose0().floatValue();
    		float year0 = fundHistoryPO.getAvg250().floatValue();
    		
    		//浮动比例大于10%考虑回撤
    		if((policyFundPO.getSumAccount().doubleValue()*close0 - policyFundPO.getInputMoney().doubleValue())
    				/policyFundPO.getInputMoney().doubleValue()>=0.10
    				&& policyFundPO.getDay7().doubleValue()>0){
	    		//年线以下，大于15%后20日内最高点回调超过8%时全部回撤满7日的
    			double closeMax = fundHistoryPOMapper.getMaxByDateAndDayNum(fund_code, date0, 20);
    			if(close0<=closeMax*(1-policyFundPO.getBackRate().doubleValue())){
    				
    				//回撤满7日的
    				double dealAccount = policyFundPO.getDay7().doubleValue();
    				double sell_money = dealAccount * close0;
    				surplus = surplus + sell_money;
    				
    				//切换到下一周期
    				//更新UserPolicyPO
    				UserPolicyPO userPolicyPONew = new UserPolicyPO();
    				userPolicyPONew.setPolicyId(policy_id);
    				userPolicyPONew.setStartDate(date0);
    				userPolicyPONew.setSurplus(surplus);
    				//make_all=make_all+利润
    				double input_sell = policyFundPO.getInputMoney().doubleValue() *
	    					dealAccount/policyFundPO.getSumAccount().doubleValue();
    				userPolicyPONew.setMakeAll(
    						userPolicyPO.getMakeAll().doubleValue() 
    						+ (sell_money - input_sell));
    				//计算now_all
        			double now_all = userPolicyPO.getSurplus().doubleValue();
        			for(int n=0;n<policyFundList.size();n++){
        				now_all = now_all + 
        						policyFundPO.getSumAccount().doubleValue() * close0;
        			}
        			userPolicyPONew.setNowAll(now_all);
    				userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew);
    				userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
    				
    				//更新PolicyFundPO
    				PolicyFundPO policyFundPONew = new PolicyFundPO();
    				policyFundPONew.setId(policyFundPO.getId());
    				policyFundPONew.setDay7(0.0);
    				policyFundPONew.setSumAccount(
    						policyFundPO.getSumAccount().doubleValue() - dealAccount
    				);
    				policyFundPONew.setNowMoney(
    					policyFundPONew.getSumAccount().doubleValue() * close0
    				);
    				//按照百分比计算剩余input
    				policyFundPONew.setInputMoney(
	    					policyFundPO.getInputMoney().doubleValue()
	    					*policyFundPONew.getSumAccount().doubleValue()
	    					/policyFundPO.getSumAccount().doubleValue()
    				);
    				double now_money = policyFundPONew.getSumAccount().doubleValue() * close0;
    				double float_money = now_money - policyFundPONew.getInputMoney().doubleValue();
    				policyFundPONew.setFloatMoney(float_money - sell_money);
    				double float_rate = 0;
    				if(float_money!=0){
    					float_rate = float_money/policyFundPONew.getInputMoney().doubleValue();
    				}
    				policyFundPONew.setFloatMoney(float_money);
    				policyFundPONew.setFloatRate(float_rate);
    				policyFundPOMapper.updateByPrimaryKeySelective(policyFundPONew);
    				policyFundList = policyFundPOMapper.getListByPolicyId(policy_id);
    				policyFundPO = policyFundList.get(k);
    				
    				//记录操作
    				saveOpt("sell", 
    						closeMax, 
    						dealAccount, 
    						sell_money, 
    						fundHistoryPO, 
    						userPolicyPO, 
    						policyFundPO, 
    						optTablePOMapper);
    				
    			}
    		}
    		
    		//每次投入数额
			double input_per_one = userPolicyPO.getInputPer().doubleValue()/policyFundList.size();
			//周一定投,小于年线时才投
    		if(week==1 && close0<=year0){
    			double input_per_one_true = 
    					(policyFundPO.getInputMoney().doubleValue()+input_per_one) 
    					- policyFundPO.getNowMoney().doubleValue();
    			if(input_per_one_true>input_per_one){
    				//取补亏损值的开四次方倍数乘以基数
    				input_per_one_true = 
    						input_per_one * Math.pow(input_per_one_true/input_per_one,1.0/4);
    			}else{
    				input_per_one_true = input_per_one;
    			}
    			//向上取整
    			input_per_one_true = Math.ceil(input_per_one_true);
    			double deal_account = input_per_one_true/close0;
    			
    			//更新PolicyFundPO信息
    			PolicyFundPO policyFundPONew = new PolicyFundPO();
    			policyFundPONew.setId(policyFundPO.getId());
//    			policyFundPONew.setDay0(
//    					policyFundPO.getDay0().doubleValue() + deal_account
//    			);
    			policyFundPONew.setSumAccount(
    					policyFundPO.getSumAccount().doubleValue() + deal_account
    			);
    			policyFundPONew.setInputMoney(
    					policyFundPO.getInputMoney().doubleValue() + input_per_one_true
    			);
    			policyFundPONew.setNowMoney(
    					policyFundPONew.getSumAccount().doubleValue() * close0
    			);
    			double now_money = policyFundPONew.getSumAccount().doubleValue() * close0;
				double float_money = now_money - policyFundPONew.getInputMoney().doubleValue();
    			double float_rate = 0;
    			if(float_money!=0){
    				float_rate = float_money/policyFundPONew.getInputMoney().doubleValue();
    			}
    			policyFundPONew.setFloatMoney(float_money);
    			policyFundPONew.setFloatRate(float_rate);
    			policyFundPOMapper.updateByPrimaryKeySelective(policyFundPONew);
    			policyFundPO = policyFundPOMapper.selectByPrimaryKey(policyFundPONew.getId());
    			
    			//更新UserPolicyPO信息
    			UserPolicyPO userPolicyPONew = new UserPolicyPO();
    			userPolicyPONew.setPolicyId(policy_id);
    			userPolicyPONew.setInputAll(
    					userPolicyPO.getInputAll().doubleValue() + input_per_one_true
    			);
    			//计算now_all
    			double now_all = userPolicyPO.getSurplus().doubleValue();
    			for(int n=0;n<policyFundList.size();n++){
    				now_all = now_all + 
    						policyFundPO.getSumAccount().doubleValue() * fundHistoryPO.getClose0().doubleValue();
    			}
    			userPolicyPONew.setNowAll(now_all);
    			userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew);
    			userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
    			
    			//记录操作
				saveOpt("buy", 
						0, 
						input_per_one_true/close0, 
						input_per_one_true, 
						fundHistoryPO, 
						userPolicyPO, 
						policyFundPO, 
						optTablePOMapper);
				
    		//不投，则存活期
    		}else if(week==1){
    			
    			//更新UserPolicyPO信息
    			UserPolicyPO userPolicyPONew = new UserPolicyPO();
    			userPolicyPONew.setPolicyId(policy_id);
    			userPolicyPONew.setSurplus(
    					userPolicyPO.getSurplus().doubleValue() + input_per_one
    			);
    			userPolicyPONew.setInputAll(
    					userPolicyPO.getInputAll().doubleValue() + input_per_one
    			);
    			//计算now_all
    			double now_all = userPolicyPONew.getSurplus().doubleValue();
    			for(int n=0;n<policyFundList.size();n++){
    				now_all = now_all + 
    						policyFundPO.getSumAccount().doubleValue() * fundHistoryPO.getClose0().doubleValue();
    			}
    			userPolicyPONew.setNowAll(now_all);
    			userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew);
    			userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
    			
    			//记录操作
				saveOpt("surplus", 
						0, 
						0, 
						input_per_one, 
						fundHistoryPO, 
						userPolicyPO, 
						policyFundPO,
						optTablePOMapper);
    		}
    		
    		//复投，条件:向下偏离年线10%
    		double deviate_standard = policyFundPO.getDeviateStandard().doubleValue();
    		if(close0<=(year0*(1-deviate_standard))){
    			//偏移率
    			double deviateRate = (year0-close0)/year0;
    			//基准偏移率倍数乘以基准每份钱数(100份)
    			double input_per_true = (surplus/100)*(deviateRate/deviate_standard);
    			//投
    			if(surplus>=input_per_true && input_per_true>=200){
    				//更新PolicyFundPO信息
    				double input_per_true_num = input_per_true/close0;
    				PolicyFundPO policyFundPONew = new PolicyFundPO();
    				policyFundPONew.setId(policyFundPO.getId());
//    				policyFundPONew.setDay0(
//    						policyFundPO.getDay0().doubleValue() + input_per_true_num
//    				);
    				policyFundPONew.setSumAccount(
    						policyFundPO.getSumAccount().doubleValue() + input_per_true_num
    				);
    				policyFundPONew.setInputMoney(
    						policyFundPO.getInputMoney().doubleValue() + input_per_true
    				);
    				policyFundPONew.setNowMoney(
    						policyFundPONew.getSumAccount().doubleValue() * close0
        			);
    				double now_money = policyFundPONew.getSumAccount().doubleValue() * close0;
    				double float_money = now_money - policyFundPONew.getInputMoney().doubleValue();
        			double float_rate = 0;
        			if(float_money!=0){
        				float_rate = float_money/policyFundPONew.getInputMoney().doubleValue();
        			}
        			policyFundPONew.setFloatMoney(float_money);
        			policyFundPONew.setFloatRate(float_rate);
    				policyFundPONew.setDeviateRate(deviateRate);
    				policyFundPOMapper.updateByPrimaryKeySelective(policyFundPONew);
    				policyFundList = policyFundPOMapper.getListByPolicyId(policy_id);
    				policyFundPO = policyFundList.get(k);
    				
    				//更新UserPolicyPO信息
    				UserPolicyPO userPolicyPONew = new UserPolicyPO();
    				userPolicyPONew.setPolicyId(policy_id);
    				userPolicyPONew.setSurplus(
    						userPolicyPO.getSurplus().doubleValue() - input_per_true
    				);
    				//计算now_all
        			double now_all = userPolicyPONew.getSurplus().doubleValue();
        			for(int n=0;n<policyFundList.size();n++){
        				now_all = now_all + 
        						policyFundPO.getSumAccount().doubleValue() * fundHistoryPO.getClose0().doubleValue();
        			}
        			userPolicyPONew.setNowAll(now_all);
    				userPolicyPOMapper.updateByPrimaryKeySelective(userPolicyPONew);
    				userPolicyPO = userPolicyPOMapper.selectByPrimaryKey(policy_id);
    				
    				//记录操作
    				saveOpt("extra", 
    						0, 
    						input_per_true/close0, 
    						input_per_true, 
    						fundHistoryPO, 
    						userPolicyPO, 
    						policyFundPO, 
    						optTablePOMapper);
    			}
    		}
		}
		
		
		mapReturn.put("flag", "true");
    	return mapReturn;
	}
	
	
	public static void saveOpt(
			String type,
			double closeMax,
			double dealAccount,
			double dealPrice,
			FundHistoryPO fundHistoryPO,
			UserPolicyPO userPolicyPO,
			PolicyFundPO policyFundPO, 
			OptTablePOMapper optTablePOMapper){
		
		OptTablePO optTablePO = new OptTablePO();
		optTablePO.setId(UUIDUtil.uuidRandom());
		optTablePO.setType(type);
		optTablePO.setBackRate(policyFundPO.getBackRate());
		//查询上一交易日的close0
		OptTablePO optTablePOLast = 
				optTablePOMapper.getLastPO(
						userPolicyPO.getUserId(), 
						userPolicyPO.getPolicyId(), 
						policyFundPO.getFundCode(), 
						fundHistoryPO.getDate0());
		if(optTablePOLast!=null){
			optTablePO.setBeforeClose(optTablePOLast.getClose0());
		}
		optTablePO.setClose0(fundHistoryPO.getClose0());
		optTablePO.setCloseMax(closeMax);
		optTablePO.setCreateTime(ToolClass.getTime());
		optTablePO.setDate0(fundHistoryPO.getDate0());
		optTablePO.setDay0(policyFundPO.getDay0());
		optTablePO.setDay1(policyFundPO.getDay1());
		optTablePO.setDay2(policyFundPO.getDay2());
		optTablePO.setDay3(policyFundPO.getDay3());
		optTablePO.setDay4(policyFundPO.getDay4());
		optTablePO.setDay5(policyFundPO.getDay5());
		optTablePO.setDay6(policyFundPO.getDay6());
		optTablePO.setDay7(policyFundPO.getDay7());
		//交易数额
		optTablePO.setDealAccount(dealAccount);
		optTablePO.setDealPrice(dealPrice);
		optTablePO.setDeviateRate(policyFundPO.getDeviateRate());
		optTablePO.setDeviateStandard(policyFundPO.getDeviateStandard());
		optTablePO.setFloatMoney(policyFundPO.getFloatMoney());
		optTablePO.setFloatRate(policyFundPO.getFloatRate());
		optTablePO.setFundCode(policyFundPO.getFundCode());;
		optTablePO.setInputAll(userPolicyPO.getInputAll());
		optTablePO.setInputMoney(policyFundPO.getInputMoney());
		optTablePO.setMakeAll(userPolicyPO.getMakeAll());
		optTablePO.setNowAll(userPolicyPO.getNowAll());
		optTablePO.setNowClose(fundHistoryPO.getClose0());
		optTablePO.setNowMoney(policyFundPO.getNowMoney());
		optTablePO.setPolicyId(userPolicyPO.getPolicyId());
		optTablePO.setSumAccount(policyFundPO.getSumAccount());
		optTablePO.setSurplus(userPolicyPO.getSurplus());
		optTablePO.setSurplusInterest(userPolicyPO.getSurplusInterest());
		optTablePO.setUserId(userPolicyPO.getUserId());
		
		int num = 0;
		
		OptTablePO optTablePOOld = 
				optTablePOMapper.getPOByUidAndPIdAndFCodeAndDate0AndType(
						optTablePO.getUserId(), 
						optTablePO.getPolicyId(), 
						optTablePO.getFundCode(), 
						optTablePO.getDate0(),
						optTablePO.getType());
		if(optTablePOOld==null){
    		num = optTablePOMapper.insert(optTablePO);
    	}else{
    		optTablePO.setId(optTablePOOld.getId());
    		num = optTablePOMapper.updateByPrimaryKey(optTablePO);
    	}
		if(num != 1){
			throw new RuntimeException();
		}
	}
	
}
