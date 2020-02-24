package lxms.service;

import java.util.List;

import lxms.entity.Order;
import lxms.entity.OrderOver;
import lxms.entity.User;
import lxms.entity.pay.AlipayNotifyEntity;
import lxms.entity.pay.WeiPayNotify;
import lxms.exception.InsertException;
import lxms.exception.OrderException;
import lxms.exception.UpdateException;
import lxms.tool.PageFilter;

public interface OrderServiceI {
	public Order addOrder(Long wid, User buyUser) throws InsertException,OrderException;
	
	public void updatePayStatus(WeiPayNotify weiPayNotify) throws UpdateException,InsertException,OrderException;

	public void updatePayStatus(AlipayNotifyEntity alipayNotifyEntity) throws UpdateException,InsertException,OrderException;
	
	public void confirm(Long orderId,User user);
	
	public void delete(Long orderId,User user);
	
	public Order getOrderById(Long orderId,User user);
	
	public List<Order> find(PageFilter pageFilter,User user);
	
	public void uploadAirticket(OrderOver orderOver,User user);
}
