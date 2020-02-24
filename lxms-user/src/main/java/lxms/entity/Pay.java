package lxms.entity;

import java.math.BigDecimal;
import java.util.Date;

import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;

/**
 * 交易信息
 * @author yanlihui
 *
 */
public class Pay {
	private Long payId;  //交易ID
	private String seller_Id;  //卖家ID
	private String trade_status; //交易状态   
	private String buyer_id;   //买家ID
	private String trade_type;  // 交易类型
	private Long total_fee; //总金额
	private String trade_no; //支付订	单号
	private String payTypeId;//支付的是什么
	private String payment;  //支付方式

	private Date createDate; //创建时间
	
	
	@Override
	public String toString() {
		return "Pay [payId=" + payId + ", seller_Id=" + seller_Id + ", trade_status=" + trade_status + ", buyer_id="
				+ buyer_id + ", trade_type=" + trade_type + ", total_fee=" + total_fee + ", trade_no=" + trade_no
				+ ", payTypeId=" + payTypeId + ", payment=" + payment + ", createDate=" + createDate + "]";
	}
	public Pay(AlipayNotifyEntity alipayNotifyEntity) {
		BigDecimal total = new BigDecimal(alipayNotifyEntity.getTotal_fee());
		this.seller_Id = alipayNotifyEntity.getSeller_id();
		this.trade_status = alipayNotifyEntity.getTrade_status();
		this.buyer_id = alipayNotifyEntity.getBuyer_id();
		this.trade_type = alipayNotifyEntity.getTrade_type();
		this.total_fee =(total.multiply(new BigDecimal(100)).longValue());
		this.trade_no = alipayNotifyEntity.getTrade_no();
		this.payment="zfb";	
		this.payTypeId = alipayNotifyEntity.getOut_trade_no();
		
	}
	public Pay(WeiPayNotify weiPayNotify){
		this.seller_Id = weiPayNotify.getMch_id();
		this.trade_status = weiPayNotify.getResult_code();
		this.buyer_id = weiPayNotify.getOpenid();
		this.trade_type = weiPayNotify.getTrade_type();
		this.total_fee =Long.valueOf(weiPayNotify.getCash_fee());
		this.trade_no = weiPayNotify.getTransaction_id();
		this.payment="wx";
		this.payTypeId = weiPayNotify.getOut_trade_no();

	}
	public Pay() {
		// TODO Auto-generated constructor stub
	}


	
	public Pay(Long payId, String seller_Id, String trade_status, String buyer_id, String trade_type, Long total_fee,
			String trade_no, String payTypeId, String payment, Date createDate) {
		super();
		this.payId = payId;
		this.seller_Id = seller_Id;
		this.trade_status = trade_status;
		this.buyer_id = buyer_id;
		this.trade_type = trade_type;
		this.total_fee = Long.valueOf(total_fee);
		this.trade_no = trade_no;
		this.payTypeId = payTypeId;
		this.payment = payment;
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getPayId() {
		return payId;
	}
	public void setPayId(Long payId) {
		this.payId = payId;
	}
	public String getSeller_Id() {
		return seller_Id;
	}
	public void setSeller_Id(String seller_Id) {
		this.seller_Id = seller_Id;
	}
	public String getTrade_status() {
		return trade_status;
	}
	public void setTrade_status(String trade_status) {
		this.trade_status = trade_status;
	}
	public String getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(String buyer_id) {
		this.buyer_id = buyer_id;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	
	public Long getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(Long total_fee) {
		this.total_fee = total_fee;
	}
	public String getTrade_no() {
		return trade_no;
	}
	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}
	

	public String getPayTypeId() {
		return payTypeId;
	}
	public void setPayTypeId(String payTypeId) {
		this.payTypeId = payTypeId;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	
}
