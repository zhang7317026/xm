package com.zrz.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.zrz.fund.GetFundHistory;

@Component
public class Test {
	
	@Scheduled(cron="0 0 1 * * ?")
	public void quartz() {
		SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm:ss");  
        System.out.println("-----开始执行runFund("+sdf.format(new Date())+")");
        
        GetFundHistory GetFundHistory = new GetFundHistory();
		GetFundHistory.run();
	}
}
