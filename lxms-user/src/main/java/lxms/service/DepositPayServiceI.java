package lxms.service;

import lxms.entity.DepositPay;
import lxms.exception.InsertException;

public interface DepositPayServiceI {
	
	public void add(DepositPay depositPay) throws InsertException;
	
	
	
	public DepositPay getByOrderId(Long orderId);
	
}
