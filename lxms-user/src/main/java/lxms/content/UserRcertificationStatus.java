package lxms.content;

public enum UserRcertificationStatus {
	/**
	 * 等待上传机票
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
