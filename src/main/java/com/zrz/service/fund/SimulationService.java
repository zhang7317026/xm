package com.zrz.service.fund;

import java.util.Map;


public interface SimulationService {
	
	/**
     * 模拟器
     */
    Map<String,Object> simulation();
    
    /**
     * 计算器
     */
    Map<String,Object> compute();
    
}
