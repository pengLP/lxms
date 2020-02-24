package lxms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import lxms.entity.UserAddress;

/**
 * 
 * @author yanlihui 2016.7.13  用户收货地址
 *
 */
public interface UserAddressDao {
	public Integer add(UserAddress userAddress);
	
	public Integer update(UserAddress userAddress);
	
	public Integer delete(@Param("addressId")Long addressId,@Param("uid")Long uid);
	
	public List<UserAddress> find(Map<String,Object> params);
	
	public UserAddress findOne(Map<String,Object> params);
	
	public UserAddress getById(Long addressId);
	
	public Integer getCount(Long uid);
	
	public Integer updateDefault(Long addressId,Integer isDefault,Long uid);
		 
	public Integer notDefault(Long uid);
}