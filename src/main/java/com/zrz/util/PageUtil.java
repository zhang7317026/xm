package com.zrz.util;

import java.util.List;

import com.github.pagehelper.Page;
import com.zrz.util.page.PageVO;

public class PageUtil {
	
	public static PageVO pageVO(List<?> list){
		Page<?> page = (Page<?>) list;
		
		PageVO pageVO = new PageVO();
		pageVO.setTotal(page.getTotal());
		pageVO.setPages(page.getPages());
		pageVO.setList(list);
		
		return pageVO;
	}
}
