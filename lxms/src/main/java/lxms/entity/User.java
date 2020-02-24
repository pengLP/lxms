package lxms.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long uid; // �û�id
	private String tel; // �û��绰��
	private String name; // �û��ǳ�
	private String password; // �û�����
	private String head = "http://www.izhaowo.com/images/upload/pic75x75.jpg"; // ͷ��url
	private String isReal = "n"; // �Ƿ�ͨ��ʵ����֤
	private Date createTime; // ����ʱ��

	
	
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