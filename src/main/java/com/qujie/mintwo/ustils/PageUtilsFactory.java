package com.qujie.mintwo.ustils;

import com.qujie.mintwo.config.SystemConfig;

import java.util.Map;

/**
 * @author ChengKexing
 * @date 2019/1/4 0004
 */
public class PageUtilsFactory {

    private static SystemConfig systemConfig = null;

    static{
        systemConfig = SpringContextUtils.getBean("systemConfig", SystemConfig.class);
    }

    public static PageUtil getInstance(Map <String,Object> map) {
        if( "NaN".equals(map.get("currentpagecount").toString().intern()) ){
            map.put("currentpagecount",1);
            return new PageUtil( map );
        }else{
            return new PageUtil(map,systemConfig);
        }
    }
}
