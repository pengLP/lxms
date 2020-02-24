package lxms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.UserDao;
import lxms.dao.UserRcertificationDao;
import lxms.entity.UserRcertification;
import lxms.exception.UpdateException;
import lxms.service.UserRcertificationServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;

@Service
public class UserRcertificationServiceImpl implements UserRcertificationServiceI {
	@Resource
	private UserDao userDao;
	@Resource
	private UserRcertificationDao userRcertificationDao;
	
	private Log LOG = LogFactory.getLog(this.getClass());

	public void validateSuccess(UserRcertification userRcertification) {
		try {
			userRcertification.setStatus("yztg"); // 验证通过
			userRcertification.setThroughAudit("y"); // 验证通过
			Integer RcertificationResult = userRcertificationDao.updateStatus(userRcertification);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", userRcertification.getUid());
			params.put("isReal", 1);
			Integer userResult = userDao.updateValidate(params);
			if (RcertificationResult == 0 || userResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public void validateFalse(UserRcertification userRcertification) {
		try {
			userRcertification.setStatus("yzsb"); // 验证通过
			userRcertification.setThroughAudit("n"); // 验证通过
			Integer RcertificationResult = userRcertificationDao.updateStatus(userRcertification);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("uid", userRcertification.getUid());
			params.put("isReal", 0);
			Integer userResult = userDao.updateValidate(params);
			if (RcertificationResult == 0 || userResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

	public List<UserRcertification> find(PageFilter pageFilter) {
		List<UserRcertification> list = null;
		try {
			Map<String,Object> params = new HashMap<String, Object>();
			PageParamsUtil.addToParams(params, pageFilter);
			params.put("status", "dsh");
			params.put("throughAudit", "n");
			list = userRcertificationDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return list;
	}


}
