package com.qujie.mintwo.ustils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.util.Date;

public class ConvertJson {




	/**
	 * LIST转换 JSON
	 * @param datalist
	 * @return
	 */
	public static String ToJson(Object datalist,int count){
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonDateValueProcessor());
			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new JsonDateValueProcessor());
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			String listToJson = JSONArray.fromObject(datalist,jsonConfig).toString();
			listToJson="{\"total\":"+count+",\"rows\":"+listToJson+"}";
			return listToJson;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
