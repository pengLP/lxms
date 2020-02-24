package lxms.service.impl;

import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import lxms.content.InsideCodes;
import lxms.dao.AdminDao;
import lxms.dao.UserRcertificationDao;
import lxms.entity.Admin;
import lxms.exception.UpdateException;
import lxms.service.AdminServiceI;

@Service
public class AdminServiceImpl implements AdminServiceI {
	@Resource
	private AdminDao adminDao;

	@Resource
	private UserRcertificationDao userRcertificationDao;
	private Log LOG = LogFactory.getLog(this.getClass());
	
	public Admin login(Admin admin) {
		Admin Tuser = adminDao.login(admin);
		return Tuser;
	}

	public Admin getById(Long adminId) {
		Admin Tuser = adminDao.getById(adminId);
		return Tuser;
	}

	public void update(Admin admin) {
		try {
			Integer result = adminDao.update(admin);
			if(result==0){
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
			
		}catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			
		}
	
	}
	
	public Admin getByUsername(String username){
		Admin Tuser = adminDao.getByUsername(username);
		return Tuser;
	}

	

}
