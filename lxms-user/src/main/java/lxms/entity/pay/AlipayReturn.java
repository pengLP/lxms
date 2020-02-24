package lxms.entity.pay;
/**
 *  ֧�������ؽ��
 * @author yanlihui
 *
 */
public class AlipayReturn {
	private String service; // �ӿ�����    Ĭ��ֵ mobile.securitypay.pay
	private String partner; // ���������ID
	private String _input_charset; // ���������ַ��� Ĭ��Ϊ utf-8
	private String sign_type; // ǩ����ʽ    Ĭ��ΪRSA
	private String notify_url; // �������첽֪ͨҳ��·��
	private String out_trade_no; // �̻���վΨһ������ 
	private String subject; // ��Ʒ����
	private String payment_type; // ֧������ Ĭ��ֵΪ1;
	private String seller_id; // ����֧�����˺�
	private Double total_fee; // �ܽ��    
	private String body; // ��Ʒ����
	private String it_b_pay; //�೤ʱ����Ҫ֧��
	private String success;  //�ֻ����ж��Ƿ�֧���ɹ�
	public AlipayReturn() {
		// TODO Auto-generated constructor stub
	}
	
	public AlipayReturn(String service, String partner, String _input_charset, String sign_type, String notify_url,
			String out_trade_no, String subject, String payment_type, String seller_id, Double total_fee, String body,
			String it_b_pay, String success) {
		super();
		this.service = service;
		this.partner = partner;
		this._input_charset = _input_charset;
		this.sign_type = sign_type;
		this.notify_url = notify_url;
		this.out_trade_no = out_trade_no;
		this.subject = subject;
		this.payment_type = payment_type;
		this.seller_id = seller_id;
		this.total_fee = total_fee;
		this.body = body;
		this.it_b_pay = it_b_pay;
		this.success = success;
	}

	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String get_input_charset() {
		return _input_charset;
	}
	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getPayment_type() {
		return payment_type;
	}
	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public Double getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIt_b_pay() {
		return it_b_pay;
	}
	public void setIt_b_pay(String it_b_pay) {
		this.it_b_pay = it_b_pay;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "AlipayResult [service=" + service + ", partner=" + partner + ", _input_charset=" + _input_charset
				+ ", sign_type=" + sign_type + ", notify_url=" + notify_url + ", out_trade_no=" + out_trade_no
				+ ", subject=" + subject + ", payment_type=" + payment_type + ", seller_id=" + seller_id
				+ ", total_fee=" + total_fee + ", body=" + body + ", it_b_pay=" + it_b_pay + ", success=" + success
				+ "]";
	}
	
	
}
