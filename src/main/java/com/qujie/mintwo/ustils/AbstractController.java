package com.qujie.mintwo.ustils;

import com.qujie.mintwo.config.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller公共组件
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SystemConfig systemConfig;


	protected HttpSession getSession(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession();
	}


	/**
	 * 获取前端参数 get
	 * @return
	 */
	protected Map<String,Object> GetParameterValues(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		try {
			Map<String,Object> stringMap=new HashMap<>();
			Enumeration<String> names = request.getParameterNames();
			while(names.hasMoreElements()) {
				//获取每一个文本域的name
				String name = names.nextElement();
				//根据name获得参数的值
				//为了保证获取到所有的值  因为表单可能是单值  也可能是多值
				String[] values = request.getParameterValues(name);
				//输出参数名和参数值
				if(values!=null && values.length>=1){
					if(values.length>1){
						stringMap.put(name,values);
					}else {
						stringMap.put(name,values[0]);
					}
				}
			}
			return stringMap;
		}catch (Exception e){
			e.printStackTrace();
			return new HashMap<>();
		}
	}


	protected void ajaxRequestTable(HttpServletResponse response, PageUtil page ) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(ConvertJson.ToJson(page.getRows(),page.getTotal()));
		writer.flush();
		writer.close();
	}

	protected void ajaxRequest(HttpServletResponse response, String request) throws IOException {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.print(request);
		writer.flush();
		writer.close();
	}

}
