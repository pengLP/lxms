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
	public Integer add(UserRcertification userRcertification);

	public Integer update(UserRcertification userRcertification);

	public Integer updateStatus(Map<String, Object> params);

	public UserRcertification getByUid(Long uid);

	public List<UserRcertification> find(Map<String, Object> params);
}
