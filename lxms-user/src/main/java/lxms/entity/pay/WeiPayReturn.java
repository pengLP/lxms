package lxms.entity.pay;

import java.util.Date;

/**
 * ����΢��֧���ӿڷ��ص�ֵ
 * 
 * @author Administrator
 *
 */
public class WeiPayReturn {
	private int retcode;
	private String appid; // Ӧ��APPID
	private String mch_id;
	private String nonce_str; // ����ַ���
	private String sign; // ǩ��
	private String prepay_id; // Ԥ֧�����׻Ự��ʶ
	private String timestamp;

	public WeiPayReturn() {
		// TODO Auto-generated constructor stub
	}
	

	public WeiPayReturn(int retcode, String appid, String mch_id, String nonce_str, String sign, String prepay_id,
			String timestamp) {
		super();
		this.retcode = retcode;
		this.appid = appid;
		this.mch_id = mch_id;
		this.nonce_str = nonce_str;
		this.sign = sign;
		this.prepay_id = prepay_id;
		this.timestamp = timestamp;
	}









	public String getTimestamp() {
		return timestamp;
	}









	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}









	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getPrepay_id() {
		return prepay_id;
	}

	public void setPrepay_id(String prepay_id) {
		this.prepay_id = prepay_id;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	
}
