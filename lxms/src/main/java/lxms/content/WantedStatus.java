package lxms.content;

public enum WantedStatus {
	//dzf 待支付   //gq过期    //yzf已支付  //jyz交易中   //jywc交易完成
	Tobepaid("dzf"),
	Expired("gq"),
	HavePaid("yzf"),
	Transaction("jyz"),
	TransactionComplete("jywc");
	private String status;
	
	private WantedStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}	
