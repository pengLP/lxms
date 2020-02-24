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
 * 对悬赏令的操作
 * 
 * @author yanlihui
 *
 */
@Service
public class WantedServiceImpl implements WantedServiceI {

	private Log LOG = LogFactory.getLog(this.getClass());

	private WantedIndex wantedIndex = new WantedIndex(); // 悬赏令索引

	@Resource
	private WantedDao wantedDao;
	@Resource
	private WantedPayDao wantedPayDao;
	@Resource
	private PurseDao purseDao;
	@Resource
	private PurseLogDao purseLogDao;

	/*
	 * 发布悬赏令 1.添加悬赏令 2.添加悬赏令支付信息 支付与否按照支付信息
	 */
	public void add(Wanted wanted) throws InsertException, WantedException {
		try {
			// 添加悬赏令
			Integer result = wantedDao.add(wanted); // 悬赏令添加结果

			WantedPay wantedPay = WantedPay.getWantedPay(wanted);
			Integer payResult = wantedPayDao.add(wantedPay); // 悬赏令支付信息添加结果

			if (result == 0 || payResult == 0) { // 判断悬赏令和支付信息有没有成功
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
	 * 删除悬赏令
	 * 1.查看悬赏令的支付信息
	 * 2.如果是n就直接删除
	 * 3.如果是y，把钱退还给用户
	 */
	public void delete(Long wid, User user) throws DeleteException, WantedException {
		try {
			//1.获取悬赏令支付信息
			WantedPay wantedPay = wantedPayDao.getByWanteId(wid);
			//2.获取悬赏令
			Wanted wanted = wantedDao.getById(wid);
			if (wantedPay.getPaystatus().equals("y")) {
				
				//2.获取钱包
				Purse purse = purseDao.getByUid(user.getUid());
				//3.创建钱包日志
				PurseLog purseLog = PurseLog.add(purse, wanted.getTotal());
				//5.添加钱包支付日志
				Integer purseLogResult = purseLogDao.add(purseLog);
				//6.更新钱包钱数
				Integer purseResult = purseDao.addMoney(purse.getPurseId(), wanted.getTotal());
				if (purseResult == 0 || purseLogResult == 0) {
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
			}
			//删除悬赏令
			Integer result = wantedDao.delete(wid, user.getUid());
			//删除悬令支付信息
			Integer DeleteWantedPay = wantedPayDao.delete(wantedPay.getWantedPayId());
			if (result == 0 || DeleteWantedPay == 0) {
				throw new DeleteException(InsideCodes.DELETE_ERROR.getMsg());
			}
			if(wanted.getStatus().equals(WantedStatus.HavePaid)){
				//如果悬赏状态为已支付则删除索引
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
	 * 更新悬赏
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
	 * 查找悬赏
	 */
	public List<Wanted> find(PageFilter pageFilter) {
		List<Wanted> wantedList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "yzf"); // 查找所有已支付的订单
			PageParamsUtil.addToParams(params, pageFilter);
			wantedList = wantedDao.find(params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return wantedList;
	}
	
	

	/**
	 * 获取单个悬赏令
	 * 1.悬赏令状态为已支付则直接返回
	 * 2.如果不是已支付状态则判断若悬赏令是本人发，如果是则直接返回
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
	 * 查找单个用户的悬赏
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
	 * 根据微信的异步通知更新悬赏令信息 1.更新支付状态 2.添加悬赏令索引
	 */
	public void updatePayStatus(WeiPayNotify weiPayNotify) {
		Integer updatewanted = 0; // 更新悬赏令的结果
		Integer updatewantedPay = 0; // 更新悬赏令支付情况的结果
		String out_trade_id = "未判断出是什么类型的支付";
		try {
			out_trade_id = weiPayNotify.getOut_trade_no();
			Long wantedId = Long.parseLong(out_trade_id.substring(6, out_trade_id.length()));
			// 更新悬赏令状态为 yzf已支付
			updatewanted = wantedDao.updateStatus(wantedId, WantedStatus.HavePaid.getStatus());
			// 更新悬赏令支付信息为y
			updatewantedPay = wantedPayDao.update(wantedId, "y");
			if (updatewanted == 0 || updatewantedPay == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			}
			// 把悬赏令添加到索引
			Wanted wanted = wantedDao.getById(wantedId);
			// 更新钱包信息
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
			throw new WantedException("悬赏令 " + out_trade_id + " 支付过程出错");
		}

	}

	/**
	 * 根据支付宝的异步通知更新悬赏令信息 1.更新支付状态 2.添加悬赏令索引
	 */
	public void updatePayStatus(AlipayNotifyEntity alipayNotifyEntity) {
		Integer updatewanted = 0; // 更新悬赏令的结果
		Integer updatewantedPay = 0; // 更新悬赏令支付情况的结果
		String out_trade_id = "未判断出是什么类型的支付";
		try {
			// -----执行阿里支付结果

			out_trade_id = alipayNotifyEntity.getOut_trade_no();
			Long wantedId = Long.parseLong(alipayNotifyEntity.getOut_trade_no());
			if (alipayNotifyEntity.getTrade_status().equals("TRADE_SUCCESS")
					|| alipayNotifyEntity.getTrade_status().equals("TRADE_FINISHED")) {
				// 更新悬赏令状态为 yzf已支付
				updatewanted = wantedDao.updateStatus(wantedId, WantedStatus.HavePaid.getStatus());
				// 更新悬赏令支付信息为y
				updatewantedPay = wantedPayDao.update(wantedId, "y");
				if (updatewanted == 0 || updatewantedPay == 0) {
					throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
				}
			}
			// 把悬赏令添加到索引
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
			throw new WantedException("悬赏令 " + out_trade_id + " 支付过程出错");
		}

	}
	/**
	 * 查找悬赏令
	 */
	public List<Wanted> query(PageFilter filter, String q, String field, String order) {
		List<Wanted> subWantedList = new ArrayList<Wanted>();
		try {
			//根据关键字查找悬赏令
			List<Wanted> wantedList = wantedIndex.searchWanted(q);
			if (wantedList.size() > 0) {
				if (field != null && order != null) {
					if (field.equals("total")) {
						//排序悬赏令
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
	 * 顺序查找悬赏令
	 */
	public List<Wanted> findOrder(PageFilter filter, String field, String order) {
		List<Wanted> wantedList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("status", "yzf"); // 查找所有已支付的订单
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
