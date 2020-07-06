package com.zrz.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zrz.entity.fund.FundListPO;
import com.zrz.fund.GetFundHistory;
import com.zrz.fund.GetIndexHistory;
import com.zrz.fund.Index;
import com.zrz.service.fund.FundHistoryService;
import com.zrz.service.fund.FundListService;
import com.zrz.service.fund.SimulationService;
 
@Controller
@RequestMapping(value="/test")
public class TestController {
 
	@Autowired
	private FundListService fundListService;
	@Autowired
	private FundHistoryService fundHistoryService;
	@Autowired
	private SimulationService simulationService;
	@Autowired
	GetIndexHistory GetIndexHistory;
	
    @RequestMapping(value={"","/"})
    public ModelAndView home(HttpServletRequest request, HttpServletResponse response){
    	
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/main/main");
        
        return mav;
    }
    
    
    @RequestMapping(value={"/runFund"})
    @ResponseBody
    public Map<String, Object> runFund(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		GetFundHistory GetFundHistory = new GetFundHistory();
		GetFundHistory.run();
		
		return map;
	}
    
    
    @RequestMapping(value={"/runIndex"})
    @ResponseBody
    public Map<String, Object> runIndex(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		try {
			String threadNumStr = request.getParameter("n");
			int threadNum = 2;
			if(StringUtils.isNotBlank(threadNumStr)){
				threadNum = Integer.parseInt(threadNumStr);
			}
			GetIndexHistory.run(threadNum);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
    
    
    @RequestMapping(value={"/computeYearLine"})
    @ResponseBody
    public Map<String, Object> computeYearLine(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		List<FundListPO> fundlist = fundListService.getAllList();
		for(FundListPO fundPO : fundlist){
			System.out.println("--"+fundPO.getFundCode()+"--");
			fundHistoryService.computeAvgLine(fundPO.getFundCode());
		}
		
		return map;
	}
    
	@RequestMapping(value={"/simulation"})
    @ResponseBody
    public Map<String, Object> simulation(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		simulationService.simulation();
		
		return map;
	}
	
	@RequestMapping(value={"/compute"})
    @ResponseBody
    public Map<String, Object> compute(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>(); 

		simulationService.compute();
		
		return map;
	}
	
	
	@RequestMapping(value={"/line"})
    public ModelAndView line(HttpServletRequest request, HttpServletResponse response){
    	
        ModelAndView mav=new ModelAndView();
        mav.setViewName("/index/line");
        
        return mav;
    }
	

}