package lxms.dao;

import java.util.List;
import java.util.Map;

import lxms.entity.Role;


public interface RoleDao {
	public void add(Role role);
	
	public void delete(Long rid);
	
	public void update(Role role);
	
	public Role getById(Long rid);
	
	public List<Role> find(Map<String,Object> params);
	
}
