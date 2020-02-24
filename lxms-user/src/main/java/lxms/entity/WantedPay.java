package lxms.entity;

import java.util.Date;
/**
 * ����֧��״̬
 * @author yanlihui
 *
 */
public class WantedPay {
	private Long wantedPayId;  //����֧��id
	private Wanted wanted;     //����
	private String paystatus;   //֧��״̬     n δ֧��  ֧��
	private Double payamount;   //֧�����
	private Date createDate;   //����ʱ��
	private String payMethod;    //֧����ʽ
	public WantedPay() {
		// TODO Auto-generated constructor stub
	}
	public static WantedPay getWantedPay(Wanted wanted){
		WantedPay wantedPay = new WantedPay();
		wantedPay.setWanted(wanted);
		wantedPay.setPayamount(wanted.getTotal());
		wantedPay.setPaystatus("n");
		return wantedPay;
	}
	public WantedPay(Long wantedPayId, Wanted wanted, String paystatus, Double payamount, Date createDate,
			String payMethod) {
		super();
		this.wantedPayId = wantedPayId;
		this.wanted = wanted;
		this.paystatus = paystatus;
		this.payamount = payamount;
		this.createDate = createDate;
		this.payMethod = payMethod;
	}
	public Long getWantedPayId() {
		return wantedPayId;
	}
	public void setWantedPayId(Long wantedPayId) {
		this.wantedPayId = wantedPayId;
	}
	public Wanted getWanted() {
		return wanted;
	}
	public void setWanted(Wanted wanted) {
		this.wanted = wanted;
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

	
	
}
