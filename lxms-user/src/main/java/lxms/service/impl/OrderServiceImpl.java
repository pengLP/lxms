package lxms.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import lxms.content.InsideCodes;
import lxms.content.OrderStatus;
import lxms.content.WantedStatus;
import lxms.dao.DepositPayDao;
import lxms.dao.OrderDao;
import lxms.dao.OrderOverDao;
import lxms.dao.PurseDao;
import lxms.dao.PurseLogDao;
import lxms.dao.UserAddressDao;
import lxms.dao.WantedDao;
import lxms.dao.WantedPayDao;
import lxms.entity.DepositPay;
import lxms.entity.Order;
import lxms.entity.OrderOver;
import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.User;
import lxms.entity.Wanted;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.InsertException;
import lxms.exception.OrderException;
import lxms.exception.UpdateException;
import lxms.exception.WantedException;
import lxms.lucene.WantedIndex;
import lxms.service.OrderServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;

@Service
public class OrderServiceImpl implements OrderServiceI {
	@Resource
	private OrderDao orderDao;
	@Resource
	private WantedDao wantedDao;
	@Resource
	private WantedPayDao wantedPayDao;
	@Resource
	private UserAddressDao userAddressDao;
	@Resource
	private DepositPayDao depositPayDao;
	@Resource
	private OrderOverDao orderOverDao;
	@Resource
	private PurseDao purseDao;
	@Resource
	private PurseLogDao purseLogDao;

	private WantedIndex wantedIndex = new WantedIndex(); // ����������

	private Log LOG = LogFactory.getLog(this.getClass());

	/**
	 * ���ɶ���
	 */
	public Order addOrder(Long wid, User sellerUser) throws InsertException, OrderException {
		
		Order order = null;
		try {
			Wanted wanted = wantedDao.getById(wid);
			if (wanted.getStatus().equals("yzf")&&sellerUser.getIsReal().equals("1")) {

				// 1. ����Ѻ��֧��
				DepositPay depositPay = new DepositPay(wanted, sellerUser);
				Integer depositPayResult = depositPayDao.add(depositPay);
				if (depositPayResult == 0) {
					throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
				}
				// ���ɶ���
				order = new Order(sellerUser, wanted, depositPay);
				// ��������
				// ��ӽ��
				Integer orderResult = orderDao.add(order);

				// ��������
				OrderOver orderOver = new OrderOver(order, sellerUser);
				Integer orderOverResult = orderOverDao.add(orderOver);

				if (orderResult == 0 || orderOverResult == 0) {
					throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
				}
				order.setOrderOver(orderOver);
			}

		} catch (InsertException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("�û�" + sellerUser.getTel() + "�г̶���" + wid + "ʧ��");
		}
		return order;
	}

	/**
	 * ����֧��״̬
	 */
	public void updatePayStatus(WeiPayNotify weiPayNotify) throws InsertException,UpdateException, OrderException {

		String out_trade_id = "δ�жϳ���ʲô���͵�֧��";
		try {

			String result_code = weiPayNotify.getResult_code();

			if (result_code != null && result_code.equals("SUCCESS")) {
				out_trade_id = weiPayNotify.getOut_trade_no();
				/**
				 * ����ҵ��
				 */
				process(out_trade_id);
			}
		} catch (InsertException e2) {
			throw e2;
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("���� " + out_trade_id + " ֧�����̳���");
		}

	}

	/**
	 * ����֧��״̬
	 */
	public void updatePayStatus(AlipayNotifyEntity alipayNotifyEntity) throws UpdateException, OrderException {

		String out_trade_id = "δ�жϳ���ʲô���͵�֧��";

		try {
			// -----ִ�а���֧�����
			if (alipayNotifyEntity.getTrade_status().equals("TRADE_SUCCESS")
					|| alipayNotifyEntity.getTrade_status().equals("TRADE_FINISHED")) {
				out_trade_id = alipayNotifyEntity.getOut_trade_no();
				/**
				 * ����ҵ��
				 */
				process(out_trade_id);
			}

		} catch (InsertException e2) {
			throw e2;
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("���� " + out_trade_id + " ֧�����̳���");
		}

	}

	/**
	 * ֧���ɹ�����ҵ��
	 * 
	 * @param out_trade_id
	 * @throws InsertException
	 * @throws UpdateException
	 */
	public void process(String out_trade_id) throws InsertException, UpdateException {
		Integer updateDepositPay = 0; // ����Ѻ��֧���Ľ��
		Integer updateOrderStatus = 0; // ���¶���
		try {
			Long orderId = Long.parseLong(out_trade_id.substring(1,out_trade_id.length()));
			Order order = orderDao.getById(orderId);

			Long wantedId = order.getWid();

			Long depositPayId = order.getDepositPay().getDepositPayId();
			// 1.������֧����Ϣ��Ϊn
			// 2.����������״̬
			Integer wanteResult = wantedDao.updateStatus(wantedId, WantedStatus.Transaction.getStatus());
			Integer wantedPayResult = wantedPayDao.update(wantedId, "n");
			if (wantedPayResult == 0 || wanteResult == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}

			// ����Ѻ��֧��״̬
			updateDepositPay = depositPayDao.updateStatus(depositPayId+"", "y");
			// ���¶���״̬
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId);
			params.put("status", "yzf");
			params.put("depositPaystatus", "y");
			updateOrderStatus = orderDao.updateStatus(params);
			
			if (updateDepositPay == 0 || updateOrderStatus == 0 ) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			User buyUser = order.getSellerUser();
			Purse purse = purseDao.getByUid(buyUser.getUid());
			PurseLog sellerPurseLog = PurseLog.withdraw(purse, order.getDepositPay().getPayamount(), "purse");
			Integer purseLogResult = purseLogDao.add(sellerPurseLog);
			if(purseLogResult==0){
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			// ɾ����������
			wantedIndex.deleteIndex(wantedId); // ɾ�����͵�����
		} catch (InsertException e2) {
			throw e2;
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("���� " + out_trade_id + " ֧�����̳���");
		}

	}

	/**
	 * ��ȡ�Լ��Ķ���
	 */
	public List<Order> find(PageFilter pageFilter, User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageParamsUtil.addToParams(params, pageFilter);
		params.put("uid", user.getUid());
		List<Order> order = orderDao.findMy(params);
		return order;
	}

	/**
	 * ɾ������
	 */
	public void delete(Long orderId, User user) {
		try {
			
			Order order = orderDao.getById(orderId);
			if (order != null) {

				
				Integer	orderResult = orderDao.deleteByBuyer(orderId, order.getBuyerSee());
			
				Integer	order2Result = orderDao.deleteBySeller(orderId, order.getSellerSee());
				

				if (orderResult == 0||order2Result==0) {
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
				
				if(order.getStatus().equals("yzf")){
					Purse sellerPurse = purseDao.getByUid(order.getSellerSee());
					Purse buyPurse = purseDao.getByUid(order.getBuyerSee());
					DepositPay depositPay = order.getDepositPay();
					Integer sellerPurseResult = purseDao.addMoney(sellerPurse.getPurseId(), depositPay.getPayamount());
					
					BigDecimal total = new BigDecimal(order.getTotal());
					BigDecimal newTotal = total.subtract(new BigDecimal(depositPay.getPayamount()));
					Integer buyerPurseResult = purseDao.addMoney(buyPurse.getPurseId(),newTotal.doubleValue());
					// ����Ǯ����Ϣ
					PurseLog sellerPurseLog = PurseLog.add(sellerPurse,depositPay.getPayamount());
					Integer sellerpurseLogResult = purseLogDao.add(sellerPurseLog);
					
					PurseLog buyerPurseLog = PurseLog.add(buyPurse, newTotal.doubleValue());
					Integer buyerLogResult = purseLogDao.add(buyerPurseLog);
					if(sellerPurseResult==0||buyerPurseResult==0||sellerpurseLogResult==0||buyerLogResult==0){
						throw new OrderException("��������ʧ��");
					}
					// ���¶���״̬
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderId", orderId);
					params.put("status", OrderStatus.Cancel.getStatus());
					Integer order3Result = orderDao.updateStatus(params);
					
					// ����Ѻ��֧��״̬
					Integer updateDepositPay = depositPayDao.updateStatus(depositPay.getDepositPayId()+"", "n");
					if(order3Result==0||updateDepositPay==0){
						throw new UpdateException("����ʧ��");
					}
				}
				

			}

		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("�û�Id" + user.getUid() + "ɾ������ʧ��");
		}

	}

	/**
	 * �ϴ���Ʊ��Ϣ
	 */
	public void uploadAirticket(OrderOver orderOver, User user) {
		try {
			if (user != null) {
				orderOver.setUid(user.getUid());
				orderOver.setThroughReviewed("dsh");
				Integer orderOverResult = orderOverDao.update(orderOver);
				if (orderOverResult == 0) {
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("orderId", orderOver.getOrderId());
				params.put("status", "yzjpxx");
				Integer orderResult = orderDao.updateStatus(params);
				if (orderResult == 0) {
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("�û�" + user.getTel() + "�ϴ���Ʊʧ��");
		}

	}

	/**
	 * ȷ���ջ�
	 */
	public void confirm(Long orderId, User user) {
		try {
			Order order = orderDao.getById(orderId);
			if (order != null) {
				User buyUser = order.getBuyUser();
				User sellerUser = order.getSellerUser();
				System.out.println(order.getStatus()+""+OrderStatus.Transaction.getStatus());

				if (order.getStatus().equals(OrderStatus.Transaction.getStatus()) && buyUser.getUid()==user.getUid()) {
					System.out.println("������");
					// ����Ǯ�����
					Purse purse = purseDao.getByUid(sellerUser.getUid());
					BigDecimal orderTotal = new BigDecimal(order.getTotal());
					BigDecimal depositPayTotal = new BigDecimal(order.getDepositPay().getPayamount());
					BigDecimal total = orderTotal.add(depositPayTotal);
					Integer purseResult = purseDao.addMoney(purse.getPurseId(), total.doubleValue());
					// ���¶���״̬
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderId", orderId);
					params.put("status", OrderStatus.TransactionComplete.getStatus());
					Integer orderResult = orderDao.updateStatus(params);
					// ����Ѻ��֧��״̬
					Integer updateDepositPay = depositPayDao.updateStatus(order.getDepositPay().getDepositPayId()+"", "n");
					
				
					// ����Ǯ����Ϣ
					PurseLog purseLog = PurseLog.add(purse, total.doubleValue());
					Integer purseLogResult = purseLogDao.add(purseLog);
					//���¶������
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("refundStatus", "y");
					params2.put("confirmSendProductRemark", "y");
					params2.put("orderOverId", order.getOrderOver().getOrderOverId());
				
					Integer orderOverResult = orderOverDao.updateStatus(params2);
					if (purseLogResult == 0||orderOverResult==0||purseResult == 0 || orderResult == 0||updateDepositPay==0) {
						throw new UpdateException(InsideCodes.INSERT_ERROR.getMsg());
					}
					
				

				}

			}
			;
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new OrderException("�û�" + user.getUid() + "ȷ���ջ�ʧ��");
		}

	}

	public Order getOrderById(Long orderId,User user) {
		Order order = null;
		try {
			Order Torder = orderDao.getById(orderId);
			if(Torder!=null){
				if(Torder.getBuyerSee()==user.getUid()||Torder.getSellerSee()==user.getUid()){
					order = Torder;
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return order;
	}

}
