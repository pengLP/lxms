package lxms.entity;

public class OrderOver {
	private Long orderOverId;
	private Long orderId;
	private String refundStatus;// 退款状态
	private String confirmSendProductRemark; // 客户是否收到商品
	private String expressNumber = "0";   //快递单号
	private String airTicket;    //飞机票图片
	private String throughReviewed;   //通过审核  //等待上传 ddsc    待审核dsh     通过tg   未通过wtg
	private Long uid; //卖家
	
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
