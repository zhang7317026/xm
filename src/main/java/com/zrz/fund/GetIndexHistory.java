package com.zrz.fund;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zrz.entity.fund.IndexHistoryPO;
import com.zrz.entity.fund.IndexListPO;
import com.zrz.service.SysParamService;
import com.zrz.service.fund.IndexHistoryService;
import com.zrz.service.fund.IndexListService;
import com.zrz.util.HttpsUtil;
import com.zrz.util.ToolClass;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class GetIndexHistory {

	private static final Logger logger = LoggerFactory.getLogger(GetIndexHistory.class);
	@Autowired
	private IndexListService indexListService;
	@Autowired
	private IndexHistoryService indexHistoryService;
	@Autowired
	private SysParamService sysParamService;
    
    private final String address = "https://androidinvest.com";
    
    private String getCookie(){
    	return sysParamService.getByCode("index_net_cookie").getParamValue();
    }
    
	public void run(int threadNum) throws IOException{
		//启动线程池
		ExecutorService exec = Executors.newFixedThreadPool(threadNum);
		//获取indexList
		List<IndexListPO> indexList = getIndexSet();
		for(final IndexListPO indexListPO : indexList){
			
			try{
				final String indexCode = indexListPO.getIndexCode();
				Callable<String> task = new Callable<String>() {
		            @Override
		            public String call() throws Exception {
		            	
		            	System.out.println("---开始("+indexCode+")---");
						getIndexHistoryList(indexCode);
						indexHistoryService.computeAvgLine(indexCode);
		                return indexCode;
		            }
		        };
		        
		        //run
		        exec.submit(task);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
//		//获取indexList
//		List<IndexListPO> indexList = getIndexSet();
//		for(final IndexListPO indexListPO : indexList){
//			try{
//				final String indexCode = indexListPO.getIndexCode();
//				getIndexHistoryList(indexCode);
//				indexHistoryService.computeAvgLine(indexCode);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
	}
	
	
	public List<IndexListPO> getIndexSet() throws IOException{
		String url = "https://androidinvest.com/IndicesTop/?index=all&year=1&value=1";
		String rsHtml = HttpsUtil.doGet(url, getCookie());
		
		Document doc = Jsoup.parse(rsHtml);
		Elements tr_Elements = doc.getElementById("table1").getElementsByTag("tbody").get(0)
				.getElementsByTag("tr");
		for(int i=0;i<tr_Elements.size();i++){
			Element tr_Element = tr_Elements.get(i);
			String indexName = tr_Element.select("small").first().text()
				+ tr_Element.select("a").first().text();
			String indexCode = tr_Element.select("a").first().attr("href");
			indexCode = indexCode.split("/")[2];
			
			IndexListPO indexListPO = new IndexListPO();
			indexListPO.setIndexCode(indexCode);
			indexListPO.setIndexName(indexName);
			indexListPO.setCreateTime(ToolClass.getTime());
			
			indexListService.save(indexListPO);
		}
		
		return indexListService.getAllList();
	}
	
	
	public void getIndexHistoryList(String indexCode) throws IOException{

		System.out.println("--开始("+indexCode+")--");
    	
		System.out.println("--PE开始--");
		try{
			//PE
			String url_PE = address+"/chinaindicespe/"+indexCode+"/";
			getIndexHistoryPE(url_PE, indexCode);
			System.out.println("--PB开始--");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			//PB
			String url_PB = address+"/chinaindicespb/"+indexCode+"/";
			getIndexHistoryPB(url_PB, indexCode);	
			System.out.println("--ROE开始--");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			//ROE
			String url_ROE = address+"/chinaindicesroe/"+indexCode+"/";
			getIndexHistoryROE(url_ROE, indexCode);
		}catch(Exception e){
			e.printStackTrace();
		}
		
    }
    
    
    public JSONObject getIndexHistoryCommon(String url, String indexCode) throws IOException {
    	
		String rsHtml = HttpsUtil.doGet(url, getCookie());
		
		Document doc = Jsoup.parse(rsHtml);
		Element scriptElement = doc.select("script[type=text/javascript]").first();
		String scriptStr =  scriptElement.data();
		
		if(StringUtils.isBlank(scriptStr)){
			return null;
		}
		scriptStr = scriptStr.substring(scriptStr.indexOf("{"), scriptStr.indexOf("function"));
		scriptStr = scriptStr.substring(0 , scriptStr.indexOf(";"));
		
		JSONObject JSONObject = net.sf.json.JSONObject.fromObject(scriptStr);
		JSONObject JSONObject_all = JSONObject.getJSONObject("all");
		
		return JSONObject_all;
    }
    
    public void getIndexHistoryPE(String url, String indexCode) throws IOException {
    	
    	JSONObject JSONObject_all = getIndexHistoryCommon(url, indexCode);
    	if(JSONObject_all == null){
    		return ;
    	}
    	
    	//common
    	JSONArray close0Array = JSONObject_all.getJSONArray("list_price");
    	JSONArray date0Array = JSONObject_all.getJSONArray("list_date");
    	//PE
		JSONArray PE_add = JSONObject_all.getJSONArray("list_val1");
		JSONArray PE_add_30 = JSONObject_all.getJSONArray("list_val1_30");
		JSONArray PE_add_70 = JSONObject_all.getJSONArray("list_val1_70");
		JSONArray PE_add_rate = JSONObject_all.getJSONArray("list_val1_p");
		
		JSONArray PE_avg = JSONObject_all.getJSONArray("list_val2");
		JSONArray PE_avg_30 = JSONObject_all.getJSONArray("list_val2_30");
		JSONArray PE_avg_70 = JSONObject_all.getJSONArray("list_val2_70");
		JSONArray PE_avg_rate = JSONObject_all.getJSONArray("list_val2_p");
		
		for(int i=0;i<date0Array.size();i++){
			IndexHistoryPO indexHistoryPONew = indexHistoryService.getPOByCodeAndDate(
					date0Array.getString(i), indexCode);
			if(indexHistoryPONew == null){
				indexHistoryPONew = new IndexHistoryPO();
			}
			
			indexHistoryPONew.setIndexCode(indexCode);
			indexHistoryPONew.setDate0(date0Array.getString(i));
			indexHistoryPONew.setCreateTime(ToolClass.getTime());
			indexHistoryPONew.setClose0(close0Array.getDouble(i));
			indexHistoryPONew.setPeAdd(ToolClass.getDouble(PE_add.getString(i)));
			indexHistoryPONew.setPeAdd30(ToolClass.getDouble(PE_add_30.getString(i)));
			indexHistoryPONew.setPeAdd70(ToolClass.getDouble(PE_add_70.getString(i)));
			indexHistoryPONew.setPeAddRate(ToolClass.getDouble(PE_add_rate.getString(i)));
			indexHistoryPONew.setPeAvg(ToolClass.getDouble(PE_avg.getString(i)));
			indexHistoryPONew.setPeAvg30(ToolClass.getDouble(PE_avg_30.getString(i)));
			indexHistoryPONew.setPeAvg70(ToolClass.getDouble(PE_avg_70.getString(i)));
			indexHistoryPONew.setPeAvgRate(ToolClass.getDouble(PE_avg_rate.getString(i)));
			
			indexHistoryService.save(indexHistoryPONew);
		}
		
		System.out.println("--更新了共计("+date0Array.size()+"条)");
    }
    
    
    public void getIndexHistoryPB(String url, String indexCode) throws IOException {
    	
    	JSONObject JSONObject_all = getIndexHistoryCommon(url, indexCode);
    	if(JSONObject_all == null){
    		return ;
    	}
    	
    	//common
    	JSONArray close0Array = JSONObject_all.getJSONArray("list_price");
    	JSONArray date0Array = JSONObject_all.getJSONArray("list_date");
    	//PB
		JSONArray PB_add = JSONObject_all.getJSONArray("list_val1");
		JSONArray PB_add_30 = JSONObject_all.getJSONArray("list_val1_30");
		JSONArray PB_add_70 = JSONObject_all.getJSONArray("list_val1_70");
		JSONArray PB_add_rate = JSONObject_all.getJSONArray("list_val1_p");
		
		JSONArray PB_avg = JSONObject_all.getJSONArray("list_val2");
		JSONArray PB_avg_30 = JSONObject_all.getJSONArray("list_val2_30");
		JSONArray PB_avg_70 = JSONObject_all.getJSONArray("list_val2_70");
		JSONArray PB_avg_rate = JSONObject_all.getJSONArray("list_val2_p");
		
		for(int i=0;i<date0Array.size();i++){
			IndexHistoryPO indexHistoryPONew = indexHistoryService.getPOByCodeAndDate(
					date0Array.getString(i), indexCode);
			if(indexHistoryPONew == null){
				indexHistoryPONew = new IndexHistoryPO();
			}
			
			indexHistoryPONew.setIndexCode(indexCode);
			indexHistoryPONew.setDate0(date0Array.getString(i));
			indexHistoryPONew.setCreateTime(ToolClass.getTime());
			indexHistoryPONew.setClose0(close0Array.getDouble(i));
			indexHistoryPONew.setPbAdd(ToolClass.getDouble(PB_add.getString(i)));
			indexHistoryPONew.setPbAdd30(ToolClass.getDouble(PB_add_30.getString(i)));
			indexHistoryPONew.setPbAdd70(ToolClass.getDouble(PB_add_70.getString(i)));
			indexHistoryPONew.setPbAddRate(ToolClass.getDouble(PB_add_rate.getString(i)));
			indexHistoryPONew.setPbAvg(ToolClass.getDouble(PB_avg.getString(i)));
			indexHistoryPONew.setPbAvg30(ToolClass.getDouble(PB_avg_30.getString(i)));
			indexHistoryPONew.setPbAvg70(ToolClass.getDouble(PB_avg_70.getString(i)));
			indexHistoryPONew.setPbAvgRate(ToolClass.getDouble(PB_avg_rate.getString(i)));
			
			indexHistoryService.save(indexHistoryPONew);
		}
		
		System.out.println("--更新了共计("+date0Array.size()+"条)");
    }
    
    
    
    
    public void getIndexHistoryROE(String url, String indexCode) throws IOException {
    	
    	JSONObject JSONObject_all = getIndexHistoryCommon(url, indexCode);
    	if(JSONObject_all == null){
    		return ;
    	}
    	
    	//common
    	JSONArray close0Array = JSONObject_all.getJSONArray("list_price");
    	JSONArray date0Array = JSONObject_all.getJSONArray("list_date");
    	//ROE
		JSONArray ROE = JSONObject_all.getJSONArray("list_val");
		JSONArray ROE_30 = JSONObject_all.getJSONArray("list_val_30");
		JSONArray ROE_70 = JSONObject_all.getJSONArray("list_val_70");
		JSONArray ROE_rate = new JSONArray();
		if(JSONObject_all.get("list_val_p")!=null){
			ROE_rate = JSONObject_all.getJSONArray("list_val_p");
		}
		
		for(int i=0;i<date0Array.size();i++){
			IndexHistoryPO indexHistoryPONew = indexHistoryService.getPOByCodeAndDate(
					date0Array.getString(i), indexCode);
			if(indexHistoryPONew == null){
				indexHistoryPONew = new IndexHistoryPO();
			}
			
			indexHistoryPONew.setIndexCode(indexCode);
			indexHistoryPONew.setDate0(date0Array.getString(i));
			indexHistoryPONew.setCreateTime(ToolClass.getTime());
			indexHistoryPONew.setClose0(close0Array.getDouble(i));
			indexHistoryPONew.setRoe(ToolClass.getDouble(ROE.getString(i)));
			indexHistoryPONew.setRoe30(ToolClass.getDouble(ROE_30.getString(i)));
			indexHistoryPONew.setRoe70(ToolClass.getDouble(ROE_70.getString(i)));
			if(ROE_rate.size()>0){
				indexHistoryPONew.setRoeAvgRate(ToolClass.getDouble(ROE_rate.getString(i)));;
			}
			
			indexHistoryService.save(indexHistoryPONew);
		}
		
		System.out.println("--更新了共计("+date0Array.size()+"条)");
    }
    
    public static void main(String[] args) throws IOException {
    	String url = "https://androidinvest.com/IndicesTop/?index=all&year=1&value=1";
		String rsHtml = HttpsUtil.doGet(url, "");
//		System.out.println(rsHtml);
		
		GetIndexHistory GetIndexHistory = new GetIndexHistory();
		GetIndexHistory.run(1);
	}
    
}
