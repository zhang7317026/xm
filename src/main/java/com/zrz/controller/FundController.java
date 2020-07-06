package com.zrz.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zrz.entity.SysUserInfoPO;
import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.OptTablePO;
import com.zrz.entity.fund.PolicyFundPO;
import com.zrz.entity.fund.UserPolicyPO;
import com.zrz.service.SysUserInfoService;
import com.zrz.service.fund.FundHistoryService;
import com.zrz.service.fund.OptTableService;
import com.zrz.service.fund.PolicyFundService;
import com.zrz.service.fund.UserPolicyService;
import com.zrz.util.ToolClass;
 
@Controller
@RequestMapping(value="/fund")
public class FundController {
 
	@Autowired
	private OptTableService optTableService;
	@Autowired
	private UserPolicyService userPolicyService;
	@Autowired
	private PolicyFundService policyFundService;
	@Autowired
	private FundHistoryService fundHistoryService;
	@Autowired
	private SysUserInfoService sysUserInfoService;
	
    @RequestMapping(value={"","/"})
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
    	
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/main/main");
        
        return mav;
    }
    
    
	@RequestMapping(value={"/opt"})
    @ResponseBody
    public Map<String, Object> opt(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		String user_id = request.getParameter("userId");
		String policy_id = request.getParameter("policyId");
		
		List<FundHistoryPO> fundHistoryList = 
				fundHistoryService.getListByCodeAndDate("2008-01-01", "2019-04-01", "zs000016");
		for(FundHistoryPO fundHistoryPO : fundHistoryList){
			String date0 = fundHistoryPO.getDate0();
			optTableService.computeBefore(user_id, policy_id, date0);
			optTableService.computeAfter(user_id, policy_id, date0);
		}
		
		return map;
	}
	
	
	@RequestMapping(value={"/optList"})
    @ResponseBody
    public List<Map<String,Object>> optList(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> listReturn = new ArrayList<Map<String,Object>>(); 
		
		String userId = "aaa";
		List<UserPolicyPO> policyList = userPolicyService.getListByUserId(userId);
//		String date0 = ToolClass.getDate();
		String date0 = "2019-04-01";
		for(UserPolicyPO userPolicyPO : policyList){
			Map<String,Object> mapRs = new HashMap<String,Object>();
			String policyId = userPolicyPO.getPolicyId();
			String policyName = userPolicyPO.getPolicyName();
			mapRs.put("policyId", policyId);
			mapRs.put("policyName", policyName);
			
			List<String> listStr = new ArrayList<String>();
			DecimalFormat df = new DecimalFormat("#.00");
			List<PolicyFundPO> fundList = policyFundService.getListByPolicyId(policyId);
			for(PolicyFundPO policyFundPO : fundList){
				OptTablePO optTablePO = 
						optTableService.getPOByUidAndPIdAndFCodeAndDate0(
								userId, 
								policyFundPO.getPolicyId(), 
								policyFundPO.getFundCode(), 
								date0);
				if(optTablePO!=null){
					String str = "基金("+policyFundPO.getFundCode()+" "+policyFundPO.getFundName()+")";
					if("buy".equals(optTablePO.getType())){
						str = str 
							+ "<span class='type-buy'>买入("+df.format(optTablePO.getDealPrice())+")</span>,"
							+ "折合("+df.format(optTablePO.getDealAccount())+")份"
							+ "当前净值("+df.format(optTablePO.getClose0())+")";
					}else if("surplus".equals(optTablePO.getType())){
						str = str
							+ "<span class='type-surplus'>存入活期余额("+df.format(optTablePO.getDealPrice())+")</span>";
					}else if("extra".equals(optTablePO.getType())){
						str = str
							+ "<span class='type-extra'>使用活期余额额外买入("+df.format(optTablePO.getDealPrice())+"</span>)"
							+ "折合("+df.format(optTablePO.getDealAccount())+")份"
							+ "当前净值("+df.format(optTablePO.getClose0())+")";;
					}else if("sell".equals(optTablePO.getType())){
						str = str
							+ "<span class='type-sell'>卖出("+df.format(optTablePO.getDealPrice())+"</span>)"
							+ "折合("+df.format(optTablePO.getDealAccount())+")份"
							+ "当前净值("+df.format(optTablePO.getClose0())+")";;
					}
					
					listStr.add(str);
				}
			}
			
			if(listStr.size()==0){
				listStr.add("无");
			}
			mapRs.put("listStr", listStr);
			mapRs.put("time", ToolClass.getTime());
			
			listReturn.add(mapRs);
		}

		listReturn.addAll(listReturn);
		return listReturn;
	}
	
	
	@RequestMapping(value={"/mainPanel"})
    @ResponseBody
    public Map<String,Object> mainPanel(HttpServletRequest request, HttpServletResponse response){
		Map<String,Object> mapReturn = new HashMap<String,Object>();

//		String userId = MemcachedUtils.getUserInfoPO(request).getId();
		String userId = "aaa";
		List<UserPolicyPO> policyList = userPolicyService.getListByUserId(userId);
		SysUserInfoPO sysUserInfoPO = sysUserInfoService.getById(userId);
		
		double nowAll = 0;
		double inputAll = 0;
		double surplus = 0;
		for(UserPolicyPO userPolicyPO : policyList){
			nowAll += userPolicyPO.getNowAll().doubleValue();
			inputAll += userPolicyPO.getInputAll().doubleValue();
			surplus += userPolicyPO.getSurplus().doubleValue();
		}
		double makeAll = nowAll-inputAll;
		double makeRate = inputAll==0?0:makeAll*100/inputAll;
		
		DecimalFormat df = new DecimalFormat("#0.00");
		mapReturn.put("userName", sysUserInfoPO.getName());
		mapReturn.put("nowAll", df.format(nowAll));
		mapReturn.put("makeAll", df.format(makeAll));
		mapReturn.put("inputAll", df.format(inputAll));
		mapReturn.put("surplus", df.format(surplus));
		mapReturn.put("makeRate", df.format(makeRate));
		
		return mapReturn;
	}
	
	@RequestMapping(value={"/getPolicyList"})
    @ResponseBody
    public List<Map<String,Object>> getPolicyList(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> listReturn = new ArrayList<Map<String,Object>>(); 

//		String userId = MemcachedUtils.getUserInfoPO(request).getId();
		String userId = "aaa";
		List<UserPolicyPO> policyList = userPolicyService.getListByUserId(userId);
		for(UserPolicyPO userPolicyPO : policyList){
			Map<String,Object> mapRs = new HashMap<String,Object>();
			String policyId = userPolicyPO.getPolicyId();
			List<PolicyFundPO> fundList = policyFundService.getListByPolicyId(policyId);
			
			mapRs.put("policy", userPolicyPO);
			mapRs.put("fundList", fundList);
			
			listReturn.add(mapRs);
		}
		
		
		return listReturn;
	}
	

}