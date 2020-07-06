package com.zrz.service.fund.impl;

public class ExcelPO {
	
	private String date0;
	private double now_all;
	private double input_all;
	
	public ExcelPO(String date0, double now_all, double input_all){
		this.date0 = date0;
		this.now_all = now_all;
		this.input_all = input_all;
	}
	
	
	public String getDate0() {
		return date0;
	}
	public void setDate0(String date0) {
		this.date0 = date0;
	}
	public double getNow_all() {
		return now_all;
	}
	public void setNow_all(double now_all) {
		this.now_all = now_all;
	}
	public double getInput_all() {
		return input_all;
	}
	public void setInput_all(double input_all) {
		this.input_all = input_all;
	}
	
	
}
