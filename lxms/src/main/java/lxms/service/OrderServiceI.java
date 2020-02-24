package lxms.service;

import java.util.List;
import java.util.Map;
import lxms.entity.Order;
import lxms.entity.OrderOver;
import lxms.entity.User;
import lxms.tool.PageFilter;

public interface OrderServiceI {
	
	public List<OrderOver> find(PageFilter pageFilter,String status);
	
	public void validateSuccess(OrderOver orderOver);
	
	public void validateFalse(OrderOver orderOver);
	
	
}
