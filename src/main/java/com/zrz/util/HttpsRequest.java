package com.zrz.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
/**
 * 利用HttpClient进行post请求的工具类
 * @ClassName: HttpClientUtil 
 * @Description: TODO
 * @author Devin <xxx> 
 * @date 2017年2月7日 下午1:43:38 
 *  
 */
public class HttpsRequest {
    @SuppressWarnings("resource")
    public static String doPost(String url,String jsonstr,String charset,String cookies){
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader(new BasicHeader("Cookie", cookies));
            StringEntity se = new StringEntity(jsonstr);
            se.setContentType("text/json");
            se.setContentEncoding(new BasicHeader("Content-Type", "application/json"));
            httpPost.setEntity(se);
            HttpResponse response = httpClient.execute(httpPost);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    
    
    public static String doGet(String url, String charset,String cookies){
        HttpClient httpClient = null;
        HttpGet httpGet = null;
        String result = null;
        try{
            httpClient = new SSLClient();
            httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/json");
            httpGet.addHeader(new BasicHeader("Cookie", cookies));
            HttpResponse response = httpClient.execute(httpGet);
            if(response != null){
                HttpEntity resEntity = response.getEntity();
                if(resEntity != null){
                    result = EntityUtils.toString(resEntity,charset);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public static void main(String[] args){ 
        String url = "https://inspur.hcmcloud.cn/api/attend.signin.create";
        String jsonStr = "{\"location_id\":1553,\"type\":3,\"latitude\":\"36.662248\",\"longitude\":\"117.129294\",\"beacon\":\"\",\"information\":\"{\"source\":\"both\",\"accuracy\":0}\",\"timestamp\":1520388146668,\"hash\":\"1a5da2d926d1a335c6297b0d430a19ba\"}";
        String cookies = "token=\"2|1:0|10:1520327342|5:token|56:ODcyZmViMDhlOTZhZTdiYWZiYzBlOWRiOGUxNmZlYjA0OWEwZmQ4Yg==|420790110033bc6f6fe195987b48d6310eac90f9ab2b87b6a87ccd0d9bc445f9\"";
        String httpOrgCreateTestRtn = HttpsRequest.doPost(url, jsonStr, "utf-8" ,cookies);
        System.out.println(httpOrgCreateTestRtn);
        
    }
}
