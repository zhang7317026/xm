package com.zrz.util;

public class GoldUtil {

	/**
	 * 金币-等级 对照表
	 */
	public static final int GOLD_LEVEL[] = 
			{
				100,200,500,800,1000,//1-5
				1200,1500,2000,2500,3000,//6-10
				4000,5000,6500,8000,10000,//11-15
				15000,20000,25000,35000,50000,//16-20
				70000,100000,150000//21-23
			};
	
	/**
	 * 根据gold获取level
	 */
	public static int getLevel(int gold){
		int gold_level[] = GoldUtil.GOLD_LEVEL;
    	int level = 1;
    	for(int i=0;i<gold_level.length+1;i++){
    		if(gold < gold_level[0]){
    			level = 1;//最下级 1级
    			break;
    		}else if(gold >= gold_level[gold_level.length-1]){
    			level = gold_level.length +1;//最大级 24级
    			break;
    		}else if(gold>=gold_level[i] && gold<gold_level[i+1]){
    			level = (i+2);//对应级
    			break;
    		}
    	}
    	return level;
	}
	
	public static void main(String[] args) {
		System.out.println(getLevel(150000));
	}
	
}
