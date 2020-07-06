package com.zrz.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;    
import java.io.FileOutputStream;    
import java.io.InputStream;    
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;    
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;    
    
    
public class DownImgTool {    
    
    public static void download(String urlString, String filename,String savePath) throws Exception {    
        // 构造URL    
        URL url = new URL(urlString);    
        // 打开连接    
        URLConnection con = url.openConnection();    
        //设置请求超时为5s    
        con.setConnectTimeout(5*1000);    
        // 输入流    
        InputStream is = con.getInputStream();    
        
        // 1K的数据缓冲    
        byte[] bs = new byte[1024];    
        // 读取到的数据长度    
        int len;    
        // 输出的文件流    
       File sf = new File(savePath);    
       if(!sf.exists()){    
           sf.mkdirs();    
       }    
       OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);    
        // 开始读取    
        while ((len = is.read(bs)) != -1) {    
          os.write(bs, 0, len);    
        }    
        // 完毕，关闭所有链接    
        os.close();    
        is.close();    
    }     
    
    
    
	/**
	 * TODO 下载文件到本地
	 * 
	 * @param fileUrl  
	 *            远程地址
	 * @param fileLocal
	 *            本地路径
	 * @throws Exception
	 */
	public static void downloadHttps(String fileUrl, String fileLocal) throws Exception {
		SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
		sslcontext.init(null, new TrustManager[] { new X509TrustUtiil() }, new java.security.SecureRandom());
		URL url = new URL(fileUrl);
		HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
			public boolean verify(String s, SSLSession sslsession) {
				System.out.println("WARNING: Hostname is not matched for cert.");
				return true;
			}
		};
		HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
		HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
		HttpsURLConnection urlCon = (HttpsURLConnection) url.openConnection();
		urlCon.setConnectTimeout(120000);
		urlCon.setReadTimeout(120000);
		int code = urlCon.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK) {
			throw new Exception("文件读取失败");
		}
		// 读文件流
		DataInputStream in = new DataInputStream(urlCon.getInputStream());
		DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
		byte[] buffer = new byte[2048];
		int count = 0;
		while ((count = in.read(buffer)) > 0) {
			out.write(buffer, 0, count);
		}
		out.close();
		in.close();
	}
	
    
} 
