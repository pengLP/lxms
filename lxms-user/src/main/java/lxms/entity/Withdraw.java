package lxms.entity;

import java.util.Date;

public class Withdraw {
	private Integer withdrawId;
	private Double total;
	private String email;
	private String realName;
	private Date createDate;
	private Integer block = 0;
	private String status = "cj";
	private Long purseId;
	public Withdraw() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Withdraw(Integer withdrawId, Double total, String email, String realName, Date createDate, Integer block,
			String status) {
		super();
		this.withdrawId = withdrawId;
		this.total = total;
		this.email = email;
		this.realName = realName;
		this.createDate = createDate;
		this.block = block;
		this.status = status;
	}


	public static Withdraw create(String realName,Purse purse,double total){
		Withdraw withdraw = new Withdraw();
		withdraw.setEmail(purse.getBindingAlipay());
		withdraw.setRealName(realName);
		withdraw.setTotal(total);
		withdraw.setPurseId(purse.getPurseId());
		return withdraw;
	}
	
	
	public Long getPurseId() {
		return purseId;
	}


	public void setPurseId(Long purseId) {
		this.purseId = purseId;
	}


	public Integer getBlock() {
		return block;
	}


	public void setBlock(Integer block) {
		this.block = block;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getWithdrawId() {
		return withdrawId;
	}
	public void setWithdrawId(Integer withdrawId) {
		this.withdrawId = withdrawId;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
