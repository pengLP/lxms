package lxms.entity;

import java.util.Date;

/**
 * ������modal
 * 
 * @author Administrator
 *
 */
public class Wanted {
	private Long wid; // ����id
	private User user; // �����û�
	private String productName; // ��Ʒ���� �������ŷֿ�
	private String price; // ��Ʒ�۸� �����Ÿ���
	private String num; // ��Ʒ���� �����Ÿ���
	private UserAddress userAddress; // �ջ���ַ
	private Integer deliveryTime; // ����ʱ��
	private Integer pack; // �Ƿ��а�װ 1�а�װ 0 û�а�װ
	private String deliveryArea; // ��������
	private String requirements; // ����Ҫ��
	private Double total; // �ܼ۸�
	private Date createDate; // ����ʱ��
	private String picture;
	private String status = "dzf";   //dzf ��֧��     //yzf��֧��  //jyz������   //jywc�������
	private String createType;

	public Wanted() {
		// TODO Auto-generated constructor stub
	}

	
	public Wanted(Long wid, User user, String productName, String price, String num, UserAddress userAddress,
			Integer deliveryTime, Integer pack, String deliveryArea, String requirements, Double total, Date createDate,
			String picture, String status, String createType) {
		super();
		this.wid = wid;
		this.user = user;
		this.productName = productName;
		this.price = price;
		this.num = num;
		this.userAddress = userAddress;
		this.deliveryTime = deliveryTime;
		this.pack = pack;
		this.deliveryArea = deliveryArea;
		this.requirements = requirements;
		this.total = total;
		this.createDate = createDate;
		this.picture = picture;
		this.status = status;
		this.createType = createType;
	}


	public String getCreateType() {
		return createType;
	}


	public void setCreateType(String createType) {
		this.createType = createType;
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UserAddress getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Integer getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Integer deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Long getWid() {
		return wid;
	}

	public void setWid(Long wid) {
		this.wid = wid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getPack() {
		return pack;
	}

	public void setPack(Integer pack) {
		this.pack = pack;
	}

	public String getDeliveryArea() {
		return deliveryArea;
	}

	public void setDeliveryArea(String deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}
