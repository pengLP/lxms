package lxms.entity.pay;
/**
 *  支付宝返回结果
 * @author yanlihui
 *
 */
public class AlipayReturn {
	private String service; // 接口名称    默认值 mobile.securitypay.pay
	private String partner; // 合作者身份ID
	private String _input_charset; // 参数编码字符集 默认为 utf-8
	private String sign_type; // 签名方式    默认为RSA
	private String notify_url; // 服务器异步通知页面路径
	private String out_trade_no; // 商户网站唯一订单号 
	private String subject; // 商品名称
	private String payment_type; // 支付类型 默认值为1;
	private String seller_id; // 卖家支付宝账号
	private Double total_fee; // 总金额    
	private String body; // 商品详情
	private String it_b_pay; //多长时间需要支付
	private String success;  //手机端判断是否支付成功
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
