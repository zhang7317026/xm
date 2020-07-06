package com.zrz.fund;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zrz.entity.fund.FundHistoryPO;
import com.zrz.entity.fund.FundListPO;
import com.zrz.service.fund.FundHistoryService;
import com.zrz.service.fund.FundListService;
import com.zrz.util.ToolClass;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class GetFundHistory {
	
	private static final Logger logger = LoggerFactory.getLogger(GetFundHistory.class);
	@Autowired
	private FundListService fundListService;
	@Autowired
	private FundHistoryService fundHistoryService;
	//静态初使化当前类
    public static GetFundHistory GetFundHistory;
    
    private final int threadNum = 10;
    
    @PostConstruct
    public void init() {
    	GetFundHistory = this;
    }
	
	public void run(){
		//启动线程池
		ExecutorService exec = Executors.newFixedThreadPool(threadNum);
		//获取fundSet
		Set<String> fundSet = getFundSet();
		for(final String fundCode : fundSet){
			
			try{
				
				Callable<String> task = new Callable<String>() {
		            @Override
		            public String call() throws Exception {
		            	
		            	System.out.println("---开始("+fundCode+")---");
						getFundDetail(fundCode);
						getFundHistoryList(fundCode);
						GetFundHistory.fundHistoryService.computeAvgLine(fundCode);
		                return fundCode;
		            }
		        };
		        
		        //run
		        exec.submit(task);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public Set<String> getFundSet(){
		Set<String> rsSet = new TreeSet<String>();
		
		for(int pageIndex=1;;pageIndex++){
			String url = "http://fund.eastmoney.com/data/FundGuideapi.aspx?"
					+ "dt=0"
					+ "&ft=zs"
					+ "&sd="
					+ "&ed="
					+ "&sc=z"
					+ "&st=desc"
					+ "&pi="+pageIndex
					+ "&pn=20"
					+ "&zf=diy"
					+ "&sh=list"
					+ "&rnd=0.5231154864263476";
			String rs = sendGet(
					url, 
					"", 
					"UTF-8", 
					5000);
			
			rs = rs.substring(rs.indexOf("{"), rs.length());
	//		System.out.println(rs);
			
			JSONObject JSONObject = net.sf.json.JSONObject.fromObject(rs);
			JSONArray datasArray = JSONObject.getJSONArray("datas");
			for(int j=0;j<datasArray.size();j++){
				String oneStr = datasArray.getString(j);
				String fundCode = oneStr.split(",")[0];
				
				//添加到set中返回
				rsSet.add(fundCode);
			}
			
			
			int allPages = JSONObject.getInt("allPages");
			if(pageIndex==allPages){
				break;
			}
		}
		
		return rsSet;
	}
	
	
	public void getFundDetail(String fundCode){
		String url = "http://fund.eastmoney.com/"+fundCode+".html";
		try {
			Document doc = Jsoup.connect(url).timeout(5000).get();
			//fundName
			String fundName = "";
			try{
				fundName = doc.select("div[class=fundDetail-tit]").select("div").get(0).text();
				fundName = fundName.substring(0, fundName.indexOf("("));
			}catch(Exception e){
				System.out.println("!!!错误fundName("+fundName+")");
				fundName = "错误";
			}
			//scale
			Elements tds = doc.select("div[class=infoOfFund]").select("td");
			String scaleStr = tds.get(1).text();
			scaleStr = scaleStr.substring(scaleStr.indexOf("：")+1, scaleStr.indexOf("亿元"));
			//errorRanger
			String errorRangerStr =  doc.select("td[class=specialData]").text();
			
			try{
				errorRangerStr = errorRangerStr.substring(
						errorRangerStr.lastIndexOf("：")+1, 
						errorRangerStr.lastIndexOf("%"));
			}catch(Exception e){
				System.out.println("!!!错误errorRangerStr("+errorRangerStr+")");
				errorRangerStr = "";
			}
			
			FundListPO fundListPO = new FundListPO();
			fundListPO.setFundCode(fundCode);
			fundListPO.setFundName(fundName);
			fundListPO.setCreateTime(ToolClass.getTime());
			fundListPO.setScale(ToolClass.getDouble(scaleStr));
			fundListPO.setErrorRanger(ToolClass.getDouble(errorRangerStr));
			
			GetFundHistory.fundListService.save(fundListPO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

	public void getFundHistoryList(String fundCode){
		
		int pageSize = 200;
		
		for(int pageIndex=1;;pageIndex++){
			String url = "http://api.fund.eastmoney.com/f10/lsjz?"
					+ "callback=jQuery18309481235316076648_1552353899465"
					+ "&fundCode="+fundCode
					+ "&pageIndex="+pageIndex
					+ "&pageSize="+pageSize
					+ "&startDate="
					+ "&endDate="
					+ "&_="+(new Date().getTime());
			
			String rs = sendGet(
					url, 
					"", 
					"UTF-8", 
					5000);
			rs = rs.substring(rs.indexOf("(")+1, rs.length()-1);
	//		System.out.println(rs);
			
			JSONObject JSONObject = net.sf.json.JSONObject.fromObject(rs);
			
			if(JSONObject.getInt("ErrCode")!=0){
				System.out.println("--返回结果错误--");
				break;
			}else{
				int totalCount = JSONObject.getInt("TotalCount");
				JSONArray list = JSONObject.getJSONObject("Data").getJSONArray("LSJZList");
				for(int i=0;i<list.size();i++){
					JSONObject JSONObjectOne = list.getJSONObject(i);
					String date = JSONObjectOne.getString("FSRQ");
					String value = JSONObjectOne.getString("LJJZ");
					
					
					FundHistoryPO fundHistoryPO = new FundHistoryPO();
					fundHistoryPO.setFundCode(fundCode);
					fundHistoryPO.setCreateTime(ToolClass.getTime());
					fundHistoryPO.setClose0(ToolClass.getDouble(value));
					fundHistoryPO.setDate0(date);
					
					GetFundHistory.fundHistoryService.save(fundHistoryPO);
					System.out.println("--("+fundCode+")成功保存--("+list.size()+")条");
				}
				
				if(pageSize*pageIndex>=totalCount){
					break;
				}
			}
			
		}
	}
	
	
	
	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param ,String codeType ,int outTime) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            //设置超时时间
            connection.setReadTimeout(outTime);
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            connection.setRequestProperty("Referer", "http://fundf10.eastmoney.com/jjjz_002900.html");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
//                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),codeType));//设置编码
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
            //异常后返回TIMEOUT
            return "TIMEOUT";
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }
    
    
}
