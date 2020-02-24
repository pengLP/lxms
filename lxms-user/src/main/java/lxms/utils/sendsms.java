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
		//您的是:1234567。请不要把它泄露给其他人。如非本人操作，可不用理会！
	    String content = new String("您的是:" + mobile_code + "。请不要把它泄露给其他人。如非本人操作，可不用理会！"); 

		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_yanlihui"), 
			    new NameValuePair("password", "yanchuang"), //密码可以使用明文密码或使用32位MD5加密
			    //new NameValuePair("password", util.StringUtil.MD5Encode("密码")),
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