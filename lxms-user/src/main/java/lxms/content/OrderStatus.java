package lxms.content;

public enum OrderStatus {
	//dzf 待支付   //gq过期    //yzf已支付  //jyz交易中   //jywc交易完成
	Create("cj"),
	HavePaid("yzf"),
	Transaction("jyz"),
	Validate("yzjpxx"),
	TransactionComplete("jywc"),
	Cancel("qx");
	private String status;
	
	private OrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}	
