package lxms.entity;

public class UserRcertification {
	private Long userRcertificationId;
	private User user; // 用户id
	private String realName; // 用户真实姓名
	private String carded; // 用户身份证号
	private String cardedPicture; // 身份证照片
	private String passportPicture; // 护照图片
	private String status; // 状态      dyz待验证 yzz验证中 yztg //验证通过 yzsb //验证
	private String throughAudit; // 是否通过审核 //n

	public Long getUserRcertificationId() {
		return userRcertificationId;
	}

	public void setUserRcertificationId(Long userRcertificationId) {
		this.userRcertificationId = userRcertificationId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCarded() {
		return carded;
	}

	public void setCarded(String carded) {
		this.carded = carded;
	}

	public String getCardedPicture() {
		return cardedPicture;
	}

	public void setCardedPicture(String cardedPicture) {
		this.cardedPicture = cardedPicture;
	}

	public String getPassportPicture() {
		return passportPicture;
	}

	public void setPassportPicture(String passportPicture) {
		this.passportPicture = passportPicture;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getThroughAudit() {
		return throughAudit;
	}

	public void setThroughAudit(String throughAudit) {
		this.throughAudit = throughAudit;
	}

	public UserRcertification(Long userRcertificationId, User user, String realName, String carded,
			String cardedPicture, String passportPicture, String status, String throughAudit) {
		super();
		this.userRcertificationId = userRcertificationId;
		this.user = user;
		this.realName = realName;
		this.carded = carded;
		this.cardedPicture = cardedPicture;
		this.passportPicture = passportPicture;
		this.status = status;
		this.throughAudit = throughAudit;
	}

	public UserRcertification() {
		// TODO Auto-generated constructor stub
	}

	public UserRcertification(User user) {
		this.user = user;
		this.status = "ddsc";
		this.throughAudit = "n";
	}
}
