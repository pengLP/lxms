package lxms.entity;

import java.util.Date;

/**
 * 用户收货地址
 * @author Administrator
 *
 */
public class UserAddress {
	private Long addressId;  //地址ID
	private User user;  //用户
	private String shipName;  //收货人姓名
	private String provinceCode;  //省编号
	private String province;  //省名称
	private String cityCode; //城市编号
	private String city; //城市名称
	private String areaCode;  //区域编号
	private String area;   //区域
	private String detail;  //详细地址 
	private String phone;  //电话号
	private String zip;  //邮编
	private Integer defaultAddress = 0;  //是否为默认地址
	private Date createDate; //创建时间
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
