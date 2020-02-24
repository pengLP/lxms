package lxms.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lxms.annotation.LoginLimit;

import lxms.content.StatusCodes;
import lxms.entity.User;
import lxms.entity.Wanted;
import lxms.exception.InsertException;
import lxms.service.WantedServiceI;
import lxms.tool.Json;
import lxms.tool.PageFilter;

import lxms.web.BaseController;

/**
 * 买家层面的操作
 * 
 * @author Yanlihui 2016.7.8 //添加list操作
 *
 */
@Controller
@RequestMapping("/buyer/api")
public class WantedController extends BaseController {
	private Log LOG = LogFactory.getLog(this.getClass());
	@Resource
	private WantedServiceI wantedService;
	/**
	 * 获取明星买家
	 * @param pageFilter
	 * @return
	 */
	@RequestMapping("/famous")
	@ResponseBody
	public Json famous(PageFilter pageFilter) {
		Json json = new Json();
		List<Wanted> wantedList;
		try {
			wantedList = wantedService.find(pageFilter);
			if (wantedList == null) {
				json.setCode(StatusCodes.S109);
			} else {
				json.setObj(wantedList);
				json.setCode(StatusCodes.S100);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * 查看所有悬赏 带排序
	 * 
	 * @param pageFilter
	 * @param field  排序字段
	 * @param order 升序或者降序
	 * @return
	 */
	@RequestMapping("/allWanted")
	@ResponseBody
	public Json allWanted(PageFilter pageFilter, @RequestParam(value = "field", required = false) String field,
			@RequestParam(value = "order", required = false) String order) {
		Json json = new Json();
		List<Wanted> wantedList;
		try {
			wantedList = wantedService.findOrder(pageFilter, field, order);
			if (wantedList == null) {
				json.setCode(StatusCodes.S109);
			} else {
				json.setObj(wantedList);
				json.setCode(StatusCodes.S100);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * 发布悬赏令
	 * 
	 * @param wanted
	 * @param session
	 * 
	 * @return
	 */
	// @RequestLimit(count=10,time=60)
	@LoginLimit
	@RequestMapping(value = "/wanted", method = RequestMethod.POST)
	@ResponseBody
	public Json add(Wanted wanted, HttpSession session, User user, HttpServletRequest request) {
		Json json = new Json();
		wanted.setUser(user);
		try {
			wantedService.add(wanted);
			json.setCode(StatusCodes.S100);
			json.setObj(wanted);
		} catch (InsertException e) {
			json.setCode(StatusCodes.INSERT_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * 获取某个悬赏令
	 * 
	 * @param wid
	 * @return
	 */
	@RequestMapping(value = "/wanted/{wid}", method = RequestMethod.GET)
	@ResponseBody
	public Json get(@PathVariable("wid") Long wid,HttpSession session) {
		Json json = new Json();
		try {
			Wanted wanted = wantedService.getWanted(wid,session);
			if (wanted != null) {
				json.setCode(StatusCodes.S100);
				json.setObj(wanted);
			} else {
				json.setCode(StatusCodes.S109);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * 删除悬赏令
	 * 
	 * @param wid
	 *            悬赏id
	 * @param session
	 * @return
	 */
	@LoginLimit
	@RequestMapping(value = "/wanted/{wid}", method = RequestMethod.DELETE)
	@ResponseBody
	public Json delete(@PathVariable("wid") Long wid, HttpSession session, User user) {
		Json json = new Json();
		try {
			wantedService.delete(wid, user);
			json.setCode(StatusCodes.S100);
		} catch (InsertException e) {
			json.setCode(StatusCodes.DELETE_ERROR);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	@LoginLimit
	@RequestMapping(value = "/my")
	@ResponseBody
	public Json my(HttpSession session, PageFilter pageFilter, User user) {
		Json json = new Json();
		try {
			List<Wanted> list = wantedService.findByUser(pageFilter, user);
			if (list.size() > 0) {
				json.setCode(StatusCodes.S100);
				json.setObj(list);
			} else {
				json.setCode(StatusCodes.S109);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			json.setCode(StatusCodes.S105);
		}
		return json;
	}

	/**
	 * 搜索悬赏
	 * 
	 * @param pageFilter
	 * @param query
	 * @param request
	 * @return
	 */
	// @RequestLimit(count=20,time=60)
	@RequestMapping("/q")
	@ResponseBody
	public Json query(PageFilter pageFilter, @RequestParam(required = false, value = "query") String query,
			HttpServletRequest request, @RequestParam(required = false, value = "field") String field,
			@RequestParam(required = false, value = "order") String order) {
		Json json = new Json();
		List<Wanted> wantedList;
		if (query == null) {
			json.setCode(StatusCodes.S109);
			return json;
		}
		try {
			wantedList = wantedService.query(pageFilter, query,field,order);
			if (wantedList != null) {
				json.setCode(StatusCodes.S100);
				json.setObj(wantedList);

			}
		} catch (Exception e) {
			json.setCode(StatusCodes.S105);
			LOG.error(e.getMessage());
		}
		return json;
	}
}
