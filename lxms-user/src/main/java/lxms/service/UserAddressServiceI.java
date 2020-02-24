package lxms.service;

import java.util.List;

import lxms.entity.User;
import lxms.entity.UserAddress;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.UserAddressException;

public interface UserAddressServiceI {
	public void add(UserAddress address) throws InsertException,UserAddressException;
	
	public void update(UserAddress address) throws UpdateException,UserAddressException;
	
	public void delete(Long addressId,User user) throws DeleteException,UserAddressException;
	
	public void setDefault(Long addressId,User user) throws UpdateException,UserAddressException;
	
	public List<UserAddress> getByUser(User user);
	
}
