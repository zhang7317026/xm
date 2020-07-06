package com.zrz.service;

import java.util.List;
import java.util.Map;

import com.zrz.entity.ImgTablePO;

public interface ImgTableService {
	
	List<ImgTablePO> selectList(Map<String,Object> mapWhere);
	
	ImgTablePO getById(String id);
	
	int save(ImgTablePO imgTablePO);
	
	int deleteById(String id);
	
}
