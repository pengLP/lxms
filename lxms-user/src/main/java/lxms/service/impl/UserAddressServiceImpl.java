package lxms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.UserAddressDao;
import lxms.entity.User;
import lxms.entity.UserAddress;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.UserAddressException;
import lxms.service.UserAddressServiceI;

@Service
public class UserAddressServiceImpl implements UserAddressServiceI {

	private Log LOG = LogFactory.getLog(this.getClass());

	@Resource
	private UserAddressDao userAddressDao;

	public void add(UserAddress address) throws InsertException, UserAddressException {
		try {
			User user = address.getUser();

			Integer count = userAddressDao.getCount(user.getUid());

			if (count == 0) {
				address.setDefaultAddress(1); // 如果用户没有收货地址，则第一个添加的设为默认收货地址
			} else {
				if (address.getDefaultAddress() == 1) {
					userAddressDao.notDefault(user.getUid());
				}
			}

			Integer result = userAddressDao.add(address);
			if (result == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserAddressException(address.getUser().getUid() + "添加地址操作失败");
		}

	}

	public void update(UserAddress address) throws UpdateException, UserAddressException {
		try {
			Integer result = userAddressDao.update(address);
			if (result == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserAddressException(address.getUser().getUid() + "更新地址操作失败");
		}

	}

	public void delete(Long addressId, User user) throws DeleteException, UserAddressException {
		try {
			Long uid = user.getUid();
			Integer result = userAddressDao.delete(addressId, uid);
			if (result == 0) {
				throw new DeleteException(InsideCodes.DELETE_ERROR.getMsg());
			}
		} catch (UpdateException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserAddressException(user.getUid() + "删除地址操作失败");
		}

	}

	public List<UserAddress> getByUser(User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", user.getUid());
		List<UserAddress> addressList = new ArrayList<UserAddress>();
		try {
			addressList = userAddressDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return addressList;
	}

	public void setDefault(Long addressId, User user) throws UpdateException, UserAddressException {
		try {
			userAddressDao.notDefault(user.getUid());
			Integer result = userAddressDao.updateDefault(addressId, 1, user.getUid());
			if (result == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new UserAddressException(user.getUid() + "更新地址操作失败");
		}
	}

}
