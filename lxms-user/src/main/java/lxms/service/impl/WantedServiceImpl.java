package lxms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.content.WantedStatus;
import lxms.dao.PurseDao;
import lxms.dao.PurseLogDao;
import lxms.dao.WantedDao;
import lxms.dao.WantedPayDao;
import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.User;
import lxms.entity.Wanted;
import lxms.entity.WantedPay;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.WantedException;
import lxms.lucene.WantedIndex;
import lxms.service.WantedServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;
import lxms.utils.SessionUtil;

/**
 * ��������Ĳ���
 * 
 * @author yanlihui
 *
 */
@Service
public class WantedServiceImpl implements WantedServiceI {

	private Log LOG = LogFactory.getLog(this.getClass());

	private WantedIndex wantedIndex = new WantedIndex(); // ����������

	@Resource
	private WantedDao wantedDao;
	@Resource
	private WantedPayDao wantedPayDao;
	@Resource
	private PurseDao purseDao;
	@Resource
	private PurseLogDao purseLogDao;

	/*
	 * ���������� 1.��������� 2.���������֧����Ϣ ֧�������֧����Ϣ
	 */
	public void add(Wanted wanted) throws InsertException, WantedException {
		try {
			// ���������
			Integer result = wantedDao.add(wanted); // ��������ӽ��

			WantedPay wantedPay = WantedPay.getWantedPay(wanted);
			Integer payResult = wantedPayDao.add(wantedPay); // ������֧����Ϣ��ӽ��

			if (result == 0 || payResult == 0) { // �ж��������֧����Ϣ��û�гɹ�
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
		} catch (InsertException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("Insert " + wanted.getProductName() + " error");
		}

	}
	/**
	 * ɾ��������
	 * 1.�鿴�������֧����Ϣ
	 * 2.�����n��ֱ��ɾ��
	 * 3.�����y����Ǯ�˻����û�
	 */
	public void delete(Long wid, User user) throws DeleteException, WantedException {
		try {
			//1.��ȡ������֧����Ϣ
			WantedPay wantedPay = wantedPayDao.getByWanteId(wid);
			//2.��ȡ������
			Wanted wanted = wantedDao.getById(wid);
			if (wantedPay.getPaystatus().equals("y")) {
				
				//2.��ȡǮ��
				Purse purse = purseDao.getByUid(user.getUid());
				//3.����Ǯ����־
				PurseLog purseLog = PurseLog.add(purse, wanted.getTotal());
				//5.���Ǯ��֧����־
				Integer purseLogResult = purseLogDao.add(purseLog);
				//6.����Ǯ��Ǯ��
				Integer purseResult = purseDao.addMoney(purse.getPurseId(), wanted.getTotal());
				if (purseResult == 0 || purseLogResult == 0) {
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
			}
			//ɾ��������
			Integer result = wantedDao.delete(wid, user.getUid());
			//ɾ������֧����Ϣ
			Integer DeleteWantedPay = wantedPayDao.delete(wantedPay.getWantedPayId());
			if (result == 0 || DeleteWantedPay == 0) {
				throw new DeleteException(InsideCodes.DELETE_ERROR.getMsg());
			}
			if(wanted.getStatus().equals(WantedStatus.HavePaid)){
				//�������״̬Ϊ��֧����ɾ������
				wantedIndex.deleteIndex(wid);
			}
		} catch (DeleteException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("Delete " + wid + " error");
		}

	}
	/**
	 * ��������
	 */
	public void update(Wanted wanted) throws UpdateException, WantedException {
		try {
			Integer result = wantedDao.update(wanted);
			if (result == 0) {
				throw new UpdateException(InsideCodes.DELETE_ERROR.getMsg());
			}

		} catch (UpdateException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("update " + wanted.getWid() + " error");
		}

	}
	/**
	 * ��������
	 */
	public List<Wanted> find(PageFilter pageFilter) {
		List<Wanted> wantedList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "yzf"); // ����������֧���Ķ���
			PageParamsUtil.addToParams(params, pageFilter);
			wantedList = wantedDao.find(params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wantedList;
	}
	
	

	/**
	 * ��ȡ����������
	 * 1.������״̬Ϊ��֧����ֱ�ӷ���
	 * 2.���������֧��״̬���ж����������Ǳ��˷����������ֱ�ӷ���
	 */
	public Wanted getWanted(Long wid, HttpSession session) {
		Wanted wanted = null;
		Wanted Twanted = wantedDao.getById(wid);
		try {
			if (Twanted != null) {
				if (Twanted.getStatus().equals(WantedStatus.HavePaid.getStatus())) {
					wanted = Twanted;
				} else {
					User user = SessionUtil.getCurrentUser(session);
					if(user!=null){
						if (Twanted.getUser().getUid().equals(user.getUid())) {
							wanted = Twanted;
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		return wanted;
	}
	/**
	 * ���ҵ����û�������
	 */
	public List<Wanted> findByUser(PageFilter pageFilter, User currentUser) {
		List<Wanted> wantedList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			PageParamsUtil.addToParams(params, pageFilter);
			params.put("uid", currentUser.getUid());
			params.put("status", "dzf");
			wantedList = wantedDao.findDetail(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		return wantedList;
	}

	/**
	 * ����΢�ŵ��첽֪ͨ������������Ϣ 1.����֧��״̬ 2.�������������
	 */
	public void updatePayStatus(WeiPayNotify weiPayNotify) {
		Integer updatewanted = 0; // ����������Ľ��
		Integer updatewantedPay = 0; // ����������֧������Ľ��
		String out_trade_id = "δ�жϳ���ʲô���͵�֧��";
		try {
			out_trade_id = weiPayNotify.getOut_trade_no();
			Long wantedId = Long.parseLong(out_trade_id.substring(6, out_trade_id.length()));
			// ����������״̬Ϊ yzf��֧��
			updatewanted = wantedDao.updateStatus(wantedId, WantedStatus.HavePaid.getStatus());
			// ����������֧����ϢΪy
			updatewantedPay = wantedPayDao.update(wantedId, "y");
			if (updatewanted == 0 || updatewantedPay == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			// ����������ӵ�����
			Wanted wanted = wantedDao.getById(wantedId);
			// ����Ǯ����Ϣ
			User buyUser = wanted.getUser();
			Purse purse = purseDao.getByUid(buyUser.getUid());
			PurseLog sellerPurseLog = PurseLog.withdraw(purse, wanted.getTotal(), "purse");
			Integer purseLogResult = purseLogDao.add(sellerPurseLog);
			if(purseLogResult==0){
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			wantedIndex.addIndex(wanted);
		} catch (InsertException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("������ " + out_trade_id + " ֧�����̳���");
		}

	}

	/**
	 * ����֧�������첽֪ͨ������������Ϣ 1.����֧��״̬ 2.�������������
	 */
	public void updatePayStatus(AlipayNotifyEntity alipayNotifyEntity) {
		Integer updatewanted = 0; // ����������Ľ��
		Integer updatewantedPay = 0; // ����������֧������Ľ��
		String out_trade_id = "δ�жϳ���ʲô���͵�֧��";
		try {
			// -----ִ�а���֧�����

			out_trade_id = alipayNotifyEntity.getOut_trade_no();
			Long wantedId = Long.parseLong(alipayNotifyEntity.getOut_trade_no());
			if (alipayNotifyEntity.getTrade_status().equals("TRADE_SUCCESS")
					|| alipayNotifyEntity.getTrade_status().equals("TRADE_FINISHED")) {
				// ����������״̬Ϊ yzf��֧��
				updatewanted = wantedDao.updateStatus(wantedId, WantedStatus.HavePaid.getStatus());
				// ����������֧����ϢΪy
				updatewantedPay = wantedPayDao.update(wantedId, "y");
				if (updatewanted == 0 || updatewantedPay == 0) {
					throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
				}
			}
			// ����������ӵ�����
			Wanted wanted = wantedDao.getById(wantedId);
			User buyUser = wanted.getUser();
			Purse purse = purseDao.getByUid(buyUser.getUid());
			PurseLog sellerPurseLog = PurseLog.withdraw(purse, wanted.getTotal(), "purse");
			Integer purseLogResult = purseLogDao.add(sellerPurseLog);
			if(purseLogResult==0){
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			wantedIndex.addIndex(wanted);
		} catch (InsertException e2) {
			throw e2;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new WantedException("������ " + out_trade_id + " ֧�����̳���");
		}

	}
	/**
	 * ����������
	 */
	public List<Wanted> query(PageFilter filter, String q, String field, String order) {
		List<Wanted> subWantedList = new ArrayList<Wanted>();
		try {
			//���ݹؼ��ֲ���������
			List<Wanted> wantedList = wantedIndex.searchWanted(q);
			if (wantedList.size() > 0) {
				if (field != null && order != null) {
					if (field.equals("total")) {
						//����������
						sortTotal(wantedList, order);
					}
				}
				int page = filter.getPage();
				int rows = filter.getRows();
				Integer toIndex = page * rows < wantedList.size() ? page * rows : wantedList.size();
				subWantedList = wantedList.subList((page - 1) * rows, toIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return subWantedList;
	}
	/**
	 * ˳�����������
	 */
	public List<Wanted> findOrder(PageFilter filter, String field, String order) {
		List<Wanted> wantedList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "yzf"); // ����������֧���Ķ���
			if (field != null && order != null) {
				if (order.equals("desc")) {
					params.put("orderDesc", field);
				} else {
					params.put("orderAsc", field);
				}
			}
			PageParamsUtil.addToParams(params, filter);
			wantedList = wantedDao.find(params);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		
		return wantedList;
	}

	public void sortTotal(List<Wanted> wantedList, String order) {
		if (order.equals("desc")) {
			Collections.sort(wantedList, new Comparator<Wanted>() {

				public int compare(Wanted o1, Wanted o2) {
					if (o1 == null && o2 == null)
						return 0;
					if (o1 == null)
						return -1;
					if (o2 == null)
						return 1;
					if (o1.getTotal() > o2.getTotal())
						return -1;
					if (o1.getTotal() == o2.getTotal())
						return 0;
					if (o1.getTotal() < o2.getTotal())
						return 1;

					return 0;
				}
			});
		}
		if (order.equals("asc")) {
			Collections.sort(wantedList, new Comparator<Wanted>() {

				public int compare(Wanted o1, Wanted o2) {
					if (o1 == null && o2 == null)
						return 0;
					if (o1 == null)
						return -1;
					if (o2 == null)
						return 1;
					if (o1.getTotal() > o2.getTotal())
						return 1;
					if (o1.getTotal() == o2.getTotal())
						return 0;
					if (o1.getTotal() < o2.getTotal())
						return -1;

					return 0;
				}
			});
		}

	}

}
