package lxms.utils;

import java.util.Map;

import lxms.tool.PageFilter;



public class PageParamsUtil {
	public static void addToParams(Map<String,Object> params,PageFilter pageFilter){
		params.put("start",(pageFilter.getPage()-1)*pageFilter.getRows());
		params.put("size", pageFilter.getRows());
	}
} 
