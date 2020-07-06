package com.zrz.util.api;

import java.util.HashMap;
import java.util.Map;


import com.google.gson.Gson;


public class IP_API {

	/** 
     * 调用获取城市列表接口,返回所有数据 
     * @return 返回接口数据 
     */  
    public static String excute(String IP){  
        String url="http://ip.taobao.com/service/getIpInfo.php?ip="+IP;//接口URL  
        //APIUtil是一个封装了get和post方法获取网络请求数据的工具类  
        return APIUtil.get(url);//使用get方法  
    }  
    /** 
     * 调用接口返回数据后,解析数据,返回Map
     * @param IP
     * @return Map
     */  
    public static Map<String,Object> getMap(String IP) { 
    	Map<String,Object> map = new HashMap<String,Object>();
    	if(IP.startsWith("127.")||IP.startsWith("168.")||IP.startsWith("0:0:0:0:0:0:0:1")){
    		map.put("code", "0");
    		APIData data = new APIData();
    		//本地初始化
    		data.setCountry("本地");
    		data.setCountry_id("CN");
    		data.setArea("本地");
    		data.setArea_id("000000");
    		data.setRegion("本地");
    		data.setRegion_id("000000");
    		data.setCity("本地");
    		data.setCity_id("000000");
    		data.setCounty("本地");
    		data.setCounty_id("000000");
    		data.setIsp("本地");
    		data.setIsp_id("000000");
    		data.setIp(IP);
    		
    		map.put("data", data);
    	}else{
	        String result = excute(IP);//返回接口结果,得到json格式数据  
	        if(result!=null){ 
	        	Gson gson=new Gson();
			    T t = gson.fromJson(result, T.class);
			    map.put("code", t.getCode());
			    
			    APIData data = t.getData();
			    map.put("data", data);
	        }  
    	}
        return map;  
    }  
    
    
    class T{

    	public T(){}
    	private String code = "";
    	private APIData data;
    	
    	public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public APIData getData() {
			return data;
		}
		public void setData(APIData data) {
			this.data = data;
		}
    	
    }
    
   

    
    
//    public static void main(String[] args) {  
//    	Map map = getMap("114.243.161.55");
//    	System.out.println((String)map.get("code"));
//    	System.out.println(((APIData)(map.get("data"))).getArea());
//    } 
    
}
