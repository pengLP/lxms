package lxms.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.PurseDao;
import lxms.dao.PurseLogDao;
import lxms.dao.UserRcertificationDao;
import lxms.dao.WithdrawDao;
import lxms.entity.Purse;
import lxms.entity.PurseLog;
import lxms.entity.User;
import lxms.entity.UserRcertification;
import lxms.entity.Withdraw;
import lxms.exception.InsertException;
import lxms.exception.PurseException;
import lxms.exception.UpdateException;
import lxms.service.PurseServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;

@Service
public class PurseServiceImpl implements PurseServiceI {
	@Resource
	private PurseDao purseDao;
	@Resource
	private PurseLogDao purseLogDao;
	@Resource
	private WithdrawDao withdrawDao;
	@Resource
	private UserRcertificationDao userRcertificationDao;
	
	private Log LOG = LogFactory.getLog(this.getClass());
	/**
	 * ��ȡǮ��
	 */
	public Purse getMyPurse(User user) {
		Purse purse = null;
		try {

			purse = purseDao.getByUid(user.getUid());
		} catch (Exception e) {

			LOG.error(e.getMessage());
			throw new PurseException("�û�" + user.getTel() + "��ȡǮ��ʧ��");
		}
		return purse;
	};
	/**
	 * �޸�����
	 */
	public void updatePassword(String newPassword, User user) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("password", newPassword);
			params.put("uid", user.getUid());
			Integer result = purseDao.updateMap(params);
			if (result == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PurseException("�û�" + user.getTel() + "��֧����ʧ��");
		}
	}
	/**
	 * �޸İ󶨵�֧����
	 */
	public void updateBindingAlipay(String newAliPay, User user) {
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("bindingAlipay", newAliPay);
			params.put("uid", user.getUid());
			Integer result = purseDao.updateMap(params);
			if (result == 0) {
				throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PurseException("�û�" + user.getTel() + "��֧����ʧ��");
		}
	}
	/**
	 * ��֤�����Ƿ���ȷ
	 */
	public boolean validate(String password, User user) {
		boolean flag = false;
		try {
			Purse purse = purseDao.getByUid(user.getUid());
			if (password.equals(purse.getPassword())) {
				flag = true;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return flag;

	}
	/**
	 * ��������
	 */
	public int setPassword(String newPassword, User user) {
		Integer result = 0;
		try {
			Purse purse = purseDao.getByUid(user.getUid());
			if (purse.getIsHavePassword() == 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("password", newPassword);
				params.put("isHavePassword", 1);
				params.put("uid", user.getUid());
				result = purseDao.updateMap(params);
				if (result == 0) {
					throw new UpdateException(InsideCodes.INSERT_ERROR.getMsg());
				}
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PurseException("�û�" + user.getTel() + "��������ʧ��");
		}
		return result;
	}
	/**
	 * ����֧����
	 */
	public void setBindingAlipay(String alipay, User user) {
		try {
			Purse purse = purseDao.getByUid(user.getUid());
			if (purse.getIsHaveAli() == 0) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("bindingAlipay", alipay);
				params.put("isHaveAli", 1);
				params.put("uid", user.getUid());
				Integer result = purseDao.updateMap(params);
				if (result == 0) {
					throw new UpdateException(InsideCodes.INSERT_ERROR.getMsg());
				}
			}
		} catch (UpdateException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PurseException("�û�" + user.getTel() + "��������ʧ��");
		}

	}
	/**
	 * ��¼
	 */
	public List<PurseLog> log(PageFilter filter, User user) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageParamsUtil.addToParams(params, filter);
		params.put("uid", user.getUid());
		return purseLogDao.find(params);
	}
	/**
	 * ����
	 */
	public String withdrawMoney(String password,String sum, User user,String realName) {
		String purseTotal = "0";
		try {
			
			Purse purse = purseDao.getByUid(user.getUid());
			if(realName==null){
				UserRcertification userRcertification = userRcertificationDao.getByUid(user.getUid());
				realName = userRcertification.getRealName();
			}

			if(!purse.getPassword().equals(password)){
				throw new PurseException("�������");
			}
			if(purse.getIsHaveAli()!=1){
				throw new PurseException("û�а�֧����");
			}
			if(realName==null){
				throw new PurseException("��������Ϊ��");
			}
			String Alipay= purse.getBindingAlipay();
			BigDecimal withdrawAmount = new BigDecimal(sum);
			BigDecimal total = new BigDecimal(purse.getBalance());
			if (total.compareTo(withdrawAmount) >= 0) {
				Integer purseResult = purseDao.subtractMoney(purse.getPurseId(), withdrawAmount.doubleValue());
				if(purseResult==0){
					throw new UpdateException(InsideCodes.UPDATE_ERROR.getMsg());
				}
				PurseLog purseLog = PurseLog.withdraw(purse, withdrawAmount.doubleValue());
				Integer purseLogResult = purseLogDao.add(purseLog);
				if(purseLogResult==0){
					throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
				}
				purseTotal = total.subtract(withdrawAmount).toString();
				/**
				 * ���Ժ�����ֲ���
				 */
				Withdraw withdraw = Withdraw.create(realName,purse,withdrawAmount.doubleValue());
				withdrawDao.add(withdraw);
			}

		} catch (UpdateException e) {
			throw e;
		}catch (Exception e) {
			LOG.error(e.getMessage());
			throw new PurseException("�û�"+user.getUid()+"����ʧ��");
		}
		return purseTotal;
	}
	
}
