package lxms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import lxms.content.InsideCodes;
import lxms.dao.RouteDao;
import lxms.entity.Route;
import lxms.entity.User;
import lxms.exception.DeleteException;
import lxms.exception.InsertException;
import lxms.exception.RouteException;
import lxms.lucene.RouteIndex;
import lxms.service.RouteServiceI;
import lxms.tool.PageFilter;
import lxms.utils.PageParamsUtil;
/**
 * ���г̽��в���
 * @author yanlihui
 *
 */
@Service
public class RouteServiceImpl implements RouteServiceI {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private RouteDao routeDao;

	private RouteIndex routeIndex = new RouteIndex(); // �г�����
	/**
	 * 1.����г̡�
	 * 2.����г�����
	 */
	public void add(Route route) throws InsertException, RouteException {
		try {
			Integer result = routeDao.add(route);
			if (result == 0) {
				throw new InsertException(InsideCodes.INSERT_ERROR.getMsg());
			} else {
				routeIndex.addIndex(route); // �������
			}
		} catch (InsertException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RouteException("Insert userId:" + route.getUser().getUid() + " error");
		}
	}
	/**
	 * 1.ɾ���г�
	 * 2.ɾ������
	 */
	public void delete(Long rid, User user) throws InsertException, RouteException {
		try {
			Integer result = routeDao.delete(rid, user.getUid());
			if (result == 0) {
				throw new InsertException(InsideCodes.DELETE_ERROR.getMsg());
			} else {
				routeIndex.deleteIndex(rid); // ɾ������
			}
		} catch (DeleteException e) {
			throw e;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			throw new RouteException("Delete userId:" + user.getUid() + " error");
		}
	}
	/**
	 * ����Id��ȡ�г�
	 */
	public Route getById(Long rid) {
		return routeDao.getById(rid);
	}
	/**
	 * �����г�
	 */
	public List<Route> find(PageFilter filter) {
		Map<String, Object> params = new HashMap<String, Object>();
		PageParamsUtil.addToParams(params, filter);
		List<Route> list = routeDao.find(params);
		return list;
	}
	
	/**
	 * �����û������г�
	 */
	public List<Route> findByUser(PageFilter filter, User user) {
		List<Route> routeList = null;
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			PageParamsUtil.addToParams(params, filter);
			params.put("uid", user.getUid());
			routeList = routeDao.findDetail(params);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return routeList;
	}
	/**
	 * ��˳������г�
	 */
	public List<Route> findOrder(PageFilter filter, String order) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (order != null) {
			params.put("order", order);
		}
		PageParamsUtil.addToParams(params, filter);
		return routeDao.find(params);
	}
	/**
	 * ���ݹؼ��ʲ����г�
	 */
	public List<Route> query(PageFilter filter, String q, String field, String order) {
		List<Route> subRouteList = new ArrayList<Route>();
		try {
			List<Route> routeList = routeIndex.searchRoute(q);
			if (field != null && order != null) {
				if (field.equals("returnDate")) {
					sortReturnDate(routeList, order);
				}
			}
			if (routeList.size() > 0) {
				int page = filter.getPage();
				int rows = filter.getRows();
				Integer toIndex = page * rows < routeList.size() ? page * rows : routeList.size();
				subRouteList = routeList.subList((page - 1) * rows, toIndex);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return subRouteList;
	}

	public void sortReturnDate(List<Route> routeList, String order) {
		if (order.equals("desc")) {
			Collections.sort(routeList, new Comparator<Route>() {

				public int compare(Route o1, Route o2) {
					if (o1 == null && o2 == null)
						return 0;
					if (o1 == null)
						return -1;
					if (o2 == null)
						return 1;
					if (o1.getReturnDate().after(o2.getReturnDate()))
						return -1;
					if (o1.getReturnDate().equals(o2.getReturnDate()))
						return 0;
					if (o2.getReturnDate().after(o1.getReturnDate()))
						return 1;
					return 0;
				}

			});
		}
		if (order.equals("asc")) {
			Collections.sort(routeList, new Comparator<Route>() {

				public int compare(Route o1, Route o2) {
					if (o1 == null && o2 == null)
						return 0;
					if (o1 == null)
						return -1;
					if (o2 == null)
						return 1;
					if (o1.getReturnDate().after(o2.getReturnDate()))
						return 1;
					if (o1.getReturnDate().equals(o2.getReturnDate()))
						return 0;
					if (o2.getReturnDate().after(o1.getReturnDate()))
						return -1;
					return 0;
				}

			});
		}

	}

}
