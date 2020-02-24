package lxms.content;

public enum WantedStatus {
	//dzf ��֧��   //gq����    //yzf��֧��  //jyz������   //jywc�������
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
