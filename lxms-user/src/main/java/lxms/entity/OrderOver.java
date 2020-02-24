package lxms.entity;

public class OrderOver {
	private Long orderOverId;
	private Long orderId;
	private String refundStatus;// �˿�״̬
	private String confirmSendProductRemark; // �ͻ��Ƿ��յ���Ʒ
	private String expressNumber = "0";   //��ݵ���
	private String airTicket;    //�ɻ�ƱͼƬ
	private String throughReviewed;   //ͨ�����  //�ȴ��ϴ� ddsc    �����dsh     ͨ��tg   δͨ��wtg
	private Long uid; //����
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getOrderOverId() {
		return orderOverId;
	}
	public void setOrderOverId(Long orderOverId) {
		this.orderOverId = orderOverId;
	}
	
	public String getRefundStatus() {
		return refundStatus;
	}
	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}
	public String getConfirmSendProductRemark() {
		return confirmSendProductRemark;
	}
	public void setConfirmSendProductRemark(String confirmSendProductRemark) {
		this.confirmSendProductRemark = confirmSendProductRemark;
	}
	public String getExpressNumber() {
		return expressNumber;
	}
	public void setExpressNumber(String expressNumber) {
		this.expressNumber = expressNumber;
	}
	public String getAirTicket() {
		return airTicket;
	}
	public void setAirTicket(String airTicket) {
		this.airTicket = airTicket;
	}
	public String getThroughReviewed() {
		return throughReviewed;
	}
	public void setThroughReviewed(String throughReviewed) {
		this.throughReviewed = throughReviewed;
	}
	
	
	
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	
	public OrderOver(Order order,User user) {
		this.orderId = order.getOrderId();
		this.refundStatus = "n";
		this.confirmSendProductRemark = "n";
		this.throughReviewed = "ddsc";
		this.uid = user.getUid();
	}
	public OrderOver() {
		// TODO Auto-generated constructor stub
	}
}
