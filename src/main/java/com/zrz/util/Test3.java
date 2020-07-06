package com.zrz.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test3 {
	public static void main(String[] args) throws IOException {
		
		long days = ToolClass.getDiffDays("2020-02-27", "2020-03-27", "yyyy-MM-dd");
		System.out.println(days);
	}
	
	
}
