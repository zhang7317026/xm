package com.zrz.fund.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.service.fund.impl.ExcelPO;
import com.zrz.util.DateUtils;
import com.zrz.util.ToolClass;


/**
 * 定投，无复投，3%活期，定额周一1000，池化
 */
public class DingTouPool1 {

	public static List<ExcelPO> dingTouPool(String start, String end,
			String[] fund_codeArgs,
			int input_money,
			FundHistoryPOMapper fundHistoryPOMapper){
		
		List<ExcelPO> sheetList = new ArrayList<ExcelPO>();
    	List<String> listRs = new ArrayList<String>();
		List<FundHistoryPO> listAll_date0 = 
    			fundHistoryPOMapper.getListByCodeAndDate(start, end, fund_codeArgs[0]);
    	
		input_money = input_money/fund_codeArgs.length;
		
		StringBuffer oneline = new StringBuffer();
		oneline.append("date\t");
    	for(int k=0;k<fund_codeArgs.length;k++){
    		oneline.append("close0_"+fund_codeArgs[k]+"\t");
    		oneline.append("floatC_"+fund_codeArgs[k]+"\t");
    		oneline.append("sum_"+fund_codeArgs[k]+"\t");
    		oneline.append("input_"+fund_codeArgs[k]+"\t");
    	}
    	oneline.append("input_all\tmake_all\tsurplus");
    	listRs.add(oneline.toString());
    	
    	
    	
    	double count[] = new double[fund_codeArgs.length];
    	double sum[] = new double[fund_codeArgs.length];
    	double floatC[] = new double[fund_codeArgs.length];
    	double input[] = new double[fund_codeArgs.length];
    	double close[] = new double[fund_codeArgs.length];
    	double input_all = 0;
    	double now_all = 0;
    	
    	
    	String startDate = listAll_date0.get(0).getDate0();
    	double surplus = 0;
    	double make_all = 0;
    	String lastDate = startDate;
    	
    	
    	for(int i=0;i<listAll_date0.size();i++){
    		String date0 = listAll_date0.get(i).getDate0();
    		//判断周几
    		Calendar cal=Calendar.getInstance();
    		cal.setTime(DateUtils.formatDate(date0, "yyyy-MM-dd")); 
    		int week = cal.get(Calendar.DAY_OF_WEEK)-1;
    		
    		//每日计算
    		int lastDays = (int) ((DateUtils.formatDate(date0, "yyyy-MM-dd").getTime() 
					- DateUtils.formatDate(lastDate, "yyyy-MM-dd").getTime())/(24*60*60*1000));
			lastDate = date0;
			
			for(int k=0;k<fund_codeArgs.length;k++){
				String fund_code = fund_codeArgs[k];
				double close0 = 
						fundHistoryPOMapper.getPOByCodeAndDate(fund_code, date0).getClose0().doubleValue();
				close[k] = close0;
	    		sum[k] = count[k] * close0;
				floatC[k] = sum[k] - input[k];
			}
			
			//叠加利息
			double make_oneDay = surplus*0.03/365;
			surplus = make_oneDay*lastDays + surplus;
			make_all = make_all + make_oneDay*lastDays;
			now_all = ToolClass.sum(sum) + surplus;
			
			
    		for(int k=0;k<fund_codeArgs.length;k++){
    			String fund_code = fund_codeArgs[k];
    			//获取PO
    			FundHistoryPO fundHistoryPO = fundHistoryPOMapper.getPOByCodeAndDate(fund_code, date0);
    			float close0 = fundHistoryPO.getClose0().floatValue();
        		float year0 = fundHistoryPO.getAvg250().floatValue();
        		
        		//大于15%考虑回撤
        		if(floatC[k]/input[k]>=0.10){
    	    		//年线以下，大于15%后20日内最高点回调超过8%时全部回撤	
        			double closeMax = fundHistoryPOMapper.getMaxByDateAndDayNum(fund_code, date0, 20);
        			if(close0<=closeMax*0.92){
        				
        				surplus = surplus + sum[k];
        				
        				//切换到下一周期
        				startDate = date0;
        				count[k] = 0;
        				sum[k] = 0;
        				input[k] = 0;
        				make_all = make_all + floatC[k];
        				floatC[k] = 0;
        				
        				//listRs.add("----------年线以下，大于15%后20日内最高点回调超过5%时-全部回撤--------");

        			}
        		}
        		
	    		//周一定投,小于年线时才投
	    		if(week==1 && close0<=year0){
	    			double input_money_true = (input_all+input_money) - now_all;
	    			if(input_money_true>(1000/fund_codeArgs.length)){
	    				input_money_true = input_money * Math.pow(input_money_true/input_money,1.0/4);
	    			}else{
	    				input_money_true = input_money;
	    			}
	    			
	    			count[k] = count[k] + (input_money_true/close0);
	    			input[k] += input_money_true;
	    			input_all +=  input_money_true;
	    			now_all = now_all + input_money_true;
	    		//不投，则存活期
	    		}else if(week==1){
	    			surplus = surplus + input_money;
	    			input_all = input_all + input_money;
	    			now_all = now_all + input_money;
	    		}
	    		
	    		//复投，条件:向下偏离年线10%
	    		double deviate_standard = 0.10;
	    		if(close0<=(year0*(1-deviate_standard))){
	    			//偏移率
	    			double deviateRate = (year0-close0)/year0;
	    			//基准偏移率倍数乘以基准每份钱数(100份)
	    			double input_money_true = (surplus/100)*(deviateRate/deviate_standard);
	    			//投
	    			if(surplus>=input_money_true && input_money_true>=200){
		    			count[k] = count[k] + (input_money_true/close0);
		    			surplus = surplus - input_money_true;
		    			input[k] = input[k]+input_money_true;
	    			}
	    		}
    		}
    		
    		StringBuffer oneline_tmp = new StringBuffer();
    		oneline_tmp.append(date0+"\t");
    		for(int k=0;k<fund_codeArgs.length;k++){
    			oneline_tmp.append(""+close[k]+"\t");
    			oneline_tmp.append(""+floatC[k]+"\t");
    			oneline_tmp.append(""+sum[k]+"\t");
    			oneline_tmp.append(""+input[k]+"\t");
        	}
    		oneline_tmp.append(input_all+"\t"
    				+make_all+"\t"
    				+surplus+"\t");
    		listRs.add(oneline_tmp.toString());
        		
        	sheetList.add(new ExcelPO(date0,now_all,input_all));
    	}
    	

		//write(listRs, "D:/fund计算/"+fund_codeArgs[0]+"混合.txt");
    	
    	return sheetList;
	}
	
	private static void write(List<String> listRs ,String txtPath){
		String path = txtPath;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();

			// write
			FileWriter fw = new FileWriter(file, true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			for(String line : listRs){
				bw.write(line+"\n");
			}
			bw.flush();
			bw.close();
			fw.close();
			
			System.out.println("-------写入完成("+path+")------");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
