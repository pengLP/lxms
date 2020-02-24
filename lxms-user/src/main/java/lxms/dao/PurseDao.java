package lxms.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import lxms.entity.Purse;

public interface PurseDao {
	public Integer add(Purse purse);
	
	public Integer update(Purse purse);
	
	public Integer updateMap(Map<String,Object> params);
	
	
	public Integer addMoney(@Param("purseId") Long purseId,@Param("sum")Double sum);
	
	public Integer subtractMoney(@Param("purseId")Long purseId,@Param("sum")Double sum);
	
	public Purse getById(Long purseId);
	
	public Purse getByUid(Long uid);
	
}
