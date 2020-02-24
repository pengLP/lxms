package lxms.dao;

import java.util.List;
import java.util.Map;

import lxms.entity.Route;

public interface RouteDao {
	
	public Integer add(Route route);   //����г�
	 
	public Integer delete(Long rid,Long uid);   //ɾ���г� 
	
	public Route getById(Long rid);   //����id��ȡ�г�
	
	public List<Route> find(Map<String,Object> params);   //���� ���Ǻ���ϸ
	
	public List<Route> findDetail(Map<String,Object> params);   //���Һ���ϸ
	
	public List<Route> findByUser(Map<String,Object> params); //�����û������г�
}
