package lxms.dao;

import java.util.Set;

import lxms.entity.Admin;



public interface AdminDao {
	public Admin login(Admin admin);
	
	public Integer add(Admin admin);
	
	public Integer update(Admin admin);
	
	public Integer delete(Long adminId);
	
	public Admin getById(Long adminId);
	
	public Admin getByUsername(String username);
	
	
	public Set<String> getRolesByUsername(String username);
	
}
