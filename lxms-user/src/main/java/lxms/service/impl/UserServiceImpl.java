package lxms.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.annotation.RequestLimit;
import lxms.content.InsideCodes;
import lxms.dao.PurseDao;
import lxms.dao.UserDao;
import lxms.dao.UserRcertificationDao;
import lxms.entity.Purse;
import lxms.entity.User;
import lxms.entity.UserRcertification;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.UserException;
import lxms.service.UserServiceI;

@Service
public class UserServiceImpl implements UserServiceI {
	@Resource
	private UserDao userDao;

	@Resource
	private PurseDao purseDao;

	@Resource
	private UserRcertificationDao userRcertificationDao;
	private Log LOG = LogFactory.getLog(this.getClass());

	public void add(User user) throws InsertException, UserException {
		try {
			Integer userResult = userDao.add(user);
			if (userResult == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			Purse purse = new Purse(user);

			Integer purseResult = purseDao.add(purse);
			if (purseResult == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			UserRcertification userRcertification = new UserRcertification(user);
			Integer rcertificationResult = userRcertificationDao.add(userRcertification);
			if (rcertificationResult == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e) {
			throw e;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserException("用户" + user.getTel() + "注册失败");

		}

	}

	public boolean updatePassword(User user,String password) throws UpdateException, UserException {
		Map<String,Object> params = new HashMap<String,Object>();
		boolean flag = true;
		try {
			params.put("password", password);
			params.put("uid", user.getUid());
			Integer updateResult = userDao.update(params);
			if (updateResult == 0) {
				flag = false;
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserException("用户" + user.getUid() + "更新用户信息失败");

		}
		return flag;

	}
	public void updateName(User user,String name) throws UpdateException, UserException {
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			params.put("name",name);
			params.put("uid",user.getUid());
			Integer updateResult = userDao.update(params);
			if (updateResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserException("用户" + user.getUid() + "更新用户信息失败");

		}

	}
	public void updateHead(User user,String head) throws UpdateException, UserException {
		Map<String,Object> params = new HashMap<String,Object>();
		try {
			params.put("head",head);
			params.put("uid",user.getUid());
			Integer updateResult = userDao.update(params);
			if (updateResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;

		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserException("用户" + user.getUid() + "更新用户信息失败");

		}

	}

	public User login(User user, Integer code) {
		User Tuser;
		if (code == null) {
			Tuser = userDao.login(user);
		} else {
			Tuser = userDao.getByTel(user.getTel());
		}

		return Tuser;
	}

	public User getById(Long uid) {
		User user = null;
		try {
			user = userDao.getById(uid);
		} catch (Exception e) {
			LOG.error(e);
		}
		
		return user;
	}
	/**
	 * 
	 */
	public void updateCertification(UserRcertification userRcertification, User user) {
		try {
			userRcertification.setUser(user);
			userRcertification.setStatus("dsh");
			Integer result = userRcertificationDao.update(userRcertification);
			if (result == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserException(user.getTel() + "更新实名认证失败");
		}
	}

	public User getByTel(String tel) {
		User user = userDao.getByTel(tel);
		return user;
	}

	public Boolean havePermission(User user) {
		Boolean flag = false;
		if(user.getIsReal().equals("y")){
			flag = true;
			return flag;
		}
		UserRcertification userRcertification = userRcertificationDao.getByUid(user.getUid());
		if(userRcertification.getStatus().equals("yztg")){
			flag=true;
		}
		
		return flag;
	}

}
