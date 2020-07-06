package com.zrz.util;  
  
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whalin.MemCached.MemCachedClient;
import com.zrz.entity.SysUserInfoPO;  
  
/** 
 *  
 * @ClassName: MemcachedUtils 
 * @Description: Memcached工具类 
 * @author zhangrz 
 *  
 */  
public class MemcachedUtils {  
	private static final Logger logger = LoggerFactory.getLogger(MemcachedUtils.class);
    private static MemCachedClient cachedClient;  
    static {  
        if (cachedClient == null)  
            cachedClient = new MemCachedClient();  
    }  
  
    private MemcachedUtils() {  
    }  
  
    /** 
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @return 
     */  
    public static boolean set(String key, Object value) {  
        return setExp(key, value, null);  
    }  
  
    /** 
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    public static boolean set(String key, Object value, Date expire) {  
        return setExp(key, value, expire);  
    }  
  
    /** 
     * 向缓存添加新的键值对。如果键已经存在，则之前的值将被替换。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    private static boolean setExp(String key, Object value, Date expire) {  
        boolean flag = false;  
        try {  
            flag = cachedClient.set(key, value, expire);  
        } catch (Exception e) {  
            // 记录Memcached日志  
        	logger.error("Memcached set方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
        }  
        return flag;  
    }  
  
    /** 
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @return 
     */  
    public static boolean add(String key, Object value) {  
        return addExp(key, value, null);  
    }  
  
    /** 
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    public static boolean add(String key, Object value, Date expire) {  
        return addExp(key, value, expire);  
    }  
  
    /** 
     * 仅当缓存中不存在键时，add 命令才会向缓存中添加一个键值对。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    private static boolean addExp(String key, Object value, Date expire) {  
        boolean flag = false;  
        try {  
            flag = cachedClient.add(key, value, expire);  
        } catch (Exception e) {  
            // 记录Memcached日志  
            logger.error("Memcached add方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
        }  
        return flag;  
    }  
  
    /** 
     * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @return 
     */  
    public static boolean replace(String key, Object value) {  
        return replaceExp(key, value, null);  
    }  
  
    /** 
     * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    public static boolean replace(String key, Object value, Date expire) {  
        return replaceExp(key, value, expire);  
    }  
  
    /** 
     * 仅当键已经存在时，replace 命令才会替换缓存中的键。 
     *  
     * @param key 
     *            键 
     * @param value 
     *            值 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    private static boolean replaceExp(String key, Object value, Date expire) {  
        boolean flag = false;  
        try {  
            flag = cachedClient.replace(key, value, expire);  
        } catch (Exception e) {  
            logger.error("Memcached replace方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
        }  
        return flag;  
    }  
  
    /** 
     * get 命令用于检索与之前添加的键值对相关的值。 
     *  
     * @param key 
     *            键 
     * @return 
     */  
    public static Object get(String key) {  
        Object obj = null;  
        try {  
            obj = cachedClient.get(key);  
        } catch (Exception e) {  
            logger.error("Memcached get方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
        }  
        return obj;  
    }  
  
    /** 
     * 删除 memcached 中的任何现有值。 
     *  
     * @param key 
     *            键 
     * @return 
     */  
    public static boolean delete(String key) {  
        return deleteExp(key, null);  
    }  
  
    /** 
     * 删除 memcached 中的任何现有值。 
     *  
     * @param key 
     *            键 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    public static boolean delete(String key, Date expire) {  
        return deleteExp(key, expire);  
    }  
  
    /** 
     * 删除 memcached 中的任何现有值。 
     *  
     * @param key 
     *            键 
     * @param expire 
     *            过期时间 New Date(1000*10)：十秒后过期 
     * @return 
     */  
    private static boolean deleteExp(String key, Date expire) {  
        boolean flag = false;  
        try {  
            flag = cachedClient.delete(key, expire);  
        } catch (Exception e) {  
            logger.error("Memcached delete方法报错，key值：" + key + "\r\n" + exceptionWrite(e));  
        }  
        return flag;  
    }  
  
    /** 
     * 清理缓存中的所有键/值对 
     *  
     * @return 
     */  
    public static boolean flashAll() {  
        boolean flag = false;  
        try {  
            flag = cachedClient.flushAll();  
        } catch (Exception e) {  
            logger.error("Memcached flashAll方法报错\r\n" + exceptionWrite(e));  
        }  
        return flag;  
    }  
  
    /** 
     * 返回异常栈信息，String类型 
     *  
     * @param e 
     * @return 
     */  
    private static String exceptionWrite(Exception e) {  
        StringWriter sw = new StringWriter();  
        PrintWriter pw = new PrintWriter(sw);  
        e.printStackTrace(pw);  
        pw.flush();  
        return sw.toString();  
    }  
    
    /**
     * 获取token_id
     */
    public static String getTokenId(HttpServletRequest request){
    	return CookieUtil.getUid(request, Constans.TOKEN_ID);
    }
    
    /**
     * 存储用户信息
     */
    public static void saveUserInfoPO(HttpServletRequest request ,SysUserInfoPO sysUserInfoPO){
    	set(Constans.USER_INFO + getTokenId(request),  sysUserInfoPO);
    }
    
    /**
     * 存储用户信息
     */
    public static void saveUserInfoPO(String tookenId ,SysUserInfoPO sysUserInfoPO){
    	set(Constans.USER_INFO + tookenId,  sysUserInfoPO);
    }
    
    /**
     * 获取用户信息
     */
    public static SysUserInfoPO getUserInfoPO(HttpServletRequest request){
    	return (SysUserInfoPO)get(Constans.USER_INFO + getTokenId(request));
    }
    
    /**
     * 清除用户信息缓存
     */
    public static void clearUserInfoPO(HttpServletRequest request){
    	delete(Constans.USER_INFO + getTokenId(request));
    }
    
    /**
     * 获取用户信息
     */
    public static SysUserInfoPO getUserInfoPOByTokenId(String token_id){
    	if(StringUtils.isNoneBlank(token_id)){
    		return (SysUserInfoPO)get(Constans.USER_INFO + token_id);
    	}else{
    		return null;
    	}
    }
  
}  