package lxms.dao;

import org.apache.ibatis.annotations.Param;

import lxms.entity.WantedPay;

public interface WantedPayDao {
	public Integer add(WantedPay wantedPay);
	
	public Integer update(@Param("wid") Long wid,@Param("paystatus")String paystatus);
	
	public Integer delete(Long wantedPayId);
	
	public WantedPay getById(Long wantedPayId);
	
	public WantedPay getByWanteId(Long wid);
}
