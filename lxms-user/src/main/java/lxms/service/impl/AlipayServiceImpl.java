package lxms.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;

import lxms.entity.DepositPay;
import lxms.entity.Wanted;

@Service
public class AlipayServiceImpl {
	public static final String _INPUT_CHARSET = "utf-8";
	public static final String NOTIFY_URL = "http://139.129.221.22:8080/lxms-user/alipay/notify";
	public static final String PARTNER = "2088421466656510";
	public static final String PAYMENT_TYPE = "1";
	public static final String SELLER_ID = "lxmslxms@126.com";
	public static final String SHOW_URL = "m.alipay.com";
	public static final String IT_B_PAY = "30m";

	public String AlipayRsaSign(Wanted wanted) {
		String params = "partner=\"" + PARTNER + "\"&seller_id=\"" + SELLER_ID + "\"&out_trade_no=\"" + wanted.getWid()
				+ "\"&subject=\"" + wanted.getProductName() + "\"&body=\"" + wanted.getProductName() + "\"&total_fee=\""
				+ wanted.getTotal() + "\"&notify_url=\"" + NOTIFY_URL + "\"&service=\"mobile.securitypay.pay"
				+ "\"&payment_type=\"" + PAYMENT_TYPE + "\"&_input_charset=\"" + _INPUT_CHARSET + "\"&it_b_pay=\""
				+ IT_B_PAY + "\"&show_url=\"" + SHOW_URL + "\"";

		String sign = null;
		try {
			sign = URLEncoder.encode(RSA.sign(params, AlipayConfig.private_key, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = params + "&" + "sign=\"" + sign + "\"" + "&sign_type=\"RSA\"";
		System.out.println(sign);
		return result;
	}

	public String AlipayRsaSign(DepositPay depositPay,Long orderId) {
		String params = "partner=\"" + PARTNER + "\"&seller_id=\"" + SELLER_ID + "\"&out_trade_no=\"o"
				+ orderId + "\"&subject=\"" + "Ñº½ð" + "\"&body=\""
				+ "Ñº½ð" + "\"&total_fee=\"" + depositPay.getPayamount() + "\"&notify_url=\""
				+ NOTIFY_URL + "\"&service=\"mobile.securitypay.pay" + "\"&payment_type=\"" + PAYMENT_TYPE
				+ "\"&_input_charset=\"" + _INPUT_CHARSET + "\"&it_b_pay=\"" + IT_B_PAY + "\"&show_url=\"" + SHOW_URL
				+ "\"";
		System.out.println(params);
		String sign = null;
		try {
			sign = URLEncoder.encode(RSA.sign(params, AlipayConfig.private_key, "utf-8"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result = params + "&" + "sign=\"" + sign + "\"" + "&sign_type=\"RSA\"";
		return result;

	}
}
