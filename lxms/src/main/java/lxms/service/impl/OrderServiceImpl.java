package lxms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.OrderDao;
import lxms.dao.OrderOverDao;
import lxms.entity.Order;
import lxms.entity.OrderOver;
import lxms.entity.User;
import lxms.exception.OrderException;
import lxms.exception.UpdateException;
import lxms.service.OrderServiceI;
import lxms.service.UserRcertificationServiceI;
import lxms.tool.PageFilter;

@Service
public class OrderServiceImpl implements OrderServiceI {
	@Resource
	private OrderDao orderDao;

	@Resource
	private OrderOverDao orderOverDao;
	
	@Resource
	private UserRcertificationServiceI userRcertificationService;

	private Log LOG = LogFactory.getLog(this.getClass());

	public List<OrderOver> find(PageFilter pageFilter, String status) {
		List<OrderOver> orderOverList;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("throughReviewed", status);
			orderOverList = orderOverDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("��ȡʧ��");
		}
		return orderOverList;
	}

	public void validateSuccess(OrderOver orderOver) {
		try {

			orderOver.setThroughReviewed("tg");   //��Ʊ��֤ͨ��
			Integer orderOverResult = orderOverDao.update(orderOver);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderOver.getOrderId());
			params.put("status", "jyz");   //����״̬����Ϊ������
			Integer orderResult = orderDao.updateStatus(params);
			if (orderOverResult == 0 || orderResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
			/**
			 * ��֪ͨ
			 */
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("������֤ͨ����������");
		}

	}

	public void validateFalse(OrderOver orderOver) {
		try {

			orderOver.setThroughReviewed("tg");   //��Ʊ��֤ͨ��
			Integer orderOverResult = orderOverDao.update(orderOver);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderOver.getOrderId());
			params.put("status", "jpyzsb"); //��Ʊ��֤ʧ��
			Integer orderResult = orderDao.updateStatus(params);

			if (orderOverResult == 0 || orderResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
			/**
			 * ��֪ͨ
			 */
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("������֤ͨ����������");
		}

	}

}
