package com.zrz.util;

public class BaiDuMap {
	static double DEF_PI = 3.14159265359; // PI
	static double DEF_2PI = 6.28318530712; // 2*PI
	static double DEF_PI180 = 0.01745329252; // PI/180.0
	static double DEF_R = 6370693.5; // radius of earth
	
	// 适用于近距离
	public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double dx, dy, dew;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 经度差
		dew = ew1 - ew2;
		// 若跨东经和西经180 度，进行调整
		if (dew > DEF_PI)
			dew = DEF_2PI - dew;
		else if (dew < -DEF_PI)
			dew = DEF_2PI + dew;
		dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
		dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
		// 勾股定理求斜边长
		distance = Math.sqrt(dx * dx + dy * dy);
		return distance;
	}

	// 适用于远距离
	public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2) {
		double ew1, ns1, ew2, ns2;
		double distance;
		// 角度转换为弧度
		ew1 = lon1 * DEF_PI180;
		ns1 = lat1 * DEF_PI180;
		ew2 = lon2 * DEF_PI180;
		ns2 = lat2 * DEF_PI180;
		// 求大圆劣弧与球心所夹的角(弧度)
		distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
		// 调整到[-1..1]范围内，避免溢出
		if (distance > 1.0)
			distance = 1.0;
		else if (distance < -1.0)
			distance = -1.0;
		// 求大圆劣弧长度
		distance = DEF_R * Math.acos(distance);
		return distance;
	}
	
	/**
	 * 获取平均距离
	 */
	public static double getAverageDistanceGetLongDistance(double lon1, double lat1, double lon2, double lat2){
		double distanceShort = BaiDuMap.GetShortDistance(lon1, lat1, lon2, lat2);
		double distanceLong = BaiDuMap.GetLongDistance(lon1, lat1, lon2, lat2);
		
		return (distanceShort+distanceLong)/2;
	}
	
	/**
	 * 获取传入坐标在目标坐标的方位位置
	 */
	public static String getDirection(double xGoal, double yGoal, double xGuess, double yGuess){
		String direction = "";
		//计算|x|,|y|
//		double x_abs = Math.abs(xGoal - xGuess);
//		double y_abs = Math.abs(yGoal - yGuess);
		//计算差值
		double x1 = xGuess - xGoal;
		double y1 = yGuess - yGoal;
		double tanx = x1/y1;
		double angleX = getXByTan(tanx);
		//判断是否在轴上
		if(x1==0 && y1==0){
			direction = "原点";
		}else if(x1==0 && y1>0){
			direction = "正北";
		}else if(x1==0 && y1<0){
			direction = "正南";
		}else if(y1==0 && x1>0){
			direction = "正东";
		}else if(y1==0 && x1<0){
			direction = "正西";
		}
		
		//判断象限
		//象限一
		if(x1>0 && y1>0) {
			if(angleX>0 && angleX<=22.5){
				direction = "北";
			}else if(angleX>22.5 && angleX<=67.5){
				direction = "东北";
			}else if(angleX>67.5 && angleX<90){
				direction = "东";
			}
		//象限二
        }else if(x1>0 && y1<0) {
        	if(angleX<=-67.5 && angleX>-90){
				direction = "东";
			}else if(angleX<=-22.5 && angleX>-67.5){
				direction = "东南";
			}else if(angleX>-22.5 && angleX<0){
				direction = "南";
			}
        //象限三	
        }else if(x1<0 && y1<0) {
        	if(angleX>0 && angleX<=22.5){
				direction = "南";
			}else if(angleX>22.5 && angleX<=67.5){
				direction = "西南";
			}else if(angleX>67.5 && angleX<90){
				direction = "西";
			}
        //象限四	
        }else{
        	if(angleX<=-67.5 && angleX>-90){
				direction = "西";
			}else if(angleX<=-22.5 && angleX>-67.5){
				direction = "西北";
			}else if(angleX>-22.5 && angleX<0){
				direction = "北";
			}
        }
		
		return direction;
	}
	
	public static double getXByTan(double tanx){
		return Math.atan(tanx)/Math.PI*180;
	}

	public static void main(String[] args) {
		System.out.println(getDirection(116.41009, 39.917259, 116.250946, 39.9986));
	}
	
	

}
