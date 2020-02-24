package lxms.service;

import lxms.entity.User;
import lxms.entity.UserRcertification;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.UserException;

public interface UserServiceI {
	public void add(User user) throws InsertException,UserException;
	
	public boolean updatePassword(User user,String password)throws UpdateException,UserException;
	
	public void updateName(User user,String name)throws UpdateException,UserException;
	
	public void updateHead(User user,String head) throws UpdateException, UserException;
	
	public User getByTel(String tel);
	
	public User getById(Long uid);
	
	public User login(User user,Integer code);
	
	public Boolean havePermission(User user);
	
	public void updateCertification(UserRcertification userRcertification,User user);
	
}
