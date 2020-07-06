package com.zrz.util.page;

import java.util.List;

/**
 * @author zhang_rzhi
 * 2017_03_16
 */
public class PageVO {
	private long total;
	private int pages;
	private List<?> list;
	
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
}