package lxms.service;

import java.util.List;

import lxms.entity.Route;
import lxms.entity.User;

import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.tool.PageFilter;

public interface RouteServiceI {
	public void add(Route route) throws InsertException;

	public void delete(Long rid, User user) throws DeleteException;


	public Route getById(Long rid);

	public List<Route> findOrder(PageFilter filter, String order);

	public List<Route> find(PageFilter filter);



	public List<Route> findByUser(PageFilter filter, User user);

	public List<Route> query(PageFilter filter, String q,String field,String order);
}
