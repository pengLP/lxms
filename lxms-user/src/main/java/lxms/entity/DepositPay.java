package lxms.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 押金
 * @author yanlihui
 *
 */
@JSONType(orders = { "depositPayId", "depositId", "payamount","createDate"}, ignores = { "paystatus", "user" })
public class DepositPay {
	private Long depositPayId;
	private String paystatus;   //支付状态
	private Double payamount;   //支付金额
	private String payMethod;   //支付方式
	private Date createDate;  //创建时间
	private User user; //支付的用户
	public DepositPay() {
		// TODO Auto-generated constructor stub
	}	
	public DepositPay(Wanted wanted,User user) {
		this.paystatus = "n";
		BigDecimal wantedTotal = new BigDecimal(wanted.getTotal());
		BigDecimal depositTotal = wantedTotal.multiply(new BigDecimal(3)).divide(new BigDecimal(100), 0,RoundingMode.UP);
		if(depositTotal.compareTo(new BigDecimal(1))==-1){
			this.payamount = Double.parseDouble("1.00");
		}else{
			this.payamount = depositTotal.doubleValue();
		}
		
		this.user = user;
	}	
	
	public DepositPay(Long depositPayId, String paystatus, Double payamount,
			String payMethod, Date createDate, User user) {
		super();
		this.depositPayId = depositPayId;
		this.paystatus = paystatus;
		this.payamount = payamount;
		this.payMethod = payMethod;
		this.createDate = createDate;
		this.user = user;
	}


	public Long getDepositPayId() {
		return depositPayId;
	}


	public void setDepositPayId(Long depositPayId) {
		this.depositPayId = depositPayId;
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	

	public String getPaystatus() {
		return paystatus;
	}
	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}
	public Double getPayamount() {
		return payamount;
	}
	public void setPayamount(Double payamount) {
		this.payamount = payamount;
	}
	
}
