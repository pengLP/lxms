package lxms.service;

import java.util.List;

import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.User;
import lxms.tool.PageFilter;

public interface PurseServiceI {
	public Purse getMyPurse(User user);
	
	public void updatePassword(String newPassword,User user);
	
	public int setPassword(String newPassword,User user);
	
	public void updateBindingAlipay(String newAliPay,User user);
	
	public void setBindingAlipay(String alipay,User user);
	
	public boolean validate(String password,User user);
	
	public List<PurseLog> log(PageFilter filter,User user);
	
	public String withdrawMoney(String password,String sum,User user,String realName);
}
