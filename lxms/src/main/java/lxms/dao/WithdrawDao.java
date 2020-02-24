package lxms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import lxms.entity.Withdraw;



public interface WithdrawDao {
	
	public Integer updateStatus(Map<String,Object> params);
		
	public Withdraw getByWithdrawId(@Param("withdrawId")Long withdrawId);
	
	
	public List<Withdraw> find(Map<String,Object> params);
	
}
