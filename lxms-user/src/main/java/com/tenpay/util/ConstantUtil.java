package com.tenpay.util;

public class ConstantUtil {
	/**
	 * �̼ҿ��Կ��Ƕ�ȡ�����ļ�
	 */
	
	//��ʼ��
	public static String APP_ID = "wx0f4493dbeca4d2b2";//΢�ſ���ƽ̨Ӧ��id
	public static String APP_SECRET = "c0203bb790734a591fa1c3930a17cffe";//Ӧ�ö�Ӧ��ƾ֤
	//Ӧ�ö�Ӧ����Կ
	public static String APP_KEY = "CuHDPdydCetWZJepuCWFjy4NmFjS4nN7";
	public static String PARTNER = "1377137302";//�Ƹ�ͨ�̻���
	public static String PARTNER_KEY = "EjSx7w1AxJckPPGWxVasAW5Z9xVJQ94Y";//�̻��Ŷ�Ӧ����Կ
	public static String TOKENURL = "https://api.weixin.qq.com/cgi-bin/token";//��ȡaccess_token��Ӧ��url
	public static String GRANT_TYPE = "client_credential";//�����̶�ֵ 
	public static String EXPIRE_ERRCODE = "42001";//access_tokenʧЧ�����󷵻ص�errcode
	public static String FAIL_ERRCODE = "40001";//�ظ���ȡ������һ�λ�ȡ��access_tokenʧЧ,���ش�����
	public static String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//��ȡԤ֧��id�Ľӿ�urlhttps://api.weixin.qq.com/pay/genprepay
	public static String ACCESS_TOKEN = "access_token";//access_token����ֵ
	public static String ERRORCODE = "errcode";//�����ж�access_token�Ƿ�ʧЧ��ֵ
	public static String SIGN_METHOD = "sha1";//ǩ���㷨����ֵ
	//package����ֵ
	public static String packageValue = "bank_type=WX&body=%B2%E2%CA%D4&fee_type=1&input_charset=GBK&notify_url=http%3A%2F%2F127.0.0.1%3A8180%2Ftenpay_api_b2c%2FpayNotifyUrl.jsp&out_trade_no=2051571832&partner=1900000109&sign=10DA99BCB3F63EF23E4981B331B0A3EF&spbill_create_ip=127.0.0.1&time_expire=20131222091010&total_fee=1";
	public static String traceid = "testtraceid001";//�����û�id
}
