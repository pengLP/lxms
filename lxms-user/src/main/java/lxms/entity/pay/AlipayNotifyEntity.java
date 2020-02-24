package lxms.entity.pay;

/**
 * 支付宝异步通知实体类
 * 
 * @author Yanlihui
 *
 */
public class AlipayNotifyEntity {
	private String out_trade_no; // 悬赏id
	private String trade_no; // 支付宝交易号
	private String trade_status; // 交易状态
	private String seller_id; // 卖家id
	private String seller_email; // 卖家帐号
	private String buyer_id; // 买家id
	private String buyer_email; // 买家帐号
	private String trade_type; // 交易方式
	private Double total_fee; // 总价格
	private String subject; // 商品名称
	public AlipayNotifyEntity() {
		// TODO Auto-generated constructor stub
	}

	
	


	public AlipayNotifyEntity(String out_trade_no, String trade_no, String trade_status, String seller_id,
			String seller_email, String buyer_id, String buyer_email, String trade_type, Double total_fee,
			String subject) {
		super();
		this.out_trade_no = out_trade_no;
		this.trade_no = trade_no;
		this.trade_status = trade_status;
		this.seller_id = seller_id;
		this.seller_email = seller_email;
		this.buyer_id = buyer_id;
		this.buyer_email = buyer_email;
		this.trade_type = trade_type;
		this.total_fee = total_fee;
		this.subject = subject;
	}





	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	

	

	@Override
	public String toString() {
		return "AlipayNotifyEntity [out_trade_no=" + out_trade_no + ", trade_no=" + trade_no + ", trade_status="
				+ trade_status + ", seller_id=" + seller_id + ", seller_email=" + seller_email + ", buyer_id="
				+ buyer_id + ", buyer_email=" + buyer_email + ", trade_type=" + trade_type + ", total_fee=" + total_fee
				+ ", subject=" + subject + "]";
	}





	public String getOut_trade_no() {
		return out_trade_no;
	}





	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}





	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public String getTrade_status() {
		return trade_status;
	}

	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}

	public String getSeller_id() {
		return seller_id;
	}

	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}

	public String getSeller_email() {
		return seller_email;
	}

	public void setSeller_email(String seller_email) {
		this.seller_email = seller_email;
	}

	public String getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getBuyer_email() {
		return buyer_email;
	}

	public void setBuyer_email(String buyer_email) {
		this.buyer_email = buyer_email;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public Double getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Double total_fee) {
		this.total_fee = total_fee;
	}

}
