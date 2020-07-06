package com.zrz.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

public class ToolClass {

	/**
     * isNotEmpty
     * 等价于StringUtils.isNotEmpty
     * @param value
     * @return boolean
     */
    public static boolean isNotEmpty(String value)
    {
    	return null != value && !"".equals(value);
    }
    
    /**
     * 判断是否存在汉字
     * @param string
     * @return boolean
     */
    public static boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            temp =  true;
        }
        return temp;
    }
    
    /**
     * 获取字符串的汉语拼音
     * @param string
     * @return string
     */
    public static String getPinYin(String str){
    	return PinYin.getPinYin(str);
    }
    
    /**
     * 获取当前时间 
     * @return string
     */
    public static String getTime(){
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time=format.format(date);
    	return time;
    }
    
    /**
     * 获取当前日期
     * @return string
     */
    public static String getDate(){
    	Date date=new Date();
    	DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    	String time=format.format(date);
    	return time;
    }
    
    /**
     * 获取当前时间Long 
     * @return Long
     */
    public static Long getTimeLong(){
    	Date date=new Date();
    	return date.getTime();
    }
    
    /**
     * 字符串转数字
     */
    public static int getInt(String str){
    	if(StringUtils.isBlank(str)){
    		return 0;
    	}else{
    		return Integer.parseInt(str);
    	}
    }
    
    /**
     * 字符串转大数字
     */
    public static BigDecimal getDecimal(String str){
    	if(StringUtils.isBlank(str)){
    		return new BigDecimal("0");
    	}else{
    		return new BigDecimal(str);
    	}
    }
    
    /**
	 * 获取访问真实IP
	 */
	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * 获取访问真实IP
	 */
	public static String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ToolClass.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (ToolClass.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}
	
	/**
	 * 两个string异或 
	 */
	public static String twoStringXor(String str1, String str2) {
		String result = "";
        byte strb1[] = str1.getBytes();
        byte strb2[] = str2.getBytes();
        byte strb3[] = new byte[strb1.length];
        for(int i=0;i<strb1.length;i++){
        	 int aa = strb1[i] ^ strb2[i];
        	 strb3[i] = (byte) aa;
        }
        result = new String(strb3);
        return result;
    }
	
	/**
	 * 判断string中是否含有非法字符
	 */
	public static boolean isConstantSQL(String str){
		boolean is = false;
		String[] args = {";","'","*","delete","drop","truncate","or","and","select","--"};
		String[] extend = {"store"};
		for(String a : args){
			if(str.toLowerCase().contains(a)){
				for(String e : extend){
					if(e.equals(str)){
						break;
					}else{
						is = true;
						break;
					}
				}
			}
		}
		return is;
	}
	
	
	/**
	 * string转double
	 */
	public static double getDouble(String str){
		if(StringUtils.isBlank(str)){
			return 0;
		}else{
			try{
				return Double.parseDouble(str);
			}catch(Exception e){
				return 0;
			}
		}
	}
	
	/**
	 * double类型数组取和
	 */
	public static double sum(double[] sumArgs){
		double sumAll = 0.0;
		for(double one : sumArgs){
			sumAll += one;
		}
		return sumAll;
	}
	
	/**
	 * float类型数组取和
	 */
	public static float sum(float[] sumArgs){
		float sumAll = 0;
		for(float one : sumArgs){
			sumAll += one;
		}
		return sumAll;
	}
	
	/**
	 * int类型数组取和
	 */
	public static int sum(int[] sumArgs){
		int sumAll = 0;
		for(int one : sumArgs){
			sumAll += one;
		}
		return sumAll;
	}
	
	
	/**
	 * elements为要操作的数据集合，即长度为M的容器，num为每次取的元素个数 
	 */
    public static List<List<String>> findsort(List<String> elements, int num){
            List<List<String>> result = new ArrayList<List<String>>();
        //将elements中的数据取出来，存到新的list中，为后续计算做准备
        for (int i = 0; i < elements.size(); i++) {
            List<String> list = new ArrayList<String>();
            list.add(elements.get(i));
            result.add(list);
        }
        return combiner(elements, num, result);     
    }
    //elements为要操作的数据集合，即长度为M的容器，num为每次取的元素个数
    private static List<List<String>> combiner(List<String> elements, int num,
    List<List<String>> result){
        //当num为1时，即返回结果集
        if(num == 1){
            return result;
        }
        //result的长度是变化的，故把原始值赋给变量leng
        int leng = result.size();
        //循环遍历，将 elements每两个元素放到一起，作为result中的一个元素
        for (int i = 0; i < leng; i++) {
            for (int j = 0; j < elements.size(); j++) {
                if(!result.get(i).contains(elements.get(j))){
                    List<String> list1 = new ArrayList<String>();
                    for (int j2 = 0; j2 < result.get(i).size(); j2++) {
                        list1.add(result.get(i).get(j2));
                    }
                    list1.add(elements.get(j));
                    Collections.sort(list1);
                    result.add(list1);
                }   
            }           
        } 
        //将result中的循环遍历前的数据删除
        for (int i = 0; i < leng; i++) {
            result.remove(0);
        }
        //对result进行去重
        Iterator<List<String>> it=result.iterator();  
        List<List<String>> listTemp= new ArrayList<List<String>>();  
        while(it.hasNext()){  
            List<String> a=it.next();  
            if (listTemp.contains(a)){  
                it.remove();  
            }else {  
                listTemp.add(a);  
            }  
        } 
        //递归计算，根据num的值来确定递归次数
        combiner(elements, num - 1, result);
        return result;   
    }
    
    
    /**
     * map按照value排序
     */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    
    /**
     * 获取两个时间的日期差值
     */
    public static long getDiffDays(String last, String now, String formatStr){
		if(StringUtils.isBlank(last)){
			return 0;
		}
		long diffDays = 0;
        SimpleDateFormat format = new SimpleDateFormat(formatStr); 
        
        try {
			Date beginDate = format.parse(last);
			Date endDate= format.parse(now);    
			diffDays = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000); 
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return diffDays;
	}
    
    /**
     * 移动日期，每次往后平行移动一次
     */
    public static double[] moveDays(double[] days){
		for(int i=days.length-1;i>-1;i--){
			if(i==days.length-1){
				days[i] = days[i]+days[i-1];
			}else if(i==0){
				days[0] = 0;
			}else{
				days[i] = days[i-1];
			}
		}
		
		return days;
	}
    
}
