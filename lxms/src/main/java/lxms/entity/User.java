package lxms.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long uid; // 用户id
	private String tel; // 用户电话号
	private String name; // 用户昵称
	private String password; // 用户密码
	private String head = "http://www.izhaowo.com/images/upload/pic75x75.jpg"; // 头像url
	private String isReal = "n"; // 是否通过实名验证
	private Date createTime; // 创建时间

	
	
	public User(Long uid, String tel, String name, String password, String head, String isReal, Date createTime) {
		super();
		this.uid = uid;
		this.tel = tel;
		this.name = name;
		this.password = password;
		this.head = head;
		this.isReal = isReal;
		this.createTime = createTime;
	}

	public String getIsReal() {
		return isReal;
	}

	public void setIsReal(String isReal) {
		this.isReal = isReal;
	}

	public String getHead() {
		return head;
	}

	public void setHead(String head) {
		this.head = head;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public User() {
		// TODO Auto-generated constructor stub
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}