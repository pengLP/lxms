package lxms.service;

import java.util.List;

import lxms.entity.UserRcertification;
import lxms.tool.PageFilter;

public interface UserRcertificationServiceI {
	
	
	public void validateSuccess(UserRcertification userRcertification);
	
	public void validateFalse(UserRcertification userRcertification);
	
	public List<UserRcertification> find(PageFilter pageFilter);

}
