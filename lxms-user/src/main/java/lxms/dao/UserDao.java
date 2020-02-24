package lxms.dao;

import java.util.Map;

import lxms.entity.User;

public interface UserDao {
	public Integer add(User user);
	
	public Integer update(Map<String,Object> params);
	
	public User getByTel(String tel);
	
	public User getById(Long uid);
	
	public User login(User user);
	
	
}
