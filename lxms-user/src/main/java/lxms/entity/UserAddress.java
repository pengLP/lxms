package lxms.entity;

import java.util.Date;

/**
 * �û��ջ���ַ
 * @author Administrator
 *
 */
public class UserAddress {
	private Long addressId;  //��ַID
	private User user;  //�û�
	private String shipName;  //�ջ�������
	private String provinceCode;  //ʡ���
	private String province;  //ʡ����
	private String cityCode; //���б��
	private String city; //��������
	private String areaCode;  //������
	private String area;   //����
	private String detail;  //��ϸ��ַ 
	private String phone;  //�绰��
	private String zip;  //�ʱ�
	private Integer defaultAddress = 0;  //�Ƿ�ΪĬ�ϵ�ַ
	private Date createDate; //����ʱ��
	public UserAddress() {
		// TODO Auto-generated constructor stub
	}
	
	

	public UserAddress(Long addressId, User user, String shipName, String provinceCode, String province,
			String cityCode, String city, String areaCode, String area, String detail, String phone, String zip,
			Integer defaultAddress, Date createDate) {
		super();
		this.addressId = addressId;
		this.user = user;
		this.shipName = shipName;
		this.provinceCode = provinceCode;
		this.province = province;
		this.cityCode = cityCode;
		this.city = city;
		this.areaCode = areaCode;
		this.area = area;
		this.detail = detail;
		this.phone = phone;
		this.zip = zip;
		this.defaultAddress = defaultAddress;
		this.createDate = createDate;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getDefaultAddress() {
		return defaultAddress;
	}

	public void setDefaultAddress(Integer defaultAddress) {
		this.defaultAddress = defaultAddress;
	}

	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getShipName() {
		return shipName;
	}
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	
}
