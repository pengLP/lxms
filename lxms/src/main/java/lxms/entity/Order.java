package lxms.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author Administrator
 *
 */
@JSONType(orders = { "orderId", "status", "createDate", "buyUser", "total", "depositPay", "otherRequirement",
		"orderDetail", "orderShip" }, ignores = { "sellerUser", "depositPaystatus", "wanted" })
public class Order {
	private Long orderId; // ������
	private String status; // ����״̬ 'cj'���� 'yzf'��֧��   'jpyzs'��Ʊ��֤��  'jpyzsb' ��Ʊ��֤ʧ��'jyz'������ 'jywc'������� 'qx' ȡ��
	private Date createDate; // ����ʱ��

	private User buyUser; // ���

	private User sellerUser; // �������͵���
	private Double total; // �ܼ�

	private String depositPaystatus;

	private String otherRequirement;



	private Long buyerSee;

	private Long sellerSee;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	

	public Long getBuyerSee() {
		return buyerSee;
	}



	public void setBuyerSee(Long buyerSee) {
		this.buyerSee = buyerSee;
	}



	public Long getSellerSee() {
		return sellerSee;
	}

	public void setSellerSee(Long sellerSee) {
		this.sellerSee = sellerSee;
	}

	public String getOtherRequirement() {
		return otherRequirement;
	}

	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}


	public String getDepositPaystatus() {
		return depositPaystatus;
	}

	public void setDepositPaystatus(String depositPaystatus) {
		this.depositPaystatus = depositPaystatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getBuyUser() {
		return buyUser;
	}

	public void setBuyUser(User buyUser) {
		this.buyUser = buyUser;
	}

	public User getSellerUser() {
		return sellerUser;
	}

	public void setSellerUser(User sellerUser) {
		this.sellerUser = sellerUser;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	

}
