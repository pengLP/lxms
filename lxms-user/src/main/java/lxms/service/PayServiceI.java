package lxms.service;

import lxms.entity.Pay;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.InsertException;
import lxms.exception.PayException;
import lxms.exception.UpdateException;

public interface PayServiceI {

	public void addLog(AlipayNotifyEntity alipayNotifyEntity) throws InsertException, UpdateException, PayException;

	public void addLog(WeiPayNotify weiPayNotify) throws InsertException, UpdateException, PayException;

	public Pay getById(String payTypeId);
}
