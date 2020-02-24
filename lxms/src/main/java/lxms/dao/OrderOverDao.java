package lxms.dao;

import java.util.List;
import java.util.Map;
import lxms.entity.OrderOver;
/**
 * 订单后续流程
 * @author Administrator
 *
 */
public interface OrderOverDao {
	public Integer add(OrderOver OrderOver);

	public Integer update(OrderOver OrderOver);

	public Integer updateStatus(Map<String, Object> params);

	public OrderOver getByOrderId(Long OrderId);

	public List<OrderOver> find(Map<String, Object> params);

}
