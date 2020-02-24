package lxms.service;

import lxms.entity.Admin;


public interface AdminServiceI {
	public Admin login(Admin admin);
	
	public Admin getById(Long adminId);
	
	public void update(Admin admin);
	
	public Admin getByUsername(String username);
	
	
}
