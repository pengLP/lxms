package lxms.service.impl;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.PayDao;
import lxms.entity.Pay;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.InsertException;
import lxms.exception.PayException;
import lxms.service.PayServiceI;

/**
 * ֧����¼
 * 
 * @author Administrator
 *
 */
@Service
public class PayServiceImpl implements PayServiceI {

	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private PayDao payDao;

	/**
	 * ����֧�������ص���Ϣ��¼
	 * 
	 * @param alipayNotifyEntity
	 * @return
	 */
	public void addLog(AlipayNotifyEntity alipayNotifyEntity) {
		try {
			if (alipayNotifyEntity == null) {
				throw new PayException("֧����֪ͨΪ��");
			}
			Pay pay = new Pay(alipayNotifyEntity);
			System.out.println(pay.toString());
			Integer result = payDao.add(pay);
			if (result == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e) {
			throw e;
		} catch (PayException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PayException(alipayNotifyEntity.getOut_trade_no() + "���ؽ�����Ϣʧ��");
		}

	}

	public Pay getById(String payTypeId) {
		Pay pay = payDao.getByPayTypeId(payTypeId);
		return pay;
	}

	/**
	 * ����΢�ŷ��ص���Ϣ��¼
	 * 
	 * @param weiPayNotify
	 * @return
	 */
	public void addLog(WeiPayNotify weiPayNotify) {
		try {
			if (weiPayNotify == null) {
				throw new PayException("֧����֪ͨΪ��");
			}
			Pay pay = new Pay(weiPayNotify);
			Integer result = payDao.add(pay);
			if (result == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e) {
			throw e;
		} catch (PayException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PayException(weiPayNotify.getOut_trade_no() + "���ؽ�����Ϣʧ��");
		}

	}
	
	
}
