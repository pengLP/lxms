package lxms.entity;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long uid; // �û�id
	private String username; // �û���
	private String name; // �û��ǳ�
	private String password; // �û�����
	private Date createTime; // ����ʱ��
	public Admin() {
		// TODO Auto-generated constructor stub
	}
	
	public Admin(Long uid, String username, String name, String password, Date createTime) {
		super();
		this.uid = uid;
		this.username = username;
		this.name = name;
		this.password = password;
		this.createTime = createTime;
	}

	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
	
}
