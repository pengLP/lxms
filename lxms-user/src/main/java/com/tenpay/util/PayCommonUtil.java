package com.tenpay.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class PayCommonUtil {
	public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" +ConstantUtil.APP_KEY);
		System.out.println(sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}
	
	   public static String getRequestXml(SortedMap<Object,Object> parameters){
	        StringBuffer sb = new StringBuffer();
	        sb.append("<xml>");
	        Set es = parameters.entrySet();
	        Iterator it = es.iterator();
	        while(it.hasNext()) {
	            Map.Entry entry = (Map.Entry)it.next();
	            String k = (String)entry.getKey();
	            String v = (String)entry.getValue();
	            if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
	                sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
	            }else {
	                sb.append("<"+k+">"+v+"</"+k+">");
	            }
	        }
	        sb.append("</xml>");
	        return sb.toString();
	    }
	   
	   public static String setXML(String return_code, String return_msg) {
	        return "<xml><return_code><![CDATA[" + return_code
	                + "]]></return_code><return_msg><![CDATA[" + return_msg
	                + "]]></return_msg></xml>";

	}
}
