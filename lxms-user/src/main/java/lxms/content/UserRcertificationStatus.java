package lxms.content;

public enum UserRcertificationStatus {
	/**
	 * �ȴ��ϴ���Ʊ
	 */
	WaitingForUpload("ddsc"),  
	WaitingForReview("dsh"),
	Pass("tg"),
	NotPass("wtg");
	private String status;
	
	private UserRcertificationStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}	
