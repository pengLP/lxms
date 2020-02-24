package lxms.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * @author Administrator
 *
 */
@JSONType(orders = { "orderId", "status", "createDate", "buyUser", "total", "depositPay", "otherRequirement",
		"orderDetail", "orderShip","sellerUser"}, ignores = {  "depositPaystatus", "wid" })
public class Order {
	private Long orderId; // 订单号
	private String status; // 定单状态 'cj'创建 'yzf'已支付 'jyz'交易中 'jywc'交易完成 'qx' 取消
	private Date createDate; // 创建时间
	private User buyUser; // 买家
	private User sellerUser; // 接受悬赏的人
	private Double total; // 总价
	private DepositPay depositPay; // 押金
	private String depositPaystatus;
	private Long wid;
	private String otherRequirement;
	

	private String productName; // 商品名称 ，用括号分开
	private String price; // 商品价格 用括号隔开
	private String num; // 商品数量 用括号隔开
	private Integer deliveryTime; // 发货时间
	private Integer pack; // 是否有包装 1有包装 0 没有包装
	private String deliveryArea; // 发货地区
	private String requirements; // 特殊要求
	private Double wantedTotal; // 总价格
	private String picture;  //悬赏照片
	

	private String shipName; // 收货人姓名
	private String province; // 省名称
	private String city; // 城市名称
	private String area; // 区域
	private String detail; // 详细地址
	private String phone; // 电话号
	private String zip; // 邮编
	
	
	private OrderOver orderOver;     //订单后
	
	private Long buyerSee;
	private Long sellerSee;

	

	
	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(User sellerUser, Wanted wanted, DepositPay depositPay) {
		this.depositPay = depositPay;
		this.status = "cj";
		this.buyUser = wanted.getUser();
		this.sellerUser = sellerUser;
		BigDecimal wantedTotal = new BigDecimal(wanted.getTotal());
		this.total = wantedTotal.doubleValue();
		this.depositPaystatus = "n";
		this.wid = wanted.getWid();
		this.otherRequirement = wanted.getRequirements();
		this.buyerSee = wanted.getUser().getUid();
		this.sellerSee = sellerUser.getUid();
		
		this.productName = wanted.getProductName();
		this.price = wanted.getPrice();
		this.num = wanted.getNum();
		this.deliveryTime = wanted.getDeliveryTime();
		this.pack = wanted.getPack();
		this.deliveryArea = wanted.getDeliveryArea();
		this.requirements = wanted.getRequirements();
		this.wantedTotal = wanted.getTotal();
		this.picture = wanted.getPicture();
		
		UserAddress userAddress = wanted.getUserAddress();
		
		this.shipName = userAddress.getShipName();
		this.province = userAddress.getProvince();
		this.city = userAddress.getCity();
		this.area = userAddress.getArea();
		this.detail = userAddress.getDetail();
		this.phone = userAddress.getPhone();
		this.zip = userAddress.getZip();
	}


	public String getProductName() {
		return productName;
	}



	public void setProductName(String productName) {
		this.productName = productName;
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



	public Double getWantedTotal() {
		return wantedTotal;
	}



	public void setWantedTotal(Double wantedTotal) {
		this.wantedTotal = wantedTotal;
	}



	public String getPicture() {
		return picture;
	}



	public void setPicture(String picture) {
		this.picture = picture;
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



	public Long getBuyerSee() {
		return buyerSee;
	}



	public void setBuyerSee(Long buyerSee) {
		this.buyerSee = buyerSee;
	}



	public Long getSellerSee() {
		return sellerSee;
	}

	public void setSellerSee(Long sellerSee) {
		this.sellerSee = sellerSee;
	}

	public String getOtherRequirement() {
		return otherRequirement;
	}

	public void setOtherRequirement(String otherRequirement) {
		this.otherRequirement = otherRequirement;
	}

	

	public Long getWid() {
		return wid;
	}

	public void setWid(Long wid) {
		this.wid = wid;
	}

	public DepositPay getDepositPay() {
		return depositPay;
	}

	public void setDepositPay(DepositPay depositPay) {
		this.depositPay = depositPay;
	}

	
	public String getDepositPaystatus() {
		return depositPaystatus;
	}

	public void setDepositPaystatus(String depositPaystatus) {
		this.depositPaystatus = depositPaystatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getBuyUser() {
		return buyUser;
	}

	public void setBuyUser(User buyUser) {
		this.buyUser = buyUser;
	}

	public User getSellerUser() {
		return sellerUser;
	}

	public void setSellerUser(User sellerUser) {
		this.sellerUser = sellerUser;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	



	public OrderOver getOrderOver() {
		return orderOver;
	}



	public void setOrderOver(OrderOver orderOver) {
		this.orderOver = orderOver;
	}
	

}
