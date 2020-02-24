package lxms.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.DepositPayDao;
import lxms.entity.DepositPay;
import lxms.exception.InsertException;
import lxms.service.DepositPayServiceI;

@Service
public class DepositPayServiceImpl implements DepositPayServiceI {
	@Resource
	private DepositPayDao depositPayDao;
	private Log LOG = LogFactory.getLog(this.getClass());

	public DepositPay getByOrderId(Long orderId) {
		DepositPay depositPay = depositPayDao.getByOrderId(orderId);
		return depositPay;
	}

	public void add(DepositPay depositPay) throws InsertException {
		try {
			Integer result = depositPayDao.add(depositPay);
			if (result == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}

	}

}
