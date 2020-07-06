package com.zrz.downImg;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.zrz.util.DownImgTool;

public class QiuBai {
	
	public static String localParh = "E:/img/QiuBai";

	public static void main(String[] args) {
		
//		String rs = HttpRequest.sendGet("https://www.qiushibaike.com/imgrank/page/2/", 
//				"", 
//				"UTF-8", 
//				5000);
//		
//		System.out.println(rs);
		
		
		getImg();
	}
	
	public static void getImg(){
		
		for(int i=1;;i++){
			int pageSum = 0;
			String url = "https://www.qiushibaike.com/imgrank/page/"+i+"/";
			try {
				Document doc = Jsoup.connect(url).get();
				//获取总页数
				Elements lis = doc.select("ul[class=pagination]").select("li");
				String pageSumStr = lis.get(lis.size()-2).select("span").text();
				pageSum = Integer.parseInt(pageSumStr); 
				
				//获取内容
				Elements divs = doc.select("div .article.block.untagged.mb15");
				for(Element div : divs){
					String numberStr = div.select("div[class=stats]").select("i[class=number]").get(0).text();
					//赞数大于500
					if(Integer.parseInt(numberStr)>500){
						//获取图片url
						Element imgElement = div.select("div[class=thumb]").select("img").get(0);
						String imgUrl = imgElement.attr("src");
						imgUrl = "http:"+imgUrl;
						//获取后缀类型
						String type = imgUrl.substring(imgUrl.lastIndexOf("."), imgUrl.length());
						String imgName = imgElement.attr("alt")+type;
						//下载
						try {
							DownImgTool.download(imgUrl, imgName, localParh);
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(i>=pageSum){
				break;
			}
		}
		
	}
	
}
