package lxms.utils;
import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;

public class sendsms {
	
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	
	
	
	public static int SendMessage(String tel) {
		
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		//client.getParams().setContentCharset("GBK");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

		
		int mobile_code = (int)((Math.random()*9+1)*100000);

		//System.out.println(mobile);
		//������:1234567���벻Ҫ����й¶�������ˡ���Ǳ��˲������ɲ�����ᣡ
	    String content = new String("������:" + mobile_code + "���벻Ҫ����й¶�������ˡ���Ǳ��˲������ɲ�����ᣡ"); 

		NameValuePair[] data = {//�ύ����
			    new NameValuePair("account", "cf_yanlihui"), 
			    new NameValuePair("password", "yanchuang"), //�������ʹ�����������ʹ��32λMD5����
			    //new NameValuePair("password", util.StringUtil.MD5Encode("����")),
			    new NameValuePair("mobile", tel), 
			    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);			
		try {
			client.executeMethod(method);	
			
			String SubmitResult =method.getResponseBodyAsString();
			
			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();

			String code = root.elementText("code");	
						
			 if("2".equals(code)){
				return mobile_code;
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0; 
		
	}
	
}