package com.qujie.mintwo.ustils;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CheckUtils {
    /**
     * 不是空返回true
     * 是空返回false
     * @param obj
     * @return
     */
    public static boolean isNotNull(Object obj){
        if (obj instanceof String)
            return (obj!=null && !"".equals(obj.toString().trim()));

        if (obj instanceof Date)
            return ( obj!=null ) ;

        if (obj instanceof List)
            return ( obj!=null && ((List) obj).size()!=0 );

        if (obj instanceof Object[])
            return ( obj!=null && ((Object[]) obj).length!=0 );

        if (obj instanceof Map)
            return obj!=null && ((Map) obj).keySet().size()!=0;

        if (obj instanceof Long)
             return obj != null && (Long)obj != 0L;

        if (obj instanceof Integer)
             return obj != null && (Integer)obj != 0;

        return false;
    }
    public static boolean AllObjNotNull(Object... objs){
        for (int i = 0; i <objs.length ; i++) {
            if(!isNotNull( objs[i] ) ){
                return false;
            }
        }
        return true;
    }
}
