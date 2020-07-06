package com.zrz.service.fund.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;

import com.zrz.fund.core.DingTouFuTou1;
import com.zrz.fund.core.DingTouPool1;
import com.zrz.fund.core.DingTouWuFuTou1;
import com.zrz.fund.core.DingTouWuFuTou2;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.service.fund.SimulationService;
import com.zrz.util.DateUtils;
import com.zrz.util.ExportExcelUtil;
import com.zrz.util.ToolClass;

 
 
 
@Service
public class SimulationServiceImpl implements SimulationService{
 
    @Autowired
    private FundHistoryPOMapper fundHistoryPOMapper;
    
    /**
     * 模拟器
     */
    public Map<String,Object> simulation(){
    	Map<String,Object> mapReturn = new HashMap<String,Object>();
    	String start = "2008-01-01";
    	String end = "2900-01-01";
    	String locationUrl = "D:/fund计算/000016_000300_3%复投(类1).xlsx";
    	
    	String[] fund_codeArgs = {
    			"zs000001","zs000002","zs000003","zs000016","zs000300","zs399001",
    			"zs399002","zs399003","zs399005","zs399006","zs399106","zs399550"};
//    			"zs000001"};
    	int input_money = 500;
    	
    	List<Map<String,Object>> excelList = new ArrayList<Map<String,Object>>();
		for(String fund_code : fund_codeArgs){
			Map<String,Object> sheetMap = new HashMap<String,Object>(); 
			
			String sheetName = fund_code+" 3%复投";
			/**
			 * 核心
			 */
			//定投，无复投，3%活期，定额周一1000
//			List<ExcelPO> sheetList = 
//					DingTouWuFuTou1.dingTou(start, end, fund_code, input_money, fundHistoryPOMapper);
			//定投，无复投，3%活期，周一补差价保总价(标准:1000)
//			List<ExcelPO> sheetList = 
//					DingTouWuFuTou2.dingTou(start, end, fund_code, input_money, fundHistoryPOMapper);
//			//定投有复投，活期复利3%
//			List<ExcelPO> sheetList = 
//					DingTouFuTou1.dingTou(start, end, fund_code, input_money, fundHistoryPOMapper);
			//组合定投有复投，活期复利3%
			String[] fund_codeArgs_new = {"zs000016","zs000300"};
			List<ExcelPO> sheetList = 
					DingTouPool1.dingTouPool(start, end, fund_codeArgs_new, input_money, fundHistoryPOMapper);
			
			//增加年化说明
			Date date_date0 = DateUtils.formatDate(sheetList.get(sheetList.size()-1).getDate0(), "yyyy-MM-dd");
			Date date_startDate = DateUtils.formatDate(sheetList.get(0).getDate0(), "yyyy-MM-dd");
			int days = (int)((date_date0.getTime()-date_startDate.getTime())/(24*60*60*1000));
			ExcelPO lastPO = sheetList.get(sheetList.size()-1);
			double avgYearRate = (lastPO.getNow_all()/lastPO.getInput_all()-1)*100/(days*1.0/365);
			DecimalFormat df = new DecimalFormat("######0.00");   
			//表头
			String[] sheetHeaders = { "date0", "now_all", "input_all", "平均"+df.format(avgYearRate)+"%"};
			
			
			sheetMap.put("sheetName", sheetName);
			sheetMap.put("sheetHeaders", sheetHeaders);
			sheetMap.put("sheetList", sheetList);
			excelList.add(sheetMap);
		}
		

		ExportExcelUtil<ExcelPO> util = new ExportExcelUtil<ExcelPO>();
		util.exportExcel(excelList, locationUrl);
    	
    	
		mapReturn.put("flag", "success");
    	return mapReturn;
    }
    
    /**
     * 计算器
     */
    public Map<String,Object> compute(){
    	Map<String,Object> mapReturn = new HashMap<String,Object>();
    	String start = "2008-01-01";
    	String end = "2019-01-01";
    	String locationUrl = "D:/fund计算/000016_000300_3%复投(类1).xlsx";
    	
    	String[] fund_codeArgs = {
    			"zs000001","zs000002","zs000003","zs000016","zs000300","zs399001",
    			"zs399002","zs399003","zs399005","zs399106"};
    	int input_money = 500;
    	List<List<String>> randomList = ToolClass.findsort(
    			Arrays.asList(fund_codeArgs), 4);
    	
    	Map<String, Double> listRs = new TreeMap<String, Double>();
    	List<Map<String,Object>> excelList = new ArrayList<Map<String,Object>>();
    	
    	System.out.println("--共("+randomList.size()+")--");
		for(int i=0;i<randomList.size();i++){
			List<String> fundCode_list = randomList.get(i);
			Map<String,Object> sheetMap = new HashMap<String,Object>(); 
			
			String sheetName = "";
			for(String fundCode : fundCode_list){
				sheetName += fundCode+"_";
			}
			sheetName = sheetName.substring(0,sheetName.length()-1);
			
			System.out.print("("+(i+1)+")----"+sheetName+"----");
			
			/**
			 * 核心
			 */
			//组合定投有复投，活期复利3%
			String[] fund_codeArgs_new = new String[fundCode_list.size()];
			fundCode_list.toArray(fund_codeArgs_new);
			List<ExcelPO> sheetList = 
					DingTouPool1.dingTouPool(start, end, fund_codeArgs_new, input_money, fundHistoryPOMapper);
			
			//增加年化说明
			Date date_date0 = DateUtils.formatDate(sheetList.get(sheetList.size()-1).getDate0(), "yyyy-MM-dd");
			Date date_startDate = DateUtils.formatDate(sheetList.get(0).getDate0(), "yyyy-MM-dd");
			int days = (int)((date_date0.getTime()-date_startDate.getTime())/(24*60*60*1000));
			ExcelPO lastPO = sheetList.get(sheetList.size()-1);
			double avgYearRate = (lastPO.getNow_all()/lastPO.getInput_all()-1)*100/(days*1.0/365);
			DecimalFormat df = new DecimalFormat("######0.00");   
			//表头
			String[] sheetHeaders = { "date0", "now_all", "input_all", "平均"+df.format(avgYearRate)+"%"};
			
			System.out.println("("+df.format(avgYearRate)+"%)");
			
			sheetMap.put("sheetName", sheetName);
			sheetMap.put("sheetHeaders", sheetHeaders);
			sheetMap.put("sheetList", sheetList);
			excelList.add(sheetMap);
			
			listRs.put(sheetName, avgYearRate);
		}
		
		listRs = ToolClass.sortByValue(listRs);
		for (Map.Entry<String, Double> entry : listRs.entrySet()) { 
			System.out.println("fund("+entry.getKey()+"),value("+entry.getValue()+")");
		}
		

//		ExportExcelUtil<ExcelPO> util = new ExportExcelUtil<ExcelPO>();
//		util.exportExcel(excelList, locationUrl);
    	
    	
		mapReturn.put("flag", "success");
    	return mapReturn;
    }
    
}