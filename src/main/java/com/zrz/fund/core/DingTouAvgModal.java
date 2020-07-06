package com.zrz.fund.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.mapper.fund.FundHistoryPOMapper;
import com.zrz.service.fund.impl.ExcelPO;
import com.zrz.util.DateUtils;


/**
 * 定投，复投，3%活期，定额周一1000
 */
public class DingTouAvgModal {

	public static List<ExcelPO> dingTou(String start, String end,
			String fund_code,
			int input_money,
			FundHistoryPOMapper fundHistoryPOMapper){
		
		List<ExcelPO> sheetList = new ArrayList<ExcelPO>();
    	List<String> listRs = new ArrayList<String>();
		List<FundHistoryPO> listAll = 
    			fundHistoryPOMapper.getListByCodeAndDate(start, end, fund_code);
    	
    	listRs.add("date\tavgInputRate\tclose0\tyear0\tback\tsum\tfloatC\tinput\trate\tyearRate\tall\tinput_all\tmake_all\tsurplus");
    	double count = 0;
    	double sum = 0;
    	double floatC = 0;
    	double input = 0;
    	double back = 0;
    	double input_all = 0;
    	double now_all = 0;
    	
    	
    	String startDate = listAll.get(0).getDate0();
    	double yearRate = 0;
    	double avgInput = 0;
    	double surplus = 0;
    	double make_all = 0;
    	String lastDate = startDate;
    	int allDays = 0;
    	double avgInputRate = 0;
    	

    	for(int i=0;i<listAll.size();i++){
    		FundHistoryPO fundHistoryPO = listAll.get(i);
    		String date0 = fundHistoryPO.getDate0();
    		float close0 = fundHistoryPO.getClose0().floatValue();
    		float year0 = fundHistoryPO.getAvg250().floatValue();
    		//判断周几
    		Calendar cal=Calendar.getInstance();
    		cal.setTime(DateUtils.formatDate(date0, "yyyy-MM-dd")); 
    		int week=cal.get(Calendar.DAY_OF_WEEK)-1;
    		
    		//每日计算
    		sum = count * close0;
			floatC = sum - input;
			int lastDays = (int) ((DateUtils.formatDate(date0, "yyyy-MM-dd").getTime() 
					- DateUtils.formatDate(lastDate, "yyyy-MM-dd").getTime())/(24*60*60*1000));
			allDays = allDays + lastDays;
			avgInput += lastDays*input;
			lastDate = date0;
			//叠加利息
			double make_oneDay = surplus*0.03/365;
			surplus = make_oneDay*lastDays + surplus;
			make_all = make_all + make_oneDay*lastDays;
			now_all = sum + surplus;
			
    		//大于15%考虑回撤
    		if(floatC/input>=0.10){
	    		//年线以下，大于15%后20日内最高点回调超过8%时全部回撤	
    			double closeMax = fundHistoryPOMapper.getMaxByDateAndDayNum(fund_code, date0, 20);
    			if(close0<=closeMax*0.92){
    				
    				//计算年化
    				Date date_date0 = DateUtils.formatDate(date0, "yyyy-MM-dd");
    				Date date_startDate = DateUtils.formatDate(startDate, "yyyy-MM-dd");
    				int days = (int)((date_date0.getTime()-date_startDate.getTime())/(24*60*60*1000));
    				avgInput = avgInput/days;
    				yearRate = (floatC/avgInput)*100.0/(days*1.0/365);
    				double rate = input==0?0:(floatC*100/input);
    				//back
    				surplus = surplus + sum;
    				
//    				listRs.add(
//    						startDate+"\t"
//    	    				+date0+"\t"
//    	    				+close0+"\t"
//    	    				+year0+"\t"
//    	    				+back+"\t"
//    	    				+sum+"\t"
//    	    				+floatC+"\t"
//    	    				+input+"\t"
//    	    				+rate+"%\t"
//    	    				+yearRate+"%\t"
//    	    				+(back+sum)+"\t"
//    	    				+input_all+"\t"
//    	    				+make_all+"\t"
//    	    				+surplus);
    				
    				//切换到下一周期
    				startDate = date0;
    				back = back + sum;
    				count = 0;
    				sum = 0;
    				input = 0;
    				make_all = make_all + floatC;
    				floatC = 0;
    				
    				//listRs.add("----------年线以下，大于15%后20日内最高点回调超过5%时-全部回撤--------");

    			}
    		}
    		
    		
			
    		//周一定投,小于年线时才投
    		if(week==1 && close0<=year0){
    			double input_money_true = (input_all+input_money) - now_all;
    			if(input_money_true>1000){
    				input_money_true = input_money * Math.pow(input_money_true/input_money,1.0/4);
    			}else{
    				input_money_true = input_money;
    			}
    			
    			count = count + (input_money_true/close0);
    			input += input_money_true;
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
	    			count = count + (input_money_true/close0);
	    			surplus = surplus - input_money_true;
	    			input = input + input_money_true;
    			}
    		}
    		
    		double rate = input==0?0:(floatC*100/input);
    		if(now_all>0){
    			avgInputRate = 1-surplus/now_all;
    		}
    		
    		listRs.add(
				date0+"\t"
				+avgInputRate+"\t"
				+close0+"\t"
				+year0+"\t"
				+back+"\t"
				+sum+"\t"
				+floatC+"\t"
				+input+"\t"
				+rate+"%\t"
				+yearRate+"%\t"
				+(back+sum)+"\t"
				+input_all+"\t"
				+make_all+"\t"
				+surplus+"\t");
    		
    		
    		sheetList.add(new ExcelPO(date0,now_all,input_all));
    	}
    	
    	
		write(listRs, "D:/fund计算/"+fund_code+".txt");
    	
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
