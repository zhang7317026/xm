package com.zrz.util;

import java.io.FileOutputStream;
import java.io.OutputStream;

import sun.misc.BASE64Decoder;

/**
 * java Base64编码实例
 * 
 * @author Administrator
 * 
 */
public class Base64Util {
    /**
     * 将 s 进行 BASE64 编码
     * 
     * @param s
     * @return
     */
    public static String getBASE64(String s) {
        if (s == null) {
            return null;
        }

        return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
    }

    /**
     * 将 BASE64 编码的字符串 s 进行解码
     * 
     * @param s
     * @return
     */
    public static String getFromBASE64(String s) {
        if (s == null) {
            return null;
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String args[]) {
        String str = "我的测试字符串";
        System.out.println("原字符串：" + str);
        String encodeStr = Base64Util.getBASE64(str);
        System.out.println("编码后：" + encodeStr);
        String decodeStr = Base64Util.getFromBASE64(encodeStr);
        System.out.println("解码后：" + decodeStr);
    }
    
    
    //base64字符串转化成图片  
    public static boolean GenerateImage(String imgStr ,String imgFilePath)  
    {   //对字节数组字符串进行Base64解码并生成图片  
        if (imgStr == null) //图像数据为空  
            return false;  
        BASE64Decoder decoder = new BASE64Decoder();  
        try   
        {  
            //Base64解码  
            byte[] b = decoder.decodeBuffer(imgStr);  
            for(int i=0;i<b.length;++i)  
            {  
                if(b[i]<0)  
                {//调整异常数据  
                    b[i]+=256;  
                }  
            }  
            //生成图片  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }   
        catch (Exception e)   
        {  
            return false;  
        }  
    }  
}