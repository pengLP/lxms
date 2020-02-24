package lxms.content;

public enum OrderStatus {
	//dzf ��֧��   //gq����    //yzf��֧��  //jyz������   //jywc�������
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
