package lxms.entity;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 钱包
 * 
 * @author yanlihui
 *
 */
@JSONType(orders = { "purseId", "balance", "isHavePssword", "isHaveAli" }, ignores = { "user", "password" })
public class Purse {
	private Long purseId; // 钱包ID
	private User user; // 用户
	private String password; // 支付密码
	private Double balance; // 余额
	private String bindingAlipay; // 绑定的支付宝
	private Integer isHavePassword;
	private Integer isHaveAli;

	public Purse() {
		// TODO Auto-generated constructor stub
	}

	public Purse(User user) {
		this.user = user;
		this.balance = new Double("0");
		this.isHavePassword = 0;
		this.isHaveAli = 0;
	}

	public Purse(Long purseId, User user, String password, Double balance, String bindingAlipay, Integer isHavePassword,
			Integer isHaveAli) {
		super();
		this.purseId = purseId;
		this.user = user;
		this.password = password;
		this.balance = balance;
		this.bindingAlipay = bindingAlipay;
		this.isHavePassword = isHavePassword;
		this.isHaveAli = isHaveAli;
	}

	
	public Integer getIsHavePassword() {
		return isHavePassword;
	}

	public void setIsHavePassword(Integer isHavePassword) {
		this.isHavePassword = isHavePassword;
	}

	public Integer getIsHaveAli() {
		return isHaveAli;
	}

	public void setIsHaveAli(Integer isHaveAli) {
		this.isHaveAli = isHaveAli;
	}

	public Long getPurseId() {
		return purseId;
	}

	public void setPurseId(Long purseId) {
		this.purseId = purseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getBindingAlipay() {
		return bindingAlipay;
	}

	public void setBindingAlipay(String bindingAlipay) {
		this.bindingAlipay = bindingAlipay;
	}

}
