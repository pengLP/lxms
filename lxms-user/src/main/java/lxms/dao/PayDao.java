package lxms.dao;


import org.apache.ibatis.annotations.Param;

import lxms.entity.Pay;

public interface PayDao {
	public Integer add(Pay pay);

	public Integer update(Pay pay);

	public Pay get(@Param("wid")String wid,@Param("depositId")String depositId);

	public Pay getByPayTypeId(String payTypeId);
	
	
}
