package lxms.content;

public enum OrderOverStatus {
	/**
	 * 等待上传身份证和护照
	 */
	WaitingForUpload("ddsc"),
	WaitingForReview("dsh"),
	Pass("tg"),
	NotPass("wtg");
	private String status;
	
	private OrderOverStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}	
