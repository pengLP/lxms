package lxms.dao;

import java.util.List;
import java.util.Map;

import lxms.entity.UserRcertification;

/**
 * �û�ʵ����֤
 * 
 * @author Administrator
 *
 */
public interface UserRcertificationDao {

	public Integer updateStatus(UserRcertification userRcertification);
	
	public UserRcertification getById(Long userRcertificationId);
	
	public UserRcertification getByUid(Long uid);
	
	public List<UserRcertification> find(Map<String, Object> params);
}
