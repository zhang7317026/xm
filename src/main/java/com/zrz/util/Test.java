package com.zrz.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
	static int i=0;
	static int j=0;
	private static String nexturl="http://www.qzone.cc/haokan/dongman/1217271.html";
	public static void main(String[] args) {
		while(nexturl!=null){
			if(j==5000){
				break;
			}
			System.out.println(nexturl);
			System.out.println("正在扫描链接.........");
			final String[] str=getImg(nexturl).split(",");
			System.out.println("链接扫描完成........");
			System.out.println("开始下载.........");
			for(i=0;i<str.length;i++){
				new Thread(){
					public void run() {
						File file=new File("C:/Img/"+System.currentTimeMillis()+"."+str[i].substring(str[i].lastIndexOf(".")));
						try {
							URL url=new URL(str[i]);
							HttpURLConnection con=(HttpURLConnection) url.openConnection();
							InputStream in=con.getInputStream();
							FileOutputStream out=new FileOutputStream(file);
							int len;
							byte[] byt=new byte[1028];
							while((len=in.read(byt))!=-1){
								out.write(byt, 0, len);
							}
							System.out.println("下载完成!");
							out.flush();
							in.close();
							out.close();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					};
				}.start();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			j+=1;
		}
		System.out.println("图片扫描完成！");
	}
	
	public static String getImg(String urls){
		URL url;
		StringBuilder b=new StringBuilder();
		String content="";
		String cont="";
		String next="";
		try {
			url = new URL(urls);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			BufferedReader buf=new BufferedReader(new InputStreamReader(con.getInputStream()));
			String str=null;
			//detail_article
			String strs="";
			while((str=buf.readLine())!=null){
				strs+=str;
			}
			strs=strs.substring(strs.indexOf("detail_article")+14);
			content=strs.substring(0,strs.indexOf("detail_article"));
			next=strs.substring(strs.indexOf("class=\"down\""));
			next=next.substring(next.indexOf("href")+6);
			next=next.substring(0, next.indexOf("\""));
			while(true){
				if(content.indexOf("original")!=-1){
					cont=content.substring(content.indexOf("original")+10);
					cont=cont.substring(0,cont.indexOf("\""));
					b.append(cont+",");
					content=content.substring(content.indexOf(cont));
				}else{
					break;
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nexturl="http://www.qzone.cc";
		nexturl+=next;
		return b.toString();
	} 
}
