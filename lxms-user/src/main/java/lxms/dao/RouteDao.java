package lxms.dao;

import java.util.List;
import java.util.Map;

import lxms.entity.Route;

public interface RouteDao {
	
	public Integer add(Route route);   //添加行程
	 
	public Integer delete(Long rid,Long uid);   //删除行程 
	
	public Route getById(Long rid);   //根据id获取行程
	
	public List<Route> find(Map<String,Object> params);   //查找 不是很详细
	
	public List<Route> findDetail(Map<String,Object> params);   //查找很详细
	
	public List<Route> findByUser(Map<String,Object> params); //根据用户查找行程
}
