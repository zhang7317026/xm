package com.zrz.downImg;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zrz.util.DownImgTool;

public class JuRanGaoXiao {
	
	public static String localParh = "E:/img/JuRanGaoXiao";

	public static void main(String[] args) {
		
		Stack<List<String>> stackAll = getImg();
		downImg(stackAll);
	}
	
	public static Stack<List<String>> getImg(){
		
		Stack<List<String>> stackAll = new Stack<List<String>>();
		
		for(int i=1;;i++){
			int pageSum = 0;
			String url = "https://www.zbjuran.com/quweitupian/list_2_"+i+".html";
			try {
				Document doc = Jsoup.connect(url).validateTLSCertificates(false).get();
				//获取总页数
				Elements as = doc.select("div[class=pages]").select("a");
				String href = as.get(as.size()-1).attr("href");
				href = href.split("\\.")[0].split("_")[2];
				pageSum = Integer.parseInt(href); 
				
				//获取内容
				Elements items = doc.select("div[class=item]");
				for(Element item : items){
					String href_one = item.select("a[class=read]").attr("href");
					href_one = "https://www.zbjuran.com"+href_one;
					
					Document doc_one = Jsoup.connect(href_one).validateTLSCertificates(false).get();
					Elements ps = doc_one.select("div[class=article]").select("div[class=text]").select("p");
					
					//获取及组装name-url对
					String imgName = "";
					String imgUrl = "";
					String imgType = "";
					for(int j=1;j<ps.size();j++){
						Element p = ps.get(j);
						if(StringUtils.isBlank(p.html())){
							continue;
						}
						if(j%2==0){
							try{
								imgUrl = p.select("img").get(0).attr("src");
								imgType = "."+imgUrl.split("\\.")[imgUrl.split("\\.").length-1];
							}catch(Exception e){
								continue;
							}
						}else{
							imgName = replaceSpecStr(p.text());
						}
						
						if(j%2==0){
							try {
								List<String> listOne = new ArrayList<String>();
								listOne.add(imgName+imgType);
								listOne.add(imgUrl);
								//压栈
								stackAll.add(listOne);
								
//								final String imgArgs[] = {imgUrl,imgName+imgType};
//								new Thread(){
//									public void run() {
//										try {
//											System.out.println("------下载("+imgArgs[0]+")+("+imgArgs[1]+")");
//											DownImgTool.downloadHttps(imgArgs[0], localParh+"/"+imgArgs[1]);
//										} catch (Exception e) {
//											e.printStackTrace();
//										}
//									}
//						        }.start();
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					
				}


				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			System.out.println("---当前("+i+")页----共计("+pageSum+")页");
			if(i>=pageSum){
				break;
			}
		}
		
		System.out.println("------------共计("+stackAll.size()+")条----------");
		return stackAll;
	}
	
	
	public static void downImg(final Stack<List<String>> stackAll){
		
		int threadNum = 20;
		for (int i = 0; i < threadNum; i++) {
			new Thread(){
				public void run() {
					while (!stackAll.empty()) {
						List<String> list = stackAll.pop();
						String imgName = list.get(0);
						String imgUrl = list.get(1);
						try {
							System.out.println("------下载("+imgUrl+")+("+imgName+")");
							DownImgTool.downloadHttps(imgUrl, localParh+"/"+imgName);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
	        }.start();
		}
		
	}
	
	
	/**
	 * 正则替换所有特殊字符
	 * @param orgStr
	 * @return
	 */
	public static String replaceSpecStr(String orgStr){
		if (null!=orgStr&&!"".equals(orgStr.trim())) {
			String regEx="[\\s~·`!！@#￥$%^……&*（()）\\-——\\-_=+【\\[\\]】｛{}｝\\|、\\\\；;：:‘'“”\"，,《<。.》>、/？?]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(orgStr);
			return m.replaceAll("");
		}
		return null;
	}
	
}
