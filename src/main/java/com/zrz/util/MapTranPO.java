package com.zrz.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;  
  
/** 
 * @author 杜群星 
 * 
 */  
public class MapTranPO {  
      
    public static Map<String, Object> PO2Map(Object o) throws Exception{  
        Map<String, Object> map = new HashMap<String, Object>();  
        Field[] fields = null;  
        String clzName = o.getClass().getSimpleName();  
        fields = o.getClass().getDeclaredFields();  
        for (Field field : fields) {  
            field.setAccessible(true);  
            String proName = field.getName();  
            Object proValue = field.get(o);  
            map.put(proName.toUpperCase(), proValue);  
        }  
        return map;  
    }  
      
  
    public static Object map2PO(Map<String,Object> map,Object o) throws Exception{  
        if (!map.isEmpty()) {  
            for (String k : map.keySet()) {  
                Object v = "";  
                if (!k.isEmpty()) {  
                    v = map.get(k);  
                }  
                Field[] fields = null;  
                fields = o.getClass().getDeclaredFields();  
                String clzName = o.getClass().getSimpleName();  
                for (Field field : fields) {  
                    int mod = field.getModifiers();  
                    if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){  
                        continue;  
                    }  
                    if (field.getName().toUpperCase().equals(k)) {  
                        field.setAccessible(true);  
                        field.set(o, v);  
                    }  
  
                }  
            }  
        }  
        return o;  
    }  
}  