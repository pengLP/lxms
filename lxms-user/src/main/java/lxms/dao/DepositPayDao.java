package lxms.dao;


import org.apache.ibatis.annotations.Param;

import lxms.entity.DepositPay;


public interface DepositPayDao {
	public Integer add(DepositPay depositPay);

	public Integer delete(String depositId);

	public Integer update(DepositPay depositPay);

	public Integer updateStatus(@Param("depositPayId") String depositPayId, @Param("paystatus") String paystatus);

	public DepositPay getById(Long depositPayId);
	
	public DepositPay getByDepositId(String depositId);

	public DepositPay getByOrderId(Long orderId);

}
