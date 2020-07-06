package com.zrz.fund;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
public class Index {
	
	private static final Logger logger = LoggerFactory.getLogger(Index.class);
	@Autowired
	private FundListService fundListService;
	@Autowired
	private FundHistoryService fundHistoryService;
	//静态初使化当前类
    public static Index Fund;
    
    @PostConstruct
    public void init() {
    	Fund = this;
    }
	
	public void run(){
		
		String[] fundIdArray = {
				"0000011","0000021","0000031","0000161","0003001","3990012",
				"3990022","3990032","3990052","3990062","3991062","3995502"};
		
		for(String fundId : fundIdArray){
			try{
				System.out.println("fundId("+fundId+")");
				//获取内容
				getFundDetail(fundId);
				//计算年线
				String fundCode = "zs"+fundId.substring(0,fundId.length()-1);
				computeYearLine(fundCode);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	public static void getFundDetail(String fundId){
		String url = "http://pdfm.eastmoney.com/EM_UBG_PDTI_Fast/api/js?"
				+ "rtntype=5"
				+ "&token=4f1862fc3b5e77c150a2b985b12db0fd"
				+ "&cb=jQuery18303092460537014976_1553130779004"
				+ "&id="+fundId
				+ "&type=k"
				+ "&authorityType="
				+ "&_="+(new Date().getTime());
		System.out.println(url);
		String rs = sendGet(
				url, 
				"", 
				"UTF-8", 
				5000);
		rs = rs.substring(rs.indexOf("(")+1, rs.lastIndexOf(")"));
		try {

			JSONObject JsonObject = net.sf.json.JSONObject.fromObject(rs);
			String fundCode = "zs"+JsonObject.getString("code");
			String fundName = JsonObject.getString("name");
			
			//FundList
			FundListPO fundListPO = new FundListPO();
			fundListPO.setFundCode(fundCode);
			fundListPO.setFundName(fundName);
			fundListPO.setCreateTime(ToolClass.getTime());
			Fund.fundListService.save(fundListPO);
			//FundHistory
			JSONArray JsonArray = JsonObject.getJSONArray("data"); 
			for(int i=0;i<JsonArray.size();i++){
				String one = JsonArray.getString(i);
				String[] oneArray = one.split(",");
				String date0 = oneArray[0];
				String close0 = oneArray[2];
				
				FundHistoryPO fundHistoryPO = new FundHistoryPO();
				fundHistoryPO.setFundCode(fundCode);
				fundHistoryPO.setCreateTime(ToolClass.getTime());
				fundHistoryPO.setDate0(date0);
				fundHistoryPO.setClose0(ToolClass.getDouble(close0));
				
				Fund.fundHistoryService.save(fundHistoryPO);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void computeYearLine(String fundCode){
		
		Fund.fundHistoryService.computeYearLine(fundCode);
		
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
