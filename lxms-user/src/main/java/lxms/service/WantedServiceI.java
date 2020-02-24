package lxms.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import lxms.entity.User;
import lxms.entity.Wanted;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.UpdateException;
import lxms.exception.WantedException;
import lxms.tool.PageFilter;

public interface WantedServiceI {

	public void add(Wanted wanted) throws InsertException, WantedException;

	public void delete(Long wid, User user) throws DeleteException, WantedException;

	public void update(Wanted wanted) throws UpdateException, WantedException;

	public void updatePayStatus(WeiPayNotify weiPayNotify) throws UpdateException, WantedException;

	public void updatePayStatus(AlipayNotifyEntity alipayNotifyEntity) throws UpdateException, WantedException;

	public Wanted getWanted(Long wid,HttpSession session);

	public List<Wanted> findOrder(PageFilter filter, String field,String order);

	public List<Wanted> find(PageFilter filter);


	public List<Wanted> findByUser(PageFilter pageFilter, User currentUser);

	public List<Wanted> query(PageFilter filter, String q, String field, String order);
}
