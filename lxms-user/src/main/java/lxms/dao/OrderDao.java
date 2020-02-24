package lxms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import lxms.entity.Order;

public interface OrderDao {
	public Integer add(Order order);

	public Integer delete(Long orderId);

	public Integer update(Order order);

	public Integer updateStatus(Map<String, Object> params);

	public Integer deleteBySeller(@Param("orderId") Long orderId, @Param("suid") Long suid);

	public Integer deleteByBuyer(@Param("orderId") Long orderId, @Param("buid") Long buid);

	public Order getById(Long OrderId);

	public List<Order> find(Map<String, Object> params);

	public List<Order> findMy(Map<String, Object> params);
}
